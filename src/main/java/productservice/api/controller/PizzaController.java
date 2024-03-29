package productservice.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import productservice.dto.PizzaDTO;
import productservice.api.entity.Pizza;
import productservice.exception.CouldNotCreatePizzaException;
import productservice.api.service.PizzaDTOMapper;
import productservice.api.service.PizzaService;

import java.util.List;

@RestController
@RequestMapping
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private PizzaDTOMapper dtoMapper;

    @GetMapping(path = "/pizzas", produces = "application/json")
    public ResponseEntity<List<PizzaDTO>> getPizzas() {
        List<PizzaDTO> allPizzas = pizzaService.getPizzas();
        return new ResponseEntity<>(allPizzas,HttpStatus.OK);
    }

    @GetMapping(path = "/pizza/{id}", produces = "application/json")
    public ResponseEntity<PizzaDTO> getPizzaById(@PathVariable(value = "id") Long pizzaId) {
        PizzaDTO pizzaById = this.pizzaService.getPizza(pizzaId);
        return new ResponseEntity<>(pizzaById, HttpStatus.OK);
    }

    @PostMapping(path = "/pizza/create", produces = "application/json")
    public ResponseEntity<Pizza> createPizza(@RequestBody PizzaDTO pizzaDTO) {
        Pizza pizza = pizzaService.savePizza(pizzaDTO);
        if(pizza == null) throw new CouldNotCreatePizzaException();
        else return new ResponseEntity<>(pizza, HttpStatus.CREATED);
    }
}
