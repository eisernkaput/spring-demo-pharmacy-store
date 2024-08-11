package com.example.springdemopharmacystore.service;

import com.example.springdemopharmacystore.exception.DataNotFoundException;
import com.example.springdemopharmacystore.model.dictionaries.DrugPurpose;
import com.example.springdemopharmacystore.model.dictionaries.DrugType;
import com.example.springdemopharmacystore.model.dictionaries.Manufacturer;
import com.example.springdemopharmacystore.model.entity.Drug;
import com.example.springdemopharmacystore.model.entity.Shelving;
import com.example.springdemopharmacystore.mapper.DrugMapper;
import com.example.springdemopharmacystore.model.entity.WarehouseStock;
import com.example.springdemopharmacystore.repository.*;
import com.example.springdemopharmacystore.rest.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import static com.example.springdemopharmacystore.util.Utils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DrugServiceImpl implements DrugService {

    private final DrugRepository drugRepository;
    private final ShelvingRepository shelvingRepository;
    private final WarehouseStockRepository warehouseStockRepository;

    private final DrugMapper drugMapper;

    @Override
    @Transactional
    public AddDrugResponseDto addDrugToWarehouse(AddDrugRequestDto requestBody) {
        AddDrugResponseDto responseDto = new AddDrugResponseDto();
        try {
            Drug inputDrug = drugEntityCreate(requestBody.getDrugInfo());
            drugRepository.save(inputDrug);

            Long warehouseId = Long.valueOf(requestBody.getWarehouseId());
            WarehouseStock availableStock = warehouseStockRepository.findAvailableStock(
                    warehouseId, inputDrug.getRequireRefrigeration(), inputDrug.getPackageSize());
            if (availableStock == null) {
                log.warn("[addDrugToWarehouse] no available capacity in warehouse {}", warehouseId);
                responseDto.setMessage(String.format("На складе ИД:%s нет места", warehouseId));
                return responseDto;
            }
            String shipmentNum = requestBody.getShipmentNum();
            LocalDate expirationDate = requestBody.getExpirationDate();

            Shelving shelvingToAdd = shelvingRepository
                    .findByDrugIdAndStock(inputDrug, availableStock).stream()
                    .filter(shelving -> shelving.getShipmentNum().equals(shipmentNum))
                    .filter(shelving -> shelving.getShipmentDate().isEqual(LocalDate.now()))
                    .filter(shelving -> shelving.getExpirationDate().isEqual(expirationDate))
                    .findFirst().orElseGet(
                            ()->shelvingEntityCreate(inputDrug, availableStock, shipmentNum, expirationDate)
                    );
            Long packageSizeInShelving = shelvingToAdd.getPackageCounter();
            shelvingToAdd.setPackageCounter(
                    packageSizeInShelving + inputDrug.getPackageSize()
            );
            shelvingRepository.save(shelvingToAdd);

            availableStock.setAvailableStockCapacity(
                    availableStock.getAvailableStockCapacity() - inputDrug.getPackageSize()
            );
            warehouseStockRepository.save(availableStock);

            responseDto.setMessage("Лекарство можно поместить в стеллаж с номером:");
            responseDto.setShelvingNum(availableStock.getShelvingNum());
        } catch (Exception e) {
            log.error("[addDrugToWarehouse] exception {}, message {}", e.getClass().getSimpleName(), e.getMessage());
            throw e;
        }
        return responseDto;
    }

    @Override
    @Transactional
    public GetDrugResponseDto getDrugFromWarehouse(GetDrugRequestDto requestBody) {
        GetDrugResponseDto responseDto = new GetDrugResponseDto();
        try {
            Long warehouseId = Long.valueOf(requestBody.getWarehouseId());
            Long drugIdId = Long.valueOf(requestBody.getDrugId());

            Drug drugInStock = drugRepository.findByIdAndIsInStock(drugIdId, true)
                    .orElseThrow(() -> new DataNotFoundException("Drug with id: \"%s\" not found".formatted(drugIdId)));

            List<Shelving> shelvingWithDrugOnWarehouseList = shelvingRepository
                    .findByDrugIdAndWarehouseId(drugInStock, warehouseId).stream()
                    .filter(shelving -> shelving.getExpirationDate().isAfter(LocalDate.now()))
                    .filter(shelving -> shelving.getPackageCounter() >= requestBody.getNumOfPackages())
                    .toList();
            if (shelvingWithDrugOnWarehouseList.isEmpty()) {
                log.warn("[getDrugFromWarehouse] no drug: {} in warehouse: {}", drugIdId, warehouseId);
                responseDto.setMessage(String.format("Нет лекарства с ИД:%s на складе с ИД:%s", drugIdId, warehouseId));
                return responseDto;
            }
            Shelving shelvingWithDrug = shelvingWithDrugOnWarehouseList.getFirst();
            WarehouseStock stockWithDrug = shelvingWithDrug.getWarehouseStock();
            String shelvingNum = stockWithDrug.getShelvingNum();

            responseDto.setDrugInfo(drugMapper.toDrugDto(drugInStock));
            responseDto.setShelvingNum(shelvingNum);

            Long availableStockCapacityAfterGet = stockWithDrug.getAvailableStockCapacity() + requestBody.getNumOfPackages();
            stockWithDrug.setAvailableStockCapacity(availableStockCapacityAfterGet);
            warehouseStockRepository.save(stockWithDrug);

            long numOfPackagesAfterGet = shelvingWithDrug.getPackageCounter() - requestBody.getNumOfPackages();
            if (numOfPackagesAfterGet <= 0L) {
                shelvingRepository.delete(shelvingWithDrug);
                numOfPackagesAfterGet = 0L;
            } else {
                shelvingWithDrug.setPackageCounter(numOfPackagesAfterGet);
                shelvingRepository.save(shelvingWithDrug);
            }
            responseDto.setNumOfPackagesInStock(numOfPackagesAfterGet);
            responseDto.setMessage(
                    String.format("Лекарство \"%s\" можно взять на стеллаже N:%s. Количество оставшихся упаковок: %s"
                    , drugInStock.getName(), shelvingNum, numOfPackagesAfterGet)
            );

        } catch (Exception e) {
            log.error("[getDrugFromWarehouse] exception {}, message {}", e.getClass().getSimpleName(), e.getMessage());
            throw e;
        }
        return responseDto;
    }

    private Drug drugEntityCreate(DrugDto drugDto) {
        return Drug.builder()
                .name(drugDto.getName())
                .commercialName(drugDto.getCommercialName())
                .type(getEnum(drugDto.getType(), DrugType.class))
                .purpose(getEnum(drugDto.getPurpose(), DrugPurpose.class))
                .manufacturer(getEnum(drugDto.getManufacturer(), Manufacturer.class))
                .requireRefrigeration(Boolean.valueOf(drugDto.getRequireRefrigeration()))
                .packageSize(Long.valueOf(drugDto.getPackageSize()))
                .isInStock(true)
                .build();
    }

    private Shelving shelvingEntityCreate(Drug inputDrug, WarehouseStock stock, String shipmentNum
            , LocalDate expirationDate) {
        return Shelving.builder()
                .drug(inputDrug)
                .warehouseStock(stock)
                .shipmentNum(shipmentNum)
                .shipmentDate(LocalDate.now())
                .expirationDate(expirationDate)
                .packageCounter(0L)
                .build();
    }
}
