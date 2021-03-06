package com.gdula.foodieshub.service.dto;

import com.gdula.foodieshub.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotBlank
    private String id;
    @NotBlank
    @Size(min = 2)
    private String login;
    @NotBlank
    @Size(min = 3)
    private String name;
    @NotBlank
    @Size(min = 3)
    private String surname;
    @Size(min = 8)
    private String password;
    @NotBlank
    @Size(min = 3)
    private String address;
    @NotBlank
    @Size(min = 3)
    private String city;
    @NotBlank
    @Size(min = 6)
    private String zip;
    @NotBlank
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$",
            message = "Podany mail jest nieprawidłowy")
    private String mail;
    private List<Recipe> recipes;
}
