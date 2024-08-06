package com.example.springdemopharmacystore.repository;

import com.example.springdemopharmacystore.model.entity.WarehouseStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WarehouseStockRepository extends JpaRepository<WarehouseStock, Long> {

    @Query("SELECT ws FROM WarehouseStock ws WHERE ws.warehouse = :warehouseId AND ws.isRefrigerator = :isRefrigerator" +
            " AND ws.availableStockCapacity >= :packageSize ORDER BY ws.availableStockCapacity DESC LIMIT 1")
    WarehouseStock findAvailableStock(Long warehouseId, Boolean isRefrigerator, Integer packageSize);
}