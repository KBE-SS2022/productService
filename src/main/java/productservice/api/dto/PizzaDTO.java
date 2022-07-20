package productservice.api.dto;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PizzaDTO {

    private Long id;
    private String name;
    private Map<Long,Double> ingredientIdToPrice;
}
