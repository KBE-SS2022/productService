package productservice.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import productservice.dto.IngredientDTO;
import productservice.api.entity.Ingredient;
import productservice.api.entity.Pizza;
import productservice.api.repository.PizzaRepository;

import java.util.LinkedList;
import java.util.List;

@Component
public class IngredientDTOMapper {

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

        return new IngredientDTO(id, name, brand, countryOrigin, nutritionScore, calories, amount, weight, price);
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
        List<Pizza> pizzas = new LinkedList<>();

        return new Ingredient(id, name, brand, countryOrigin, nutritionScore, calories, amount, weight, price, pizzas);
    }
}