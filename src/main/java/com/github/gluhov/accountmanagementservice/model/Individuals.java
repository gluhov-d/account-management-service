package com.github.gluhov.accountmanagementservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;
import java.util.UUID;

@Table("individuals")
@Data
@NoArgsConstructor
public class Individuals implements Persistable<UUID> {
    @Id
    @Column("id")
    private UUID id;
    @Transient
    private Users user;
    @Column("user_id")
    private UUID userId;
    @Column("passport_number")
    private String passportNumber;
    @Column("email")
    private String email;
    @Column("phone_number")
    private String phoneNumber;
    @Transient
    private String password;
    @Builder
    public Individuals(UUID id, Users user, UUID userId, String passportNumber, String email, String phoneNumber, String password) {
        this.id = id;
        this.user = user;
        this.userId = userId;
        this.passportNumber = passportNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    @Override
    @Transient
    public boolean isNew() {
        boolean result = Objects.isNull(id);
        this.id = result ? UUID.randomUUID() : this.id;
        return result;
    }
}