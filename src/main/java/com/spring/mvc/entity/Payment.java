package com.spring.mvc.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @ManyToOne(fetch =FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "Customer_id", nullable = false)
    private Customer customer;

    @jakarta.persistence.JoinColumn(name = "house_id")
    private House house;

    private long paymentAmount;

    @Column(nullable = true)
    private LocalDateTime paymentDate;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String paymentDescription;

    public Payment(Customer customer, String paymentDescription, long paymentAmount) {
        this.customer = customer;
        this.paymentDescription = paymentDescription;
        this.paymentAmount = paymentAmount;
    }
}
