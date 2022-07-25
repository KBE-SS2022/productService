package productservice.rabbitmq.receiver;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import productservice.api.controller.PizzaController;
import productservice.dto.PizzaDTO;
import productservice.api.entity.Pizza;
import productservice.api.service.PizzaDTOMapper;
import productservice.rabbitmq.MyAcknowledgement;
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
    public List<PizzaDTO> getPizzas(Channel channel) {
        MyAcknowledgement.setAcknowledgement(channel, 1L, true);
        ResponseEntity<List<PizzaDTO>> entity;
        entity = pizzaController.getPizzas();
        List<PizzaDTO> allPizzas = entity.getBody();
        return allPizzas;
    }

    @RabbitListener(queues = Constant.GET_PIZZA_QUEUE)
    public PizzaDTO getPizzaByID(@Payload Long id, Channel channel) {
        MyAcknowledgement.setAcknowledgement(channel, 1L, true);
        ResponseEntity<PizzaDTO> entity;
        entity = pizzaController.getPizzaById(id);
        PizzaDTO pizzaDTO = entity.getBody();
        return pizzaDTO;
    }

    @RabbitListener(queues = Constant.CREATE_PIZZA_QUEUE)
    public PizzaDTO createPizza(@Payload PizzaDTO pizzaDTO, Channel channel) {
        MyAcknowledgement.setAcknowledgement(channel, 1L, true);
        ResponseEntity<Pizza> entity;
        entity = pizzaController.createPizza(pizzaDTO);
        Pizza newPizza = entity.getBody();
        return dtoMapper.toPizzaDTO(newPizza);
    }
}