package com.spring.mvc.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "Fire_equipments")
public class FireEquipments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    public FireEquipments() {
    }

    @Override
    public String toString() {
        return "Fire_equipments{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
