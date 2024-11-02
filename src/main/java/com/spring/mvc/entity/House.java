package com.spring.mvc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name" , columnDefinition = "NVARCHAR(MAX)")
    private String name;

    @Column(name = "ward" , columnDefinition = "NVARCHAR(MAX)")
    private String ward;

    @Column(name = "district" , columnDefinition = "NVARCHAR(MAX)")
    private String district;

    @Column(name = "province" , columnDefinition = "NVARCHAR(MAX)")
    private String province;

    @Column(name = "location" , columnDefinition = "NVARCHAR(MAX)")
    private String location;

    @Column(name = "land_space", precision = 12, scale = 4)
    private BigDecimal land_space;

    @Column(name = "living_space", precision = 12, scale = 4)
    private BigDecimal living_space;

    @Column(name = "number_bed_room")
    private String number_bed_room;

    @Column(name = "number_bath")
    private String number_bath;

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "coordinates_on_map", columnDefinition = "NVARCHAR(MAX)")
    private String coordinates_on_map;

    @Column(name = "available_status")
    private String available_status;

    @Column(name = "updated_date")
    private String updated_date;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "owner_by")
    private HouseOwner owner;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "updated_by")
    private Account updated_by;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "Tag_House",
            joinColumns = @JoinColumn(name = "house_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tagList;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "Amenities_House",
            joinColumns = @JoinColumn(name = "house_id"),
            inverseJoinColumns = @JoinColumn(name = "amenities_id")
    )
    private List<Amenities> amenities;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "Fire_equipments_House",
            joinColumns = @JoinColumn(name = "house_id"),
            inverseJoinColumns = @JoinColumn(name = "fire_id")
    )
    private List<FireEquipments> fireEquipments;

    @OneToMany(mappedBy = "house",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Image> images;

    @OneToMany(mappedBy = "house",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Contract> contracts;
}
