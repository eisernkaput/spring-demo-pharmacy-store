package com.example.springdemopharmacystore.repository;

import com.example.springdemopharmacystore.model.entity.WarehouseStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WarehouseStockRepository extends JpaRepository<WarehouseStock, Long> {

    @Query("SELECT ws FROM WarehouseStock ws" +
            " JOIN ws.warehouse w" +
            " WHERE w.id = :warehouseId" +
            " AND ws.isRefrigerator = :isRefrigerator" +
            " AND ws.availableStockCapacity >= :packageSize" +
            " ORDER BY ws.availableStockCapacity LIMIT 1")
    WarehouseStock findAvailableStock(Long warehouseId, Boolean isRefrigerator, Long packageSize);

    List<WarehouseStock> findByWarehouseId(Long warehouseId);
}