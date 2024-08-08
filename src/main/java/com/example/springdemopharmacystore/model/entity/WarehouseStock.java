package com.example.springdemopharmacystore.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "warehouse_stock")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class WarehouseStock {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = SEQUENCE, generator = "w_stock_sequence")
    @SequenceGenerator(name = "w_stock_sequence", sequenceName = "w_stock_sequence", allocationSize = 1)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "warehouseStock")
    private Set<Shelving> shelvings = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @NotNull(message = "warehouseStock shelving num is null")
    @Column(name = "shelving_num")
    private String shelvingNum;

    @NotNull(message = "warehouseStock is refrigerator is null")
    @Column(name = "is_refrigerator")
    private Boolean isRefrigerator;

    @NotNull(message = "warehouseStock total stock capacity is null")
    @Column(name = "total_stock_capacity")
    private Long totalStockCapacity;

    @NotNull(message = "warehouseStock available stock capacity is null")
    @Column(name = "available_stock_capacity")
    private Long availableStockCapacity;

    @NotNull(message = "warehouseStock last update date is null")
    @LastModifiedDate
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @CreatedDate
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        WarehouseStock that = (WarehouseStock) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
