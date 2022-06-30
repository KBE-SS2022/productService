package productservice.api.dto;

import warehouse.api.entity.Pizza;

import java.util.List;

public class PizzaDTO {

    private Long id;
    private String name;
    private List<Long> ingredientsIDs;


    public PizzaDTO() {}

    public PizzaDTO(Long id, String name, List<Long> ingredientsIDs) {
        this.id = id;
        this.name = name;
        this.ingredientsIDs = ingredientsIDs;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Long> getIngredientsIDs() {
        return ingredientsIDs;
    }
}
