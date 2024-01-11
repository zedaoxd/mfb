package com.myfitbody.controllers.impl;

import com.myfitbody.controllers.HttpResponse;
import com.myfitbody.controllers.contracts.IngredientController;
import com.myfitbody.domain.food.ingredient.IngredientCreateDTO;
import com.myfitbody.domain.food.ingredient.IngredientResponseDTO;
import com.myfitbody.domain.food.ingredient.IngredientUpdateDTO;
import com.myfitbody.services.contracts.IngredientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ingredients")
public class IngredientControllerImpl implements IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    @Override
    public ResponseEntity<HttpResponse<Page<IngredientResponseDTO>>> getAllIngredients(
            @PageableDefault(size = 20, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "")
            String search) {
        var ingredients = ingredientService.getAllIngredients(pageable, search);
        return ResponseEntity.ok(HttpResponse.<Page<IngredientResponseDTO>>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Fetching ingredients")
                .data(Map.of("ingredients", ingredients))
                .timestamp(Instant.now())
                .build()
        );
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse<IngredientResponseDTO>> getIngredientById(@PathVariable UUID id) {
        var ingredient = ingredientService.getIngredientById(id);
        return ResponseEntity.ok(HttpResponse.<IngredientResponseDTO>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Fetching ingredient")
                .data(Map.of("ingredient", ingredient))
                .timestamp(Instant.now())
                .build()
        );
    }

    @Override
    @PostMapping
    public ResponseEntity<HttpResponse<IngredientResponseDTO>> createIngredient(@RequestBody @Valid IngredientCreateDTO dto) {
        var ingredient = ingredientService.createIngredient(dto);
        URI uri = URI.create("/api/v1/ingredients/" + ingredient.id());
        return ResponseEntity.created(uri).body(HttpResponse.<IngredientResponseDTO>builder()
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .message("Ingredient created")
                .data(Map.of("ingredient", ingredient))
                .timestamp(Instant.now())
                .build()
        );
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<HttpResponse<IngredientResponseDTO>> updateIngredient(@PathVariable UUID id, @RequestBody @Valid IngredientUpdateDTO dto) {
        var ingredient = ingredientService.updateIngredient(id, dto);
        return ResponseEntity.ok(HttpResponse.<IngredientResponseDTO>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Ingredient updated")
                .data(Map.of("ingredient", ingredient))
                .timestamp(Instant.now())
                .build()
        );
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteIngredient(@PathVariable UUID id) {
        ingredientService.deleteIngredientById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
