package productservice.api.dto;

import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PizzaDTO {

    private Long id;
    private String name;
    private List<Long> ingredientIDs;

    public PizzaDTO(Long id, String name) {
        this.id = id;
        this.name = name;
        this.ingredientIDs = new LinkedList<>();
    }
}
