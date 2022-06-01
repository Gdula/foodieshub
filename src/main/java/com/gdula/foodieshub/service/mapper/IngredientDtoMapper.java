package com.gdula.foodieshub.service.mapper;

import com.gdula.foodieshub.model.Ingredient;
import com.gdula.foodieshub.service.dto.CreateUpdateIngredientDto;
import com.gdula.foodieshub.service.dto.IngredientDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IngredientDtoMapper {
    public IngredientDto toDto(Ingredient ingredient) {
        return new IngredientDto(ingredient.getId(), ingredient.getName(), ingredient.getQuantity(), ingredient.getRecipe());
    }

   public Ingredient toModel(CreateUpdateIngredientDto dto) {
    String randomId = UUID.randomUUID().toString();
    return new Ingredient(randomId, dto.getName(), dto.getQuantity(), dto.getRecipe());
   }

   public CreateUpdateIngredientDto toCreateUpdateIngredientDto(IngredientDto dto) {
        return new CreateUpdateIngredientDto(dto.getName(), dto.getQuantity(), dto.getRecipe());
   }
}
