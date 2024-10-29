package com.spring.mvc.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Agent")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agent_id")
    private int agentId;

    @Column(name = "email")
    private String email;

    @Column(name = "agent_name", columnDefinition = "NVARCHAR(100)")
    private String agentName;

    @Column(name = "address", columnDefinition = "NVARCHAR(MAX)")
    private String address;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    private Status status;

    @Column(name = "registration_date")
    private Date registrationDate;

    @Column(name = "account_balance")
    private int accountBalance;

    public Agent() {
    }

    @Override
    public String toString() {
        return "Agent{" +
                "agentId=" + agentId +
                ", email='" + email + '\'' +
                ", agentName='" + agentName + '\'' +
                ", address='" + address + '\'' +
                ", registrationDate=" + registrationDate +
                ", status=" + status +
                ", accountBalance=" + accountBalance +
                '}';
    }
}
