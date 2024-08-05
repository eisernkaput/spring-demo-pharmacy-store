package com.example.springdemopharmacystore.entity;

import com.example.springdemopharmacystore.dictionaries.DrugPurpose;
import com.example.springdemopharmacystore.dictionaries.DrugType;
import com.example.springdemopharmacystore.dictionaries.Manufacturer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "drug")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Drug {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = SEQUENCE, generator = "drug_sequence")
    @SequenceGenerator(name = "drug_sequence", sequenceName = "drug_sequence", allocationSize = 1)
    private Long id;

    @NotNull(message = "drug name is null")
    @Column(name = "name")
    private String name;

    @NotNull(message = "drug commercial name is null")
    @Column(name = "commercial_name", unique = true)
    private String commercialName;

    @NotNull(message = "drug type is null")
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private DrugType type;

    @NotNull(message = "drug purpose is null")
    @Enumerated(EnumType.STRING)
    @Column(name = "purpose")
    private DrugPurpose purpose;

    @NotNull(message = "drug manufacturer is null")
    @Enumerated(EnumType.STRING)
    @Column(name = "manufacturer")
    private Manufacturer manufacturer;

    @NotNull(message = "drug require refrigeration is null")
    @Column(name = "require_refrigeration")
    private Boolean requireRefrigeration;

    @NotNull(message = "drug package size is null")
    @Column(name = "package_size")
    private Integer packageSize;

    @NotNull(message = "drug in stock refrigeration is null")
    @Column(name = "in_stock")
    private Boolean isInStock;

    @NotNull(message = "drug last update date is null")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

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
        Drug that = (Drug) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
