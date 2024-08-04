package com.example.springdemopharmacystore.repository;

import com.example.springdemopharmacystore.entity.WarehouseStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseStockRepository extends JpaRepository<WarehouseStock, Long> {
}