package com.spring.mvc.dto;

import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.Customer;
import com.spring.mvc.entity.Staff;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileDTO {

    Customer customer;
    Account account;
    Staff staff;

}
