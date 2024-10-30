package com.spring.mvc.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "Amenities")
public class Amenities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    public Amenities() {
    }

    @Override
    public String toString() {
        return "Amenities{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
