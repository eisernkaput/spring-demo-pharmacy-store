package com.example.springdemopharmacystore.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "warehouse")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class Warehouse {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = SEQUENCE, generator = "warehouse_sequence")
    @SequenceGenerator(name = "warehouse_sequence", sequenceName = "warehouse_sequence", allocationSize = 1)
    private Long id;

    @NotNull(message = "warehouseStock last update date is null")
    @LastModifiedDate
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @CreatedDate
    @Column(name = "create_date")
    private LocalDateTime createDate;
}
