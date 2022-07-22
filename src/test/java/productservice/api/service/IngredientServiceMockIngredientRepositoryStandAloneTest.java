package productservice.api.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import productservice.dto.IngredientDTO;
import productservice.api.entity.Ingredient;
import productservice.exception.IngredientNotFoundException;
import productservice.api.repository.IngredientRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IngredientServiceMockIngredientRepositoryStandAloneTest {

	@Mock
	private IngredientRepository mockedIngredientRepository;
	@Mock
	private IngredientDTOMapper ingredientDtoMapper;

	@InjectMocks
	private IngredientService ingredientService;

	private Ingredient ingredient;
	private Ingredient ingredient1;
	private IngredientDTO dto;
	private IngredientDTO dto1;


	@BeforeAll
	public void init() {
		ingredient = new Ingredient(20L, "Salami", "jaa", "italy",
				'd', 350, 1, 100.0, 4.0);
		dto = new IngredientDTO(20L, "Salami", "jaa", "italy",
				'd', 350, 1, 100.0, 4.0);

		ingredient1 = new Ingredient(30L, "Brot", "noname", "spain", 'b',
				200, 1, 100.0, 1.0);
		dto1 = new IngredientDTO(30L, "Brot", "noname", "spain", 'b',
				200, 1, 100.0, 1.0);
	}

	@Test
	void getIngredientById_GoodCase() throws IngredientNotFoundException {
		when(mockedIngredientRepository.findById(20L)).thenReturn(Optional.of(ingredient));
		when(ingredientDtoMapper.toIngredientDTO(ingredient)).thenReturn(dto);

		Assertions.assertEquals("Salami", ingredientService.getIngredient(20L).getName());
		Assertions.assertEquals("italy", ingredientService.getIngredient(20L).getCountryOrigin());
		Assertions.assertEquals('d', ingredientService.getIngredient(20L).getNutritionScore());
		Assertions.assertEquals(350, (int)ingredientService.getIngredient(20L).getCalories());
		Assertions.assertEquals(1, (int)ingredientService.getIngredient(20L).getAmount());
		Assertions.assertEquals(100.0, (double)ingredientService.getIngredient(20L).getWeight());
		Assertions.assertEquals(4.0, (double)ingredientService.getIngredient(20L).getPrice());
	}
	@Test
	void getIngredientById_NotFoundException() throws IngredientNotFoundException {
		when(mockedIngredientRepository.findById(20L)).thenReturn(Optional.ofNullable((null)));

		IngredientNotFoundException thrown = Assert.assertThrows(IngredientNotFoundException.class, () -> {
			ingredientService.getIngredient(20L);
		});
		Assertions.assertEquals("Ingredient with id: 20 not found in Database", thrown.getMessage());
	}
	@Test
	void getIngredients_ShouldReturnIngredientList() throws IngredientNotFoundException {
		List<Ingredient> ingredientList = List.of(ingredient1, ingredient);
		when(mockedIngredientRepository.findAll()).thenReturn(ingredientList);
		when(ingredientDtoMapper.toIngredientDTO(ingredient)).thenReturn(dto);
		when(ingredientDtoMapper.toIngredientDTO(ingredient1)).thenReturn(dto1);

		Assertions.assertEquals("Brot", ingredientService.getIngredients().get(0).getName());
		Assertions.assertEquals("noname", ingredientService.getIngredients().get(0).getBrand());
		Assertions.assertEquals("spain", ingredientService.getIngredients().get(0).getCountryOrigin());
		Assertions.assertEquals('b', ingredientService.getIngredients().get(0).getNutritionScore());
		Assertions.assertEquals(200, (int)ingredientService.getIngredients().get(0).getCalories());
		Assertions.assertEquals("Salami", ingredientService.getIngredients().get(1).getName());
	}
	@Test
	void getIngredients_ShouldReturnEmptyIngredientList() throws IngredientNotFoundException {
		List<Ingredient> emptyIngredientList = new LinkedList<>();
		when(mockedIngredientRepository.findAll()).thenReturn(emptyIngredientList);

		Assertions.assertEquals(0,ingredientService.getIngredients().size());
	}
}