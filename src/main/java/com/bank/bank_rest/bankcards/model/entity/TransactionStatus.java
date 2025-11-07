package com.bank.bank_rest.bankcards.model.entity;

import com.bank.bank_rest.bankcards.model.entity.enums.TransactionalStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TransactionStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING) // 👈 важно, если храним enum как текст
    TransactionalStatus status;
}
