package com.gdula.foodieshub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(columnDefinition = "varchar(100)")
    private String id;
    @Size(min = 3)
    private String name;
    @NotNull
    private Difficulty difficulty;
    public static enum Difficulty {
        Łatwe, Średnie, Trudne
    }
    @NotBlank
    @Size(min = 3)
    String description;
    @NotNull
    Integer minutesToPrepare;
    @NotNull
    private Category category;
    public static enum Category {
        Deser, Obiad, Kolacja, Przekąska
    }
    @Lob
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;

    @Column(name="ingredients")
    @ElementCollection(targetClass=Ingredient.class)
    private List<Ingredient> ingredients;

}
