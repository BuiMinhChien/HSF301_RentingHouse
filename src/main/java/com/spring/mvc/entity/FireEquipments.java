package com.spring.mvc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Fire_equipments")
public class FireEquipments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", columnDefinition = "NVARCHAR")
    private String name;

    @ManyToMany(mappedBy = "fireEquipments", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<House> houses;

    @Override
    public String toString() {
        return "Fire_equipments{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    public void addHouse(House house) {
        if (houses == null) {
            houses = new ArrayList<House>();
        }
        houses.add(house);
    }
}
