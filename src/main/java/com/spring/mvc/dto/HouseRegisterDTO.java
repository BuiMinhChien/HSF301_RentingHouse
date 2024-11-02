package com.spring.mvc.dto;

import com.spring.mvc.entity.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HouseRegisterDTO {
     String name;
     String province;
     String district;
     String ward;
     String location;
     BigDecimal land_space;
     BigDecimal living_space;
     String number_bed_room;
     String number_bath;
     String description;
     String coordinates_on_map;
     String available_status;
     String updated_date;
     HouseOwner owner;
     Account updated_by;
     List<Tag> tagList;
     List<Amenities> amenities;
     List<FireEquipments> fireEquipments;
     List<MultplePa>

}
