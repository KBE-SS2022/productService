package productservice.api.dto;

import java.util.LinkedList;
import java.util.List;

public class PizzaDTO {

    private Long id;
    private String name;
    private List<Long> ingredientIDs;


    public PizzaDTO() {}

    public PizzaDTO(Long id, String name, List<Long> ingredientIDs) {
        this.id = id;
        this.name = name;
        this.ingredientIDs = ingredientIDs;
    }

    public PizzaDTO(Long id, String name) {
        this.id = id;
        this.name = name;
        this.ingredientIDs = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Long> getIngredientIDs() {
        return ingredientIDs;
    }
}
