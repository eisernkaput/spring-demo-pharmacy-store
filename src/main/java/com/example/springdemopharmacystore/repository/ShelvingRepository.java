package com.example.springdemopharmacystore.repository;

import com.example.springdemopharmacystore.model.entity.Shelving;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelvingRepository extends JpaRepository<Shelving, Long> {
}