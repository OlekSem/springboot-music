package com.example.springbootmusic.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RegisterUserDTO {
    @NotBlank
    private String username;

    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;


    private MultipartFile image;
}
