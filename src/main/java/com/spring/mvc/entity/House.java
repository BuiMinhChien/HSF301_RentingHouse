package com.spring.mvc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Column(name = "name", columnDefinition = "NVARCHAR(MAX)")
    private String name;

    @Column(name = "ward", columnDefinition = "NVARCHAR(MAX)")
    private String ward;

    @Column(name = "district", columnDefinition = "NVARCHAR(MAX)")
    private String district;

    @Column(name = "province", columnDefinition = "NVARCHAR(MAX)")
    private String province;

    @Column(name = "location", columnDefinition = "NVARCHAR(MAX)")
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

    @OneToMany(mappedBy = "house", fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Image> images;

    @OneToOne(mappedBy = "house", fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Contract contract;

    @OneToMany(mappedBy = "house", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<HouseRegister> registers;

    public House(String name, String ward, String district, String province, String location, BigDecimal land_space, BigDecimal living_space, String number_bed_room, String description, String number_bath, String coordinates_on_map, String available_status, String updated_date, Account updated_by) {
        this.name = name;
        this.ward = ward;
        this.district = district;
        this.province = province;
        this.location = location;
        this.land_space = land_space;
        this.living_space = living_space;
        this.number_bed_room = number_bed_room;
        this.description = description;
        this.number_bath = number_bath;
        this.coordinates_on_map = coordinates_on_map;
        this.available_status = available_status;
        this.updated_date = updated_date;
        this.updated_by = updated_by;
    }

    public void addAmenities(Amenities amenities) {
        if (this.amenities == null) {
            this.amenities = new ArrayList<>();
        }
        this.amenities.add(amenities);
    }

    public void addFireEquipments(FireEquipments fireEquipments) {
        if (this.fireEquipments == null) {
            this.fireEquipments = new ArrayList<>();
        }
        this.fireEquipments.add(fireEquipments);
    }

    public void addRegister(HouseRegister register) {
        if (this.registers == null) {
            this.registers = new ArrayList<>();
        }
        this.registers.add(register);
    }
    public void addImage(Image image) {
        if (this.images == null) {
            this.images = new ArrayList<>();
        }
        this.images.add(image);
//        image.setHouse(this);
    }


    public void addImages(Image image) {
        if (this.images == null) {
            this.images = new ArrayList<>();
        }
        this.images.add(image);
        image.setHouse(this);
    }

}
