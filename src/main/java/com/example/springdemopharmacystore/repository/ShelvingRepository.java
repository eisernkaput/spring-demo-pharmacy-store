package com.example.springdemopharmacystore.repository;

import com.example.springdemopharmacystore.model.entity.Drug;
import com.example.springdemopharmacystore.model.entity.Shelving;
import com.example.springdemopharmacystore.model.entity.WarehouseStock;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShelvingRepository extends JpaRepository<Shelving, Long> {

    @Query("select s from Shelving s join fetch s.warehouseStock ws where s.drug = :drug and ws.id = :stockId")
    List<Shelving> findByDrugIdAndStock(@NotNull Drug drug, @NotNull Long stockId);

    @Query("select s from Shelving s join fetch s.warehouseStock ws join fetch ws.warehouse w" +
            " where s.drug = :drug and w.id = :warehouseId")
    List<Shelving> findByDrugIdAndWarehouseId(@NotNull Drug drug, @NotNull Long warehouseId);
}