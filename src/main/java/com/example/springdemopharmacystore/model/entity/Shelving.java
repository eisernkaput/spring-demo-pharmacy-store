package com.example.springdemopharmacystore.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "shelving")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Shelving {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = SEQUENCE, generator = "shelving_sequence")
    @SequenceGenerator(name = "shelving_sequence", sequenceName = "shelving_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "drug_id", nullable = false)
    private Drug drug;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_stock_id", nullable = false)
    private WarehouseStock warehouseStock;

    @NotNull(message = "shelving shipment num is null")
    @Column(name = "shipment_num")
    private String shipmentNum;

    @NotNull(message = "shelving package counter is null")
    @Column(name = "package_counter")
    private Long packageCounter;

    @NotNull(message = "shelving shipment date is null")
    @Column(name = "shipment_date")
    private LocalDate shipmentDate;

    @NotNull(message = "shelving expiration date is null")
    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @NotNull(message = "shelving last update date is null")
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
        Shelving that = (Shelving) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
