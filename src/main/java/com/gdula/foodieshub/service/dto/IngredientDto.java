package com.gdula.foodieshub.service.dto;

import com.gdula.foodieshub.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDto {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(columnDefinition = "varchar(100)")
    private String id;
    @Size(min = 3)
    private String name;
    @NotNull
    private String quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    private Recipe recipe;
}
