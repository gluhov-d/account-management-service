package com.github.gluhov.accountmanagementservice.model;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("addresses")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Addresses extends BaseEntity {
    @Column("country_id")
    private UUID countryId;
    @Transient
    private Countries country;
    @Column("address")
    private String address;
    @Column("zip_code")
    private String zipCode;
    @Column("archived_at")
    private LocalDateTime archivedAt;
    @Column("city")
    private String city;
    @Column("state")
    private String state;

    @Builder
    public Addresses(UUID id, Status status, LocalDateTime created, LocalDateTime updated, UUID countryId,
                     Countries country, String address, String zipCode, LocalDateTime archivedAt, String city, String state) {
        super(id, status, created, updated);
        this.countryId = countryId;
        this.country = country;
        this.address = address;
        this.zipCode = zipCode;
        this.archivedAt = archivedAt;
        this.city = city;
        this.state = state;
    }
}