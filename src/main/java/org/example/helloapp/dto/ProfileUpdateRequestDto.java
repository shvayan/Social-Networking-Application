package org.example.helloapp.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProfileUpdateRequestDto {
    @NotNull
    private String name;

    private String about;

    private Long phone_number;

}
