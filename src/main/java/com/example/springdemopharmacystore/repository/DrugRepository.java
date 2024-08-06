package com.example.springdemopharmacystore.repository;

import com.example.springdemopharmacystore.model.entity.Drug;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrugRepository extends JpaRepository <Drug, Long>  {

    @Query("SELECT e FROM Drug e WHERE e.id = :id")
    Optional<Drug> findById(@NonNull Long id);
}
