package com.gdula.foodieshub.repository;

import com.gdula.foodieshub.model.Recipe;
import com.gdula.foodieshub.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, String> {
    List<Recipe> findAll();
    List<Recipe> findAllByIdIn(List<String> itemIds);
    Recipe findFirstById(String id);
    @Query("SELECT i FROM Recipe i WHERE i.name LIKE %?1%")
    List<Recipe> findAllWithKeyword(String keyword);
}
