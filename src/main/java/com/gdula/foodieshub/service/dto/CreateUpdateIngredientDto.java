package com.gdula.foodieshub.service.dto;

import com.gdula.foodieshub.model.Ingredient;
import com.gdula.foodieshub.model.Recipe;
import com.gdula.foodieshub.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateIngredientDto {
    @Size(min = 3)
    private String name;
    @NotNull
    private String quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    private Recipe recipe;
}
