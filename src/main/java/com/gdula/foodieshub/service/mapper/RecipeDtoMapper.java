package com.gdula.foodieshub.service.mapper;

import com.gdula.foodieshub.model.Recipe;
import com.gdula.foodieshub.service.dto.CreateUpdateRecipeDto;
import com.gdula.foodieshub.service.dto.RecipeDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RecipeDtoMapper {
    public RecipeDto toDto(Recipe recipe) {
        return new RecipeDto(recipe.getId(), recipe.getName(), recipe.getDifficulty(), recipe.getDescription(),
                recipe.getMinutesToPrepare(), recipe.getCategory(), recipe.getImage(), recipe.getOwner(), recipe.getIngredients());
    }

    public Recipe toModel(CreateUpdateRecipeDto dto) {
        String randomId = UUID.randomUUID().toString();
        return new Recipe(randomId, dto.getName(), dto.getDifficulty(), dto.getDescription(), dto.getMinutesToPrepare(),
                dto.getCategory(), dto.getImage(), dto.getOwner(), dto.getIngredients());
    }

    public CreateUpdateRecipeDto toCreateUpdateRecipeDto(RecipeDto dto) {
        return new CreateUpdateRecipeDto(dto.getName(), dto.getDifficulty(), dto.getDescription(), dto.getMinutesToPrepare(), dto.getCategory(), dto.getImage(), dto.getOwner(), dto.getIngredients());
    }
}
