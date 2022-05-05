package com.gdula.foodieshub.service.dto;

import com.gdula.foodieshub.model.Ingredient;
import com.gdula.foodieshub.model.Recipe;
import com.gdula.foodieshub.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateRecipeDto {
    @Size(min = 3)
    private String name;
    @NotNull
    private Recipe.Difficulty difficulty;
    public static enum Difficulty {
        Łatwe, Średnie, Trudne
    }
    @NotBlank
    @Size(min = 3)
    String description;
    @NotNull
    Integer minutesToPrepare;
    @NotNull
    private Recipe.Category category;
    public static enum Category {
        Deser, Obiad, Kolacja, Przekąska
    }
    @Lob
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;
}
