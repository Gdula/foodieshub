package com.gdula.foodieshub.service;

import com.gdula.foodieshub.model.Recipe;
import com.gdula.foodieshub.model.User;
import com.gdula.foodieshub.repository.RecipeRepository;
import com.gdula.foodieshub.repository.UserRepository;
import com.gdula.foodieshub.service.dto.CreateUpdateRecipeDto;
import com.gdula.foodieshub.service.dto.RecipeDto;
import com.gdula.foodieshub.service.exception.RecipeDataInvalid;
import com.gdula.foodieshub.service.exception.RecipeNotFound;
import com.gdula.foodieshub.service.exception.UserNotFound;
import com.gdula.foodieshub.service.mapper.RecipeDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeDtoMapper recipeDtoMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private UserService userService;

    public RecipeDto createRecipe(CreateUpdateRecipeDto dto, MultipartFile file) throws RecipeDataInvalid {
        if (!isRecipeValid(dto))
            throw new RecipeDataInvalid();
        Recipe recipeToSave = recipeDtoMapper.toModel(dto);
        User owner = userRepository.findFirstByLogin(securityUtils.getUserName());
        recipeToSave.setOwner(owner);

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        System.out.println(fileName);
        try {
            recipeToSave.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Recipe savedItem = recipeRepository.save(recipeToSave);
        return recipeDtoMapper.toDto(savedItem);
    }


    private boolean isRecipeValid(CreateUpdateRecipeDto dto) {
        return dto.getName() != null && !dto.getName().isEmpty() && dto.getDifficulty() != null &&
                dto.getDescription() != null && !dto.getDescription().isEmpty() && dto.getMinutesToPrepare() != null &&
                dto.getCategory() != null && dto.getImage() != null && dto.getOwner() != null && dto.getIngredients() != null;
    }

    public RecipeDto updateRecipe(CreateUpdateRecipeDto dto, String id) throws RecipeNotFound, RecipeDataInvalid {
        if (!isRecipeValid(dto))
            throw new RecipeDataInvalid();

        Recipe recipe = recipeRepository.findById(id).orElseThrow(RecipeNotFound::new);

        recipe.setName(dto.getName());
        recipe.setDifficulty(dto.getDifficulty());
        recipe.setDescription(dto.getDescription());
        recipe.setCategory(dto.getCategory());
        recipe.setMinutesToPrepare(dto.getMinutesToPrepare());
        recipe.setImage(dto.getImage());
        recipe.setIngredients(dto.getIngredients());
        Recipe savedRecipe = recipeRepository.save(recipe);

        return recipeDtoMapper.toDto(savedRecipe);
    }

    public List<RecipeDto> getAllRecipes() {
        return recipeRepository.findAll()
                .stream()
                .map(r -> recipeDtoMapper.toDto(r))
                .collect(Collectors.toList());
    }

    public RecipeDto getRecipeById(String id) throws RecipeNotFound {
        return recipeRepository.findById(id)
                .map(r -> recipeDtoMapper.toDto(r))
                .orElseThrow(RecipeNotFound::new);
    }

    public RecipeDto deleteRecipeById(String id) throws RecipeNotFound {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(RecipeNotFound::new);

        recipeRepository.delete(recipe);
        return recipeDtoMapper.toDto(recipe);
    }

    public List<RecipeDto> getAllRecipesWithKeyword(String keyword) {
        if (keyword != null) {
            return recipeRepository.findAllWithKeyword(keyword)
                    .stream()
                    .map(r -> recipeDtoMapper.toDto(r))
                    .collect(Collectors.toList());
        }
        return getAllRecipes();
    }

    public List<RecipeDto> getLoggedUserRecipes() {
        User user = userRepository.findFirstByLogin(securityUtils.getUserName());
        List<Recipe> recipes = recipeRepository.findAll();
        List<Recipe> userRecipes = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getOwner().equals(user)) {
                userRecipes.add(recipe);
            }
        }

        return userRecipes.stream()
                .map(i -> recipeDtoMapper.toDto(i))
                .collect(Collectors.toList());
    }

    public List<RecipeDto> getAllUserRecipesByUserId(String id) throws UserNotFound {
        return userService.getUserById(id)
                .getRecipes()
                .stream()
                .map(r -> recipeDtoMapper.toDto(r))
                .collect(Collectors.toList());
    }


}
