package productservice.api.dto;

import java.util.LinkedList;
import java.util.List;

public class IngredientDTO {

    private Long id;
    private String name;
    private String brand;
    private String countryOrigin;
    private char nutritionScore;
    private Integer calories;
    private Integer amount;
    private Double weight;
    private Double price;
    private List<Long> pizzaIDs;


    public IngredientDTO() {}

    public IngredientDTO(Long id, String name, String brand, String countryOrigin, char nutritionScore,
                         Integer calories, Integer amount, Double weight, Double price, List<Long> pizzaIDs) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.countryOrigin = countryOrigin;
        this.nutritionScore = nutritionScore;
        this.calories = calories;
        this.amount = amount;
        this.weight = weight;
        this.price = price;
        this.pizzaIDs = pizzaIDs;
    }

    public IngredientDTO(Long id, String name, String brand, String countryOrigin, char nutritionScore,
                         Integer calories, Integer amount, Double weight, Double price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.countryOrigin = countryOrigin;
        this.nutritionScore = nutritionScore;
        this.calories = calories;
        this.amount = amount;
        this.weight = weight;
        this.price = price;
        this.pizzaIDs = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getCountryOrigin() {
        return countryOrigin;
    }

    public char getNutritionScore() {
        return nutritionScore;
    }

    public Integer getCalories() {
        return calories;
    }

    public Integer getAmount() {
        return amount;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getPrice() {
        return price;
    }

    public List<Long> getPizzaIDs() {
        return pizzaIDs;
    }
}
