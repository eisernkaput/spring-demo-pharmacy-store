package com.example.springdemopharmacystore.service;

import com.example.springdemopharmacystore.model.dictionaries.DrugPurpose;
import com.example.springdemopharmacystore.model.dictionaries.DrugType;
import com.example.springdemopharmacystore.model.dictionaries.Manufacturer;
import com.example.springdemopharmacystore.model.entity.Drug;
import com.example.springdemopharmacystore.model.entity.Shelving;
import com.example.springdemopharmacystore.mapper.DrugMapper;
import com.example.springdemopharmacystore.repository.DrugRepository;
import com.example.springdemopharmacystore.rest.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.springdemopharmacystore.util.Utils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DrugServiceImpl implements DrugService {

    private final DrugRepository drugRepository;

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
            Drug inputDrug = new Drug();
            DrugDto drugInfo = requestBody.getDrugInfo();
            inputDrug.setName(drugInfo.getName());
            inputDrug.setCommercialName(drugInfo.getCommercialName());
            inputDrug.setType(getEnum(drugInfo.getType(), DrugType.class));
            inputDrug.setPurpose(getEnum(drugInfo.getPurpose(), DrugPurpose.class));
            inputDrug.setManufacturer(getEnum(drugInfo.getManufacturer(), Manufacturer.class));
            inputDrug.setRequireRefrigeration(Boolean.valueOf(drugInfo.getRequireRefrigeration()));
            inputDrug.setPackageSize(Integer.valueOf(drugInfo.getPackageSize()));
            inputDrug.setIsInStock(true);
            drugRepository.save(inputDrug);

            Shelving shelving = new Shelving();
            shelving.setDrug(inputDrug);
            shelving.setShipmentNum(requestBody.getShipmentNum());
            shelving.setPackageCounter();
        } catch (Exception e) {
            log.error("[addDrugToWarehouse] exception {}, message {}", e.getClass().getSimpleName(), e.getMessage());
            throw e;
        }
        return responseDto;
    }

    @Override
    @Transactional
    public GetDrugResponseDto getDrugFromWarehouse(GetDrugRequestDto requestBody) {

        drugRepository.findById(drugMapper.toEntity(input).getId());
        GetDrugResponseDto responseDto = new GetDrugResponseDto();
        return responseDto;
    }
}
