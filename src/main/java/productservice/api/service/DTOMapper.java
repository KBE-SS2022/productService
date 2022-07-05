package productservice.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import productservice.api.dto.IngredientDTO;
import productservice.api.dto.PizzaDTO;
import productservice.api.entity.Ingredient;
import productservice.api.entity.Pizza;
import productservice.api.exception.IngredientNotFoundException;
import productservice.api.exception.PizzaNotFoundException;
import productservice.api.repository.IngredientRepository;
import productservice.api.repository.PizzaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DTOMapper {

    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private PizzaRepository pizzaRepository;

    public IngredientDTO toIngredientDTO(Ingredient ingredient){
        Long id = ingredient.getId();
        String name = ingredient.getName();
        String brand = ingredient.getBrand();
        String countryOrigin = ingredient.getCountryOrigin();
        char nutritionScore = ingredient.getNutritionScore();
        Integer calories = ingredient.getCalories();
        Integer amount = ingredient.getAmount();
        Double weight = ingredient.getWeight();
        Double price = ingredient.getPrice();
        List<Long> pizzaIDs = ingredient.getPizzaIDs();

        return new IngredientDTO(id, name, brand, countryOrigin, nutritionScore, calories, amount, weight, price, pizzaIDs);
    }

    public PizzaDTO toPizzaDTO(Pizza pizza){
        Long id = pizza.getId();
        String name = pizza.getName();
        List<Long> ingredientIDs = pizza.getIngredients().stream()
                .map(Ingredient::getId).collect(Collectors.toList());

        return new PizzaDTO(id, name, ingredientIDs);
    }

    public Ingredient toIngredient(IngredientDTO ingredientDTO) {
        Long id = ingredientDTO.getId();
        String name = ingredientDTO.getName();
        String brand = ingredientDTO.getBrand();
        String countryOrigin = ingredientDTO.getCountryOrigin();
        char nutritionScore = ingredientDTO.getNutritionScore();
        Integer calories = ingredientDTO.getCalories();
        Integer amount = ingredientDTO.getAmount();
        Double weight = ingredientDTO.getWeight();
        Double price = ingredientDTO.getPrice();
        List<Pizza> pizzas = ingredientDTO.getPizzaIDs().stream()
                .map(pizza_id -> {
                    Optional<Pizza> maybePizza = pizzaRepository.findById(pizza_id);
                    if(maybePizza.isPresent()) return maybePizza.get();
                    else throw new PizzaNotFoundException("Unknown Pizza with ID: " + pizza_id);
                })
                .collect(Collectors.toList());

        return new Ingredient(id, name, brand, countryOrigin, nutritionScore, calories, amount, weight, price, pizzas);
    }

    public Pizza toPizza(PizzaDTO pizzaDTO){
        Long id = pizzaDTO.getId();
        String name = pizzaDTO.getName();
        List<Ingredient> ingredients = pizzaDTO.getIngredientIDs().stream()
                .map(ingredient_id -> {
                    Optional<Ingredient> maybeIngredient = ingredientRepository.findById(ingredient_id);
                    if(maybeIngredient.isPresent()) return maybeIngredient.get();
                    else throw new IngredientNotFoundException("Unknown Ingredient with ID: " + ingredient_id);
                })
                .collect(Collectors.toList());

        return new Pizza(id, name, ingredients);
    }
}
