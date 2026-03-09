package org.example.helloapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostRequestDto {
    @NotNull(message = "Content is required")
    @Size(min = 10, message = "Content must be less than 10 characters")
    private String content;
    private MultipartFile attachment;
}
