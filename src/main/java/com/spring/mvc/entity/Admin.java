package com.spring.mvc.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "Admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private int adminId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "admin_name", columnDefinition = "NVARCHAR(100)")
    private String adminName;

    public Admin() {
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", adminName='" + adminName + '\'' +
                '}';
    }
}