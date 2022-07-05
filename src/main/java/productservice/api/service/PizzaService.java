package productservice.api.service;

import productservice.api.dto.PizzaDTO;
import productservice.api.entity.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productservice.api.exception.PizzaNotFoundException;
import productservice.api.repository.PizzaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PizzaService {

    @Autowired
    DTOMapper dtoMapper;
    @Autowired
    private PizzaRepository pizzaRepository;

    public List<PizzaDTO> getPizzas() {
        List<Pizza> allPizzas = pizzaRepository.findAll();
        return allPizzas.stream()
                .map(pizza -> dtoMapper.toPizzaDTO(pizza))
                .collect(Collectors.toList());
    }

    public PizzaDTO getPizza(Long id) throws PizzaNotFoundException {
        Pizza pizza =  pizzaRepository.findById(id).orElseThrow(()->
                new PizzaNotFoundException("Pizza with id: " + id + " not found in Database"));
        return dtoMapper.toPizzaDTO(pizza);
    }

    public Pizza savePizza(PizzaDTO pizza){
        Pizza newPizza = dtoMapper.toPizza(pizza);
        return pizzaRepository.save(newPizza);
    }
}
