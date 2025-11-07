package com.bank.bank_rest.bankcards.model.entity;

import com.bank.bank_rest.bankcards.model.entity.enums.CardStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DialectOverride;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cards")
public class Cards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Users user;

    @Size(min = 16, max = 16)
    @Column(name = "card_number", nullable = false)
    String cardNumber;

    @Column(name = "Balance")
    BigDecimal balance;

    @Column(name = "creatDate")
    Date creatDate;

    @Column(name = "validityPeriod")
    Date validityPeriod;

    @Column(name = "cardStatus")
    CardStatus cardStatus;

    @Column(name = "cardIsDefault")
    Boolean defaultStatus;
}
