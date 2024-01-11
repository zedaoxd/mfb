package com.myfitbody.domain.food.ingredient;

import com.myfitbody.domain.food.enums.FoodTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record IngredientCreateDTO(
        @NotBlank
        String name,
        @NotNull
        @PositiveOrZero
        double calories,
        @NotNull
        @PositiveOrZero
        double protein,
        @NotNull
        @PositiveOrZero
        double fat,
        @NotNull
        @PositiveOrZero
        double carbohydrates,
        @NotBlank
        String imgUrl,
        @NotNull
        FoodTime foodTime
) {
}
