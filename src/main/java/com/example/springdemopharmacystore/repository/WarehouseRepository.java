package com.example.springdemopharmacystore.repository;

import com.example.springdemopharmacystore.model.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}