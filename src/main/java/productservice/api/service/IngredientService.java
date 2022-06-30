package productservice.api.service;

import productservice.api.entity.Ingredient;
import productservice.api.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productservice.api.exception.IngredientNotFoundException;

import java.util.List;


@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredient(Long id) {
        return ingredientRepository.findById(id).orElseThrow(()->
                new IngredientNotFoundException("Ingredient with id: " + id + " not found in Database"));
    }
}
