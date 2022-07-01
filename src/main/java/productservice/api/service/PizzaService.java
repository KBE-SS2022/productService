package productservice.api.service;

import productservice.api.entity.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productservice.api.exception.PizzaNotFoundException;
import productservice.api.repository.PizzaRepository;

import java.util.List;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    public List<Pizza> getPizzas() {
        return pizzaRepository.findAll();
    }

    public Pizza getPizza(Long id) throws PizzaNotFoundException {
        return pizzaRepository.findById(id).orElseThrow(()->
                new PizzaNotFoundException("Pizza with id: " + id + " not found in Database"));
    }

    public Pizza savePizza(Pizza pizza){
        return pizzaRepository.save(pizza);
    }
}
