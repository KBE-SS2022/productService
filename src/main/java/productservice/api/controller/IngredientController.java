package productservice.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import productservice.dto.IngredientDTO;
import productservice.api.service.IngredientService;

import java.util.List;

@RestController
@RequestMapping
public class IngredientController {

	@Autowired
	private IngredientService ingredientService;

	@GetMapping(path = "/ingredients", produces = "application/json")
	public ResponseEntity<List<IngredientDTO>> getIngredients() {
		List<IngredientDTO> allIngredients = ingredientService.getIngredients();
		return new ResponseEntity<>(allIngredients, HttpStatus.OK);
	}

	@GetMapping(path = "/ingredient/{id}", produces = "application/json")
	public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable(value = "id") Long ingredientId) {
		IngredientDTO ingredientById = this.ingredientService.getIngredient(ingredientId);
		return new ResponseEntity<>(ingredientById, HttpStatus.OK);
	}
}