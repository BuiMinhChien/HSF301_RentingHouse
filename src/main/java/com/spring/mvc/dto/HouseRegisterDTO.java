package com.spring.mvc.dto;

import com.spring.mvc.entity.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HouseRegisterDTO {
     String name;//House
     String province;//House
     String district;//House
     String ward;//House
     String location;//House
     BigDecimal land_space;//House
     BigDecimal living_space;//House
     String number_bed_room;//House
     String number_bath;//House
     String description;//House
     String coordinates_on_map;//House
     String available_status;//House
     String updated_date;//House
     String HouseOwnerName;//HouseOwner
     String HouseOwnerPhone;//HouseOwner
     String HouseOwnerAddress;//HouseOwner
     Account updated_by;
     List<Tag> tagList;
     List<Amenities> amenities;
     List<FireEquipments> fireEquipments;
     List<MultipartFile> images;
     List<MultipartFile> documents;//Contract
     long price;//Contract
     BigDecimal lease_duration_day;//Contract
     String created_date;//Contract







}
