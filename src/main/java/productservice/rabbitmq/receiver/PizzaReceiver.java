package productservice.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import productservice.api.controller.PizzaController;
import productservice.api.dto.PizzaDTO;
import productservice.api.entity.Pizza;
import productservice.api.service.PizzaDTOMapper;
import productservice.rabbitmq.config.Constant;

import java.util.List;

@Component
@Transactional
public class PizzaReceiver {

    @Autowired
    PizzaController pizzaController;
    @Autowired
    PizzaDTOMapper dtoMapper;

    @RabbitListener(queues = Constant.GETALL_PIZZA_QUEUE)
    public List<PizzaDTO> getPizzas() {
        ResponseEntity<List<PizzaDTO>> entity;
        try {
            entity = pizzaController.getPizzas();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        List<PizzaDTO> allPizzas = entity.getBody();
        return allPizzas;
    }

    @RabbitListener(queues = Constant.GET_PIZZA_QUEUE)
    public PizzaDTO getPizzaByID(@Payload Long id) {
        ResponseEntity<PizzaDTO> entity;
        try {
            entity = pizzaController.getPizzaById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        PizzaDTO pizzaDTO = entity.getBody();
        return pizzaDTO;
    }

    @RabbitListener(queues = Constant.CREATE_PIZZA_QUEUE)
    public PizzaDTO createPizza(@Payload PizzaDTO pizzaDTO) {
        ResponseEntity<Pizza> entity;
        try {
            entity = pizzaController.createPizza(pizzaDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        Pizza newPizza = entity.getBody();
        return dtoMapper.toPizzaDTO(newPizza);
    }
}