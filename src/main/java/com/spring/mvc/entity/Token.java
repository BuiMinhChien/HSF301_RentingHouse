package com.spring.mvc.entity;

import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "Token")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;

    @OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    @JoinColumn( name = "account_id")
    private Account account;

    @Column(name = "expiry_date")
    private Date expiryDate;
}
