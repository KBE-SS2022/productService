package productservice.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import productservice.api.dto.PizzaDTO;
import productservice.api.entity.Ingredient;
import productservice.api.entity.Pizza;
import productservice.api.exception.IngredientNotFoundException;
import productservice.api.repository.IngredientRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PizzaDTOMapper {

    @Autowired
    private IngredientRepository ingredientRepository;

    public PizzaDTO toPizzaDTO(Pizza pizza){
        Long id = pizza.getId();
        String name = pizza.getName();
        List<Long> ingredientIDs = pizza.getIngredients().stream()
                .map(Ingredient::getId).collect(Collectors.toList());

        return new PizzaDTO(id, name, ingredientIDs);
    }

    public Pizza toPizza(PizzaDTO pizzaDTO){
        Long id = pizzaDTO.getId();
        String name = pizzaDTO.getName();
        List<Ingredient> ingredients = pizzaDTO.getIngredientIDs().stream()
                .map(ingredient_id -> {
                    Optional<Ingredient> maybeIngredient = ingredientRepository.findById(ingredient_id);
                    if(maybeIngredient.isPresent()) return maybeIngredient.get();
                    else throw new IngredientNotFoundException(ingredient_id);
                })
                .collect(Collectors.toList());

        return new Pizza(id, name, ingredients);
    }
}