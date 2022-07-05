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
import productservice.api.dto.IngredientDTO;
import productservice.api.entity.Ingredient;
import productservice.api.exception.IngredientNotFoundException;
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
	private DTOMapper dtoMapper;

	@InjectMocks
	private IngredientService ingredientService;

	private Ingredient ingredient;


	@BeforeAll
	public void init() {
		this.ingredient = new Ingredient(20L, "Salami", "jaa", "italy",
				'd', 350, 1, 100.0, 4.0);
	}

	@Test
	void getIngredientById_GoodCase() throws IngredientNotFoundException {
		IngredientDTO dto = new IngredientDTO(20L, "Salami", "jaa", "italy",
				'd', 350, 1, 100.0, 4.0);

		when(mockedIngredientRepository.findById(20L)).thenReturn(Optional.of(this.ingredient));
		when(dtoMapper.toIngredientDTO(this.ingredient)).thenReturn(dto);

		Assert.assertEquals("Salami", ingredientService.getIngredient(20L).getName());
		Assert.assertEquals("italy", ingredientService.getIngredient(20L).getCountryOrigin());
		Assert.assertEquals('d', ingredientService.getIngredient(20L).getNutritionScore());
		Assert.assertEquals(350, (int)ingredientService.getIngredient(20L).getCalories());
		Assert.assertEquals(1, (int)ingredientService.getIngredient(20L).getAmount());
		Assertions.assertEquals(100.0, (double)ingredientService.getIngredient(20L).getWeight());
		Assertions.assertEquals(4.0, (double)ingredientService.getIngredient(20L).getPrice());
	}
	@Test
	void getIngredientById_NotFoundException() throws IngredientNotFoundException {
		when(mockedIngredientRepository.findById(20L)).thenReturn(Optional.ofNullable((null)));

		IngredientNotFoundException thrown = Assert.assertThrows(IngredientNotFoundException.class, () -> {
			ingredientService.getIngredient(20L);
		});
		Assert.assertEquals("Ingredient with id: 20 not found in Database", thrown.getMessage());
	}
	@Test
	void getIngredients_ShouldReturnIngredientList() throws IngredientNotFoundException {
		Ingredient ingredient1 = new Ingredient(30L, "Brot", "noname", "spain", 'b',
				200, 1, 100.0, 1.0);
		IngredientDTO dto = new IngredientDTO(20L, "Salami", "jaa", "italy",
				'd', 350, 1, 100.0, 4.0);
		IngredientDTO dto1 = new IngredientDTO(30L, "Brot", "noname", "spain", 'b',
				200, 1, 100.0, 1.0);

		List<Ingredient> ingredientList = List.of(ingredient1, this.ingredient);
		when(mockedIngredientRepository.findAll()).thenReturn(ingredientList);
		when(dtoMapper.toIngredientDTO(this.ingredient)).thenReturn(dto);
		when(dtoMapper.toIngredientDTO(ingredient1)).thenReturn(dto1);

		Assert.assertEquals("Brot", ingredientService.getIngredients().get(0).getName());
		Assert.assertEquals("noname", ingredientService.getIngredients().get(0).getBrand());
		Assert.assertEquals("spain", ingredientService.getIngredients().get(0).getCountryOrigin());
		Assert.assertEquals('b', ingredientService.getIngredients().get(0).getNutritionScore());
		Assert.assertEquals(200, (int)ingredientService.getIngredients().get(0).getCalories());
		Assert.assertEquals("Salami", ingredientService.getIngredients().get(1).getName());
	}
	@Test
	void getIngredients_ShouldReturnEmptyIngredientList() throws IngredientNotFoundException {
		List<Ingredient> emptyIngredientList = new LinkedList<>();
		when(mockedIngredientRepository.findAll()).thenReturn(emptyIngredientList);

		Assert.assertEquals(0,ingredientService.getIngredients().size());
	}
}