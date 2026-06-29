package com.uni.lostandfound.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email is not valid")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;
}
