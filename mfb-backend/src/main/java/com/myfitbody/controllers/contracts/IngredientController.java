package com.myfitbody.controllers.contracts;

import com.myfitbody.controllers.HttpResponse;
import com.myfitbody.domain.food.ingredient.IngredientCreateDTO;
import com.myfitbody.domain.food.ingredient.IngredientResponseDTO;
import com.myfitbody.domain.food.ingredient.IngredientUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IngredientController {

    ResponseEntity<HttpResponse<Page<IngredientResponseDTO>>> getAllIngredients(Pageable pageable, String search);
    ResponseEntity<HttpResponse<IngredientResponseDTO>> getIngredientById(UUID id);
    ResponseEntity<HttpResponse<IngredientResponseDTO>> createIngredient(IngredientCreateDTO dto);
    ResponseEntity<HttpResponse<IngredientResponseDTO>> updateIngredient(UUID id, IngredientUpdateDTO dto);
    ResponseEntity<Void> deleteIngredient(UUID id);
}
