package productservice.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import productservice.api.dto.IngredientDTO;
import productservice.api.dto.PizzaDTO;
import productservice.api.entity.Pizza;
import productservice.api.exception.PizzaNotFoundException;
import productservice.api.service.DTOMapper;
import productservice.api.service.PizzaService;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;
    @Autowired
    private DTOMapper mapper;

    @GetMapping(path = "/pizzas", produces = "application/json")
    public ResponseEntity<List<PizzaDTO>> getPizzas() {
        List<Pizza> allPizzas = pizzaService.getPizzas();
        List<PizzaDTO> allIngredientsDTO = allPizzas.stream()
                .map(pizza -> mapper.toPizzaDTO(pizza)).collect(Collectors.toList());
        return new ResponseEntity<>(allIngredientsDTO,HttpStatus.OK);
    }

    @GetMapping(path = "/pizza/{id}", produces = "application/json")
    public ResponseEntity<PizzaDTO> getPizzaById(@PathVariable(value = "id") Long pizzaId) throws PizzaNotFoundException {
        Pizza pizzaById = this.pizzaService.getPizza(pizzaId);
        PizzaDTO pizzaByIDDTO = mapper.toPizzaDTO(pizzaById);
        return new ResponseEntity<>(pizzaByIDDTO, HttpStatus.OK);
    }
}
