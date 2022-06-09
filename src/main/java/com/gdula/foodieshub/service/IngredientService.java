package com.gdula.foodieshub.service;

import com.gdula.foodieshub.model.Ingredient;
import com.gdula.foodieshub.model.Recipe;
import com.gdula.foodieshub.model.User;
import com.gdula.foodieshub.repository.IngredientRepository;
import com.gdula.foodieshub.repository.RecipeRepository;
import com.gdula.foodieshub.repository.UserRepository;
import com.gdula.foodieshub.service.dto.CreateUpdateIngredientDto;
import com.gdula.foodieshub.service.dto.IngredientDto;
import com.gdula.foodieshub.service.dto.RecipeDto;
import com.gdula.foodieshub.service.exception.IngredientDataInvalid;
import com.gdula.foodieshub.service.exception.IngredientNotFound;
import com.gdula.foodieshub.service.exception.RecipeNotFound;
import com.gdula.foodieshub.service.mapper.IngredientDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private IngredientDtoMapper ingredientDtoMapper;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private RecipeService recipeService;

    public IngredientDto createIngredient(CreateUpdateIngredientDto dto) throws IngredientDataInvalid {
        if (!isIngredientValid(dto)) {
            throw new IngredientDataInvalid();
        }

        Ingredient ingredientToSave = ingredientDtoMapper.toModel(dto);
        //todo zastanowić się czy najpierw tworzymy recipe, czy ingredient i co najpierw zapisywać
        Recipe IngredientRecipe = recipeRepository.findFirstById(dto.getRecipe().getId());
        ingredientToSave.setRecipe(IngredientRecipe);

        Ingredient savedIngredient = ingredientRepository.save(ingredientToSave);
        return ingredientDtoMapper.toDto(savedIngredient);
    }

    public IngredientDto updateIngredient(CreateUpdateIngredientDto dto, String id) throws IngredientDataInvalid, IngredientNotFound {
        if (!isIngredientValid(dto)) {
            throw new IngredientDataInvalid();
        }
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(IngredientNotFound::new);
        ingredient.setName(dto.getName());
        ingredient.setQuantity(dto.getQuantity());
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return ingredientDtoMapper.toDto(savedIngredient);
    }

    public List<IngredientDto> getAllIngredients() {
        return ingredientRepository.findAll()
                .stream()
                .map(i -> ingredientDtoMapper.toDto(i))
                .collect(Collectors.toList());
    }

    public IngredientDto getIngredientById(String id) throws IngredientNotFound {
        return ingredientRepository.findById(id)
                .map((i -> ingredientDtoMapper.toDto(i)))
                .orElseThrow(IngredientNotFound::new);
    }

    public IngredientDto deleteIngredientById(String id) throws IngredientNotFound {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(IngredientNotFound::new);
        ingredientRepository.delete(ingredient);
        return ingredientDtoMapper.toDto(ingredient);
    }

    public List<IngredientDto> getAllIngredientsWithKeyword(String keyword) {
        if (Objects.isNull(keyword)) {
            return getAllIngredients();
        }
        return ingredientRepository.findAllWithKeyword(keyword)
                    .stream()
                    .map(i -> ingredientDtoMapper.toDto(i))
                    .collect(Collectors.toList());
    }

    public List<IngredientDto> getIngredientsByRecipeId(String id) throws RecipeNotFound {
        return recipeService.getRecipeById(id)
                .getIngredients()
                .stream()
                .map(i -> ingredientDtoMapper.toDto(i))
                .collect(Collectors.toList());
    }

    boolean isIngredientValid(CreateUpdateIngredientDto dto) {
        return dto.getName() != null && !dto.getName().isEmpty() && dto.getQuantity() != null;
    }
}
