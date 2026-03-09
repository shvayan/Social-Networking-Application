package org.example.helloapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostFilterRequest {

    private int page;
    @NotNull
    private int size;
    private String search;
}
