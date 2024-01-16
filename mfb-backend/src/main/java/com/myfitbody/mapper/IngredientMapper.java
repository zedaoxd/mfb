package com.myfitbody.mapper;

import com.myfitbody.domain.food.ingredient.Ingredient;
import com.myfitbody.domain.food.ingredient.IngredientCreateDTO;
import com.myfitbody.domain.food.ingredient.IngredientResponseDTO;
import com.myfitbody.domain.food.ingredient.IngredientUpdateDTO;

public class IngredientMapper {

    public static Ingredient toEntity(IngredientCreateDTO dto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(dto.name());
        ingredient.setCalories(dto.calories());
        ingredient.setProtein(dto.protein());
        ingredient.setFat(dto.fat());
        ingredient.setCarbohydrates(dto.carbohydrates());
        ingredient.setImgUrl(dto.imgUrl());
        ingredient.setIsChecked(false);
        ingredient.setFoodTime(dto.foodTime());
        return ingredient;
    }

    public static void copy(Ingredient destination, IngredientUpdateDTO source) {
        destination.setName(source.name());
        destination.setCalories(source.calories());
        destination.setProtein(source.protein());
        destination.setFat(source.fat());
        destination.setCarbohydrates(source.carbohydrates());
        destination.setImgUrl(source.imgUrl());
        destination.setIsChecked(source.isChecked());
        destination.setFoodTime(source.foodTime());
    }

    public static IngredientResponseDTO toResponseDTO(Ingredient ingredient) {
        return IngredientResponseDTO.builder()
                .calories(ingredient.getCalories())
                .carbohydrates(ingredient.getCarbohydrates())
                .fat(ingredient.getFat())
                .id(ingredient.getId())
                .imgUrl(ingredient.getImgUrl())
                .name(ingredient.getName())
                .protein(ingredient.getProtein())
                .isChecked(ingredient.getIsChecked())
                .foodTime(ingredient.getFoodTime())
                .build();
    }
}
