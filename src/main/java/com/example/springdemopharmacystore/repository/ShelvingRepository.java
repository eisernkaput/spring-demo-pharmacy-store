package com.example.springdemopharmacystore.repository;

import com.example.springdemopharmacystore.model.entity.Drug;
import com.example.springdemopharmacystore.model.entity.Shelving;
import com.example.springdemopharmacystore.model.entity.WarehouseStock;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShelvingRepository extends JpaRepository<Shelving, Long> {

    @Query("select s from Shelving s where s.drug = :drug and s.warehouseStock = :stock")
    List<Shelving> findByDrugIdAndStock(@NotNull Drug drug, @NotNull WarehouseStock stock);

    @Query("select s from Shelving s join s.warehouseStock ws join ws.warehouse w" +
            " where s.drug = :drug and w.id = :warehouseId")
    List<Shelving> findByDrugIdAndWarehouseId(@NotNull Drug drug, @NotNull Long warehouseId);
}