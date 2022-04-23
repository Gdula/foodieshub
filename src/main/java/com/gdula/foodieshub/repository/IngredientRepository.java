package com.gdula.foodieshub.repository;

import com.gdula.foodieshub.model.Ingredient;
import com.gdula.foodieshub.model.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
    List<Ingredient> findAll();
    List<Ingredient> findAllByIdIn(List<String> itemIds);

    @Query("SELECT i FROM Ingredient i WHERE i.name LIKE %?1%")
    List<Recipe> findAllWithKeyword(String keyword);
}
