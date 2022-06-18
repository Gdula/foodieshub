package com.gdula.foodieshub.view;

import com.gdula.foodieshub.model.Ingredient;
import com.gdula.foodieshub.model.Recipe;
import com.gdula.foodieshub.service.IngredientService;
import com.gdula.foodieshub.service.RecipeService;
import com.gdula.foodieshub.service.dto.CreateUpdateRecipeDto;
import com.gdula.foodieshub.service.dto.RecipeDto;
import com.gdula.foodieshub.service.exception.RecipeDataInvalid;
import com.gdula.foodieshub.service.exception.RecipeNotFound;
import com.gdula.foodieshub.service.exception.UserNotFound;
import com.gdula.foodieshub.service.mapper.RecipeDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Controller
public class RecipeViewController {
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RecipeDtoMapper recipeDtoMapper;
    @Autowired
    private IngredientService ingredientService;

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
        dto.setIngredients(new ArrayList<>());
        dto.addIngredient(new Ingredient());

        model.addAttribute("difficultyList", Arrays.asList(Recipe.Difficulty.values()));
        model.addAttribute("categoryList", Arrays.asList(Recipe.Category.values()));
        model.addAttribute("dto", dto);
        return "create-recipe-form";
    }

    @PostMapping("/create-recipe")
    public String createRecipe(@Valid @ModelAttribute(name = "dto") CreateUpdateRecipeDto dto,
                               BindingResult bindingResult, Model model, @RequestParam("file") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("recipes", recipeService.getAllRecipes());
        }
        try {
            recipeService.createRecipe(dto, file);
        } catch (RecipeDataInvalid e) {
            e.printStackTrace();
            model.addAttribute("dto", dto);
            model.addAttribute("recipes", recipeService.getAllRecipes());

            return "create-recipe-form";
        }
        return "redirect:/recipes";
    }

    @GetMapping("/show-recipe/{id}")
    public ModelAndView displayRecipe(@PathVariable String id) {
        try {
            RecipeDto recipeById = recipeService.getRecipeById(id);

            List<Ingredient> ingredients = recipeById.getIngredients();

            Map<String, String> ingredientsNamesQuantities = getIngredientsMap(ingredients);

            ModelAndView mav = new ModelAndView("show-recipe");
            mav.addObject("recipe", recipeById);
            mav.addObject("ingredientsNamesQuantities", ingredientsNamesQuantities);

            return mav;
        } catch (RecipeNotFound e) {
            e.printStackTrace();
            return new ModelAndView("redirect:/recipes");
        }
    }

    private Map<String, String> getIngredientsMap(List<Ingredient> ingredients) {
        List<String> ingredientsNames = Stream.of(ingredients.get(0).getName().split(",", -1))
                .collect(Collectors.toList());
        List<String> ingredientsQuantities = Stream.of(ingredients.get(0).getQuantity().split(",", -1))
                .collect(Collectors.toList());
        return zipToMap(ingredientsNames, ingredientsQuantities);
    }

    public static <K, V> Map<K, V> zipToMap(List<K> keys, List<V> values) {
        return IntStream.range(0, keys.size()).boxed()
                .collect(Collectors.toMap(keys::get, values::get));
    }

    @GetMapping("/delete-recipe/{id}")
    public String deleteRecipe(@PathVariable String id) {
        try {
            recipeService.deleteRecipeById(id);
        } catch (RecipeNotFound e) {
            e.printStackTrace();
        }

        return "redirect:/my-recipes";
    }

    @GetMapping("/update-recipe/{id}")
    public ModelAndView displayUpdateRecipeForm(@PathVariable String id) {
        try {
            RecipeDto recipeById = recipeService.getRecipeById(id);
            CreateUpdateRecipeDto updateRecipeDto = recipeDtoMapper.toCreateUpdateRecipeDto(recipeById);
            ModelAndView mav = new ModelAndView("update-recipe-form");
            mav.addObject("dto", updateRecipeDto);
            mav.addObject("id", id);
            mav.addObject("difficultyList", Arrays.asList(Recipe.Difficulty.values()));
            mav.addObject("categoryList", Arrays.asList(Recipe.Category.values()));
            return mav;
        } catch (RecipeNotFound e) {
            e.printStackTrace();
            return new ModelAndView("redirect:/recipes");
        }
    }

    @PostMapping("/update-recipe/{id}")
    public String updateRecipe(@ModelAttribute CreateUpdateRecipeDto dto, @PathVariable String id,
                               @RequestParam("file") MultipartFile file) {
        try {
            recipeService.updateRecipe(dto, id, file);
            return "redirect:/my-recipes";
        } catch (RecipeNotFound | RecipeDataInvalid e) {
            e.printStackTrace();
            return "redirect:/my-recipes";
        }
    }

    @GetMapping("/my-recipes")
    public ModelAndView showLoggedUserRecipes() {
        List<RecipeDto> userRecipes = recipeService.getLoggedUserRecipes();
        ModelAndView mav = new ModelAndView("user-recipes-table");
        mav.addObject("recipes", userRecipes);

        return mav;
    }

    @GetMapping("/user-recipes-table/{id}")
    public ModelAndView showUserRecipes(@PathVariable String id) {
        try {
            List<RecipeDto> userRecipes = recipeService.getAllUserRecipesByUserId(id);
            ModelAndView mav = new ModelAndView("user-recipes-table");
            mav.addObject("recipes", userRecipes);
            return mav;
        } catch (UserNotFound userNotFound) {
            userNotFound.printStackTrace();
            return new ModelAndView("redirect:/user/" + id);
        }
    }

    @RequestMapping("/search-recipe")
    public ModelAndView showRecipeSearchResult(@Param("keyword") String keyword) {
        List<RecipeDto> recipes = recipeService.getAllRecipesWithKeyword(keyword);
        ModelAndView mav = new ModelAndView("recipes-table");
        mav.addObject("recipes", recipes);
        return mav;
    }

}
