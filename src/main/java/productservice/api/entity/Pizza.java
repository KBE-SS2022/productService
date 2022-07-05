package productservice.api.entity;

import productservice.api.exception.MissingRequiredIngredientException;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Pizza")
public class Pizza {

    @Id
    @Column(name="pizza_id")
    private Long id;
    @Column(name="name")
    private String name;

    private final static Long REQUIRED_INGREDIENT = 10101L;

    @Column
    @ManyToMany()
    @JoinTable(name="pizza_ingredient",
            joinColumns = { @JoinColumn(name="pizza_id") },
            inverseJoinColumns = { @JoinColumn(name="ingredient_id") })
    private List<Ingredient> ingredients = new LinkedList<>();

    public Pizza() {}

    public Pizza(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Pizza(Long id, String name, List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        if(!containsRequiredIngredients(ingredients))
            throw new MissingRequiredIngredientException(
                    "Pizza with ID " + id + " doesn´t contain Ingredient with ID " + REQUIRED_INGREDIENT);
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Pizza {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }

    private Boolean containsRequiredIngredients(List<Ingredient> ingredients){
        List<Ingredient> requiredIngredients = ingredients.stream()
                .filter(ingredient -> ingredient.getId().equals(REQUIRED_INGREDIENT))
                .collect(Collectors.toList());
        return !requiredIngredients.isEmpty();
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<Ingredient> getIngredients() { return ingredients; }

    public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }
}
