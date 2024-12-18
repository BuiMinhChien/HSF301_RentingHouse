package com.spring.mvc.dto;


import com.spring.mvc.entity.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegisterDTO {
    private String username;
    private String password;
    private String email;
    private Role role;
    private String verify;
}
