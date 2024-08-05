package com.example.springdemopharmacystore.service;

import com.example.springdemopharmacystore.mapper.DrugMapper;
import com.example.springdemopharmacystore.model.DrugAddInput;
import com.example.springdemopharmacystore.repository.DrugRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DrugServiceImpl implements DrugService{

    private final DrugRepository drugRepository;

    private final DrugMapper drugMapper;

    @Override
    @Transactional
    public void addDrugToWarehouse(DrugAddInput input) {
        drugRepository.save(drugMapper.toEntity(input));
        drugRepository.findById(drugMapper.toEntity(input).getId());
    }
}
