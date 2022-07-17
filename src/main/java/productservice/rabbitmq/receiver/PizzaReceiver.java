package productservice.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import productservice.api.controller.PizzaController;
import productservice.api.dto.PizzaDTO;
import productservice.api.entity.Pizza;
import productservice.api.service.PizzaDTOMapper;
import productservice.rabbitmq.config.Constant;

@Component
public class PizzaReceiver {

    @Autowired
    PizzaController pizzaController;
    @Autowired
    PizzaDTOMapper dtoMapper;

    @RabbitListener(queues = Constant.PIZZA_QUEUE)
    public PizzaDTO receivePizzaCreate(@Payload PizzaDTO pizzaDTO) {
        ResponseEntity<Pizza> entity;
        try {
            entity = pizzaController.createPizza(pizzaDTO);
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
            return null;
        }
        Pizza newPizza = entity.getBody();
        return dtoMapper.toPizzaDTO(newPizza);
    }
}