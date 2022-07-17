package productservice.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import productservice.api.dto.PizzaDTO;
import productservice.rabbitmq.config.Constant;

@Component
public class PizzaReceiver {

    @RabbitListener(queues = Constant.PIZZA_QUEUE)
    public PizzaDTO receive(@Payload PizzaDTO pizzaDTO) {
      return null;
    }
}
