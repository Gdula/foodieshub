package com.gdula.foodieshub.view;

import com.gdula.foodieshub.model.Recipe;
import com.gdula.foodieshub.service.RecipeService;
import com.gdula.foodieshub.service.dto.CreateUpdateRecipeDto;
import com.gdula.foodieshub.service.dto.RecipeDto;
import com.gdula.foodieshub.service.mapper.RecipeDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
public class RecipeViewController {
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RecipeDtoMapper recipeDtoMapper;

    @GetMapping("/recipes")
    public ModelAndView displayRecipesTable() {
        ModelAndView mav = new ModelAndView("recipes-table");
        List<RecipeDto> allRecipes = recipeService.getAllRecipes();
        mav.addObject("recipes", allRecipes);

        return mav;
    }

    @GetMapping("/create-recipe")
    public String displayCreateRecipeForm(Model model) {
        CreateUpdateRecipeDto dto = new CreateUpdateRecipeDto();
        model.addAttribute("difficultyList", Arrays.asList(Recipe.Difficulty.values()));
        model.addAttribute("categoryList", Arrays.asList(Recipe.Category.values()));
        model.addAttribute("dto", dto);
        return "create-recipe-form";
    }


}
