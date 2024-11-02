package com.spring.mvc.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusDTO {
    private int statusId;
    private String statusName;

    public StatusDTO() {
    }
}
