package productservice.api.service;

import productservice.dto.IngredientDTO;
import productservice.api.entity.Ingredient;
import productservice.api.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productservice.exception.IngredientNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    @Autowired
    private IngredientDTOMapper ingredientDtoMapper;
    @Autowired
    private IngredientRepository ingredientRepository;

    public List<IngredientDTO> getIngredients() {
        List<Ingredient> allIngredients = ingredientRepository.findAll();
        return allIngredients.stream()
                .map(ingredient -> ingredientDtoMapper.toIngredientDTO(ingredient))
                .collect(Collectors.toList());
    }

    public IngredientDTO getIngredient(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(()->
                new IngredientNotFoundException(id));
        return ingredientDtoMapper.toIngredientDTO(ingredient);
    }
}
