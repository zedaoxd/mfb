package com.myfitbody.services.contracts;

import com.myfitbody.domain.food.ingredient.IngredientCreateDTO;
import com.myfitbody.domain.food.ingredient.IngredientResponseDTO;
import com.myfitbody.domain.food.ingredient.IngredientUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IngredientService {

    IngredientResponseDTO createIngredient(IngredientCreateDTO dto);

    IngredientResponseDTO getIngredientById(UUID id);

    Page<IngredientResponseDTO> getAllIngredients(Pageable pageable, String search);

    IngredientResponseDTO updateIngredient(UUID id, IngredientUpdateDTO dto);

    void deleteIngredientById(UUID id);
}
