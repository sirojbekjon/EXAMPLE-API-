package com.example.api.payload;

import lombok.Data;


import javax.validation.constraints.NotNull;

@Data
public class CustomerDto {

    @NotNull(message = "Familya Bo'sh bo'lmasligi kerak")
    private String fullName;

    @NotNull(message = "telefon nomer bo'sh bo'lmasligi kerak")
    private String phoneNumber;

    @NotNull(message = "adres bo'sh bo'lmasligi kerak")
    private String address;
}
