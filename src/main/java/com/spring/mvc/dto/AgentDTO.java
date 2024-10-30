package com.spring.mvc.dto;

import com.spring.mvc.validator.ValidDate;
import com.spring.mvc.validator.ValidNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AgentDTO {
    private int agentId;
    @NotBlank(message = "{user.email.notnull}")
    @Email(message = "{user.email.invalid}")
    private String email;
    @NotNull(message = "Name is required.")
    @Size(min = 10, max = 50, message = "{user.name.size}")
    private String agentName;
    @NotBlank(message = "Address is required.")
    private String address;
    @NotNull(message = "Status is required.")
    private Status status;
    @NotNull(message = "Registration date is required")
    @ValidDate(pattern = "dd/MM/yyyy", message = "{valid.date}")
    private String registrationDate;
    @ValidNumber(min = 1, message = "{valid.number}")
    private String accountBalance;

    public AgentDTO() {
    }

    @Override
    public String toString() {
        return "AgentDTO{" +
                "accountBalance='" + accountBalance + '\'' +
                ", agentId=" + agentId +
                ", email='" + email + '\'' +
                ", agentName='" + agentName + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", registrationDate='" + registrationDate + '\'' +
                '}';
    }
}
