package com.example.springdemopharmacystore.model.dictionaries;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "warehouse_address")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class WarehouseAddress {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = SEQUENCE, generator = "w_address_sequence")
    @SequenceGenerator(name = "w_address_sequence", sequenceName = "w_address_sequence", allocationSize = 1)
    private Long id;

    @NotNull(message = "warehouse address value is null")
    @Column(name = "address")
    private String address;

    @NotNull(message = "warehouse address coordinates is null")
    @Column(name = "coordinates")
    private String coordinates;

    @NotNull(message = "warehouse address last update date is null")
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
        WarehouseAddress that = (WarehouseAddress) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
