package com.myfitbody.domain.food.ingredient;

import com.myfitbody.domain.food.enums.FoodTime;
import lombok.Builder;

import java.util.UUID;

@Builder
public record IngredientResponseDTO(
        UUID id,
        String name,
        double calories,
        double protein,
        double fat,
        double carbohydrates,
        String imgUrl,
        FoodTime foodTime,
        Boolean isChecked) {
}
