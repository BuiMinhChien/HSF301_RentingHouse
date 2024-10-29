package com.spring.mvc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
public class AdminDTO {
    private int adminId;
    @NotNull(message = "{user.email.notnull}")
    @Email(message = "{user.email.invalid}")
    private String email;
    @NotNull(message = "{user.password.notnull}")
    @Size(min = 1, max = 10, message = "{user.password.size}")
    private String password;
    private String adminName;

    public AdminDTO() {
    }
}
