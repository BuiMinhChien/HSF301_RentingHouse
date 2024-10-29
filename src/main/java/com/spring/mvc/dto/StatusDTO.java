package com.spring.mvc.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusDTO {
    private int statusId;
    private String statusName;

    public StatusDTO() {
    }
}
