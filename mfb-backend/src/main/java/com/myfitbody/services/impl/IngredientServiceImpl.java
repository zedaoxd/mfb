package com.myfitbody.services.impl;

import com.myfitbody.domain.exceptions.DatabaseException;
import com.myfitbody.domain.exceptions.ResourceNotFoundException;
import com.myfitbody.domain.food.ingredient.Ingredient;
import com.myfitbody.domain.food.ingredient.IngredientCreateDTO;
import com.myfitbody.domain.food.ingredient.IngredientResponseDTO;
import com.myfitbody.domain.food.ingredient.IngredientUpdateDTO;
import com.myfitbody.mapper.IngredientMapper;
import com.myfitbody.repositories.IngredientRepository;
import com.myfitbody.services.contracts.IngredientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Override
    @Transactional
    public IngredientResponseDTO createIngredient(IngredientCreateDTO dto) {
        Ingredient ingredient = IngredientMapper.toEntity(dto);
        ingredient = ingredientRepository.save(ingredient);
        return IngredientMapper.toResponseDTO(ingredient);
    }

    @Override
    @Transactional(readOnly = true)
    public IngredientResponseDTO getIngredientById(UUID id) {
        Ingredient ingredient = ingredientRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
        return IngredientMapper.toResponseDTO(ingredient);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IngredientResponseDTO> getAllIngredients(Pageable pageable, String search) {
        return ingredientRepository
                .findAllByNameIgnoreCaseStartingWith(pageable, search)
                .map(IngredientMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public IngredientResponseDTO updateIngredient(UUID id, IngredientUpdateDTO dto) {
        var ingredient = ingredientRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
        IngredientMapper.copy(ingredient, dto);
        ingredient = ingredientRepository.save(ingredient);
        return IngredientMapper.toResponseDTO(ingredient);
    }

    @Override
    @Transactional
    public void deleteIngredientById(UUID id) {
        try {
            ingredientRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("ID not found:" + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
