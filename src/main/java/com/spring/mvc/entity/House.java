package com.spring.mvc.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "House")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "ward")
    private String ward;

    @Column(name = "district")
    private String district;

    @Column(name = "province")
    private String province;

    @Column(name = "location")
    private String location;

    @Column(name = "land_space", precision = 12, scale = 4)
    private BigDecimal land_space;

    @Column(name = "living_space", precision = 12, scale = 4)
    private BigDecimal living_space;

    @Column(name = "number_bed_room")
    private String number_bed_room;

    @Column(name = "number_bath")
    private String number_bath;

    @Column(name = "description")
    private String description;

    @Column(name = "coordinates_on_map")
    private String coordinates_on_map;

    @Column(name = "available_status")
    private String available_status;

    @Column(name = "updated_date")
    private String updated_date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "local_authority_id", referencedColumnName = "local_authority_id")
    private LocalAuthority localAuthority;
}
