package com.myfitbody.repositories;

import com.myfitbody.domain.food.ingredient.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {

    Page<Ingredient> findAllByNameIgnoreCaseStartingWith(Pageable pageable, String search);
}
