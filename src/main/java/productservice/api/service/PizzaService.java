package productservice.api.service;

import productservice.dto.PizzaDTO;
import productservice.api.entity.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productservice.api.exception.IDAlreadyExistsException;
import productservice.api.exception.PizzaNotFoundException;
import productservice.api.repository.PizzaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PizzaService {

    @Autowired
    PizzaDTOMapper pizzaDtoMapper;
    @Autowired
    private PizzaRepository pizzaRepository;

    public List<PizzaDTO> getPizzas() {
        List<Pizza> allPizzas = pizzaRepository.findAll();
        return allPizzas.stream()
                .map(pizza -> pizzaDtoMapper.toPizzaDTO(pizza))
                .collect(Collectors.toList());
    }

    public PizzaDTO getPizza(Long id) throws PizzaNotFoundException {
        Pizza pizza =  pizzaRepository.findById(id).orElseThrow(()->
                new PizzaNotFoundException(id));
        return pizzaDtoMapper.toPizzaDTO(pizza);
    }

    public Pizza savePizza(PizzaDTO pizza){
        Pizza newPizza = pizzaDtoMapper.toPizza(pizza);
        Long id = newPizza.getId();
        if(pizzaRepository.existsById(id)) throw new IDAlreadyExistsException(id);
        else return pizzaRepository.save(newPizza);
    }
}
