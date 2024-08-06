package com.example.springdemopharmacystore.service;

import com.example.springdemopharmacystore.model.dictionaries.DrugPurpose;
import com.example.springdemopharmacystore.model.dictionaries.DrugType;
import com.example.springdemopharmacystore.model.dictionaries.Manufacturer;
import com.example.springdemopharmacystore.model.entity.Drug;
import com.example.springdemopharmacystore.model.entity.Shelving;
import com.example.springdemopharmacystore.mapper.DrugMapper;
import com.example.springdemopharmacystore.model.entity.WarehouseStock;
import com.example.springdemopharmacystore.repository.DrugRepository;
import com.example.springdemopharmacystore.repository.ShelvingRepository;
import com.example.springdemopharmacystore.repository.WarehouseStockRepository;
import com.example.springdemopharmacystore.rest.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.example.springdemopharmacystore.util.Utils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DrugServiceImpl implements DrugService {

    private final DrugRepository drugRepository;
    private final ShelvingRepository shelvingRepository;
    private final WarehouseStockRepository warehouseStockRepository;

    private final DrugMapper drugMapper;

    //- поместить лекарство на склад
//            (параметры : лекарство, номер партии, дата окончания срока действия, ид склада, ид сотрудника)
//            (пишется в таблицу лекарств, если такого еще нет. выбирается первый подходящий по типу шкаф
//            , где есть место = + в потрач вместимость шкафа в складу, новая запись в этот шкаф, новая запись в лекарство)
//            = возвращает номер шкафа
    @Override
    @Transactional
    public AddDrugResponseDto addDrugToWarehouse(AddDrugRequestDto requestBody) {
        AddDrugResponseDto responseDto = new AddDrugResponseDto();
        try {
            Drug inputDrug = drugEntityCreate(requestBody.getDrugInfo());
            drugRepository.save(inputDrug);
            String warehouseId = requestBody.getWarehouseId();
            WarehouseStock availableStock = warehouseStockRepository.findAvailableStock(
                    Long.valueOf(warehouseId), inputDrug.getRequireRefrigeration(), inputDrug.getPackageSize());
            if (availableStock == null) {
                log.warn("[addDrugToWarehouse] no available capacity in warehouse {}", warehouseId);
                responseDto.setMessage(String.format("На складе ИД:%s нет места", warehouseId));
                return responseDto;
            }

            Shelving shelving = availableStock.getShelving();
            Integer packageCounterAfterAdd = shelving.getPackageCounter() + inputDrug.getPackageSize();
            shelving.setDrug(inputDrug);
            shelving.setShipmentNum(requestBody.getShipmentNum());
            shelving.setPackageCounter(packageCounterAfterAdd);
            shelving.setShipmentDate(LocalDateTime.now());
            shelving.setExpirationDate(requestBody.getExpirationDate());
            shelvingRepository.save(shelving);

            Integer availableStockAfterAdd = availableStock.getAvailableStockCapacity() - inputDrug.getPackageSize();
            availableStock.setAvailableStockCapacity(availableStockAfterAdd);
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

//        drugRepository.findById(drugMapper.toEntity(input).getId());
        GetDrugResponseDto responseDto = new GetDrugResponseDto();
        return responseDto;
    }

    private Drug drugEntityCreate(DrugDto drugDto) {
        Drug drug = new Drug();
        drug.setName(drugDto.getName());
        drug.setCommercialName(drugDto.getCommercialName());
        drug.setType(getEnum(drugDto.getType(), DrugType.class));
        drug.setPurpose(getEnum(drugDto.getPurpose(), DrugPurpose.class));
        drug.setManufacturer(getEnum(drugDto.getManufacturer(), Manufacturer.class));
        drug.setRequireRefrigeration(Boolean.valueOf(drugDto.getRequireRefrigeration()));
        drug.setPackageSize(Integer.valueOf(drugDto.getPackageSize()));
        drug.setIsInStock(true);
        return drug;
    }

    private Drug warehouseStockCreate(DrugDto drugDto) {
        Drug drug = new Drug();
        drug.setName(drugDto.getName());
        drug.setCommercialName(drugDto.getCommercialName());
        drug.setType(getEnum(drugDto.getType(), DrugType.class));
        drug.setPurpose(getEnum(drugDto.getPurpose(), DrugPurpose.class));
        drug.setManufacturer(getEnum(drugDto.getManufacturer(), Manufacturer.class));
        drug.setRequireRefrigeration(Boolean.valueOf(drugDto.getRequireRefrigeration()));
        drug.setPackageSize(Integer.valueOf(drugDto.getPackageSize()));
        drug.setIsInStock(true);
        return drug;
    }
}
