package productservice.rabbitmq.receiver;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import productservice.api.controller.IngredientController;
import productservice.dto.IngredientDTO;
import productservice.rabbitmq.MyAcknowledgement;
import productservice.rabbitmq.config.Constant;

import java.util.List;

@Component
@Transactional
public class IngredientReceiver {

    @Autowired
    IngredientController ingredientController;

    @RabbitListener(queues = Constant.GETALL_INGREDIENT_QUEUE)
    public List<IngredientDTO> getIngredients(Channel channel) {
        MyAcknowledgement.setAcknowledgement(channel, 1L, true);
        ResponseEntity<List<IngredientDTO>> entity;
        entity = ingredientController.getIngredients();
        List<IngredientDTO> allIngredients = entity.getBody();
        return allIngredients;
    }

    @RabbitListener(queues = Constant.GET_INGREDIENT_QUEUE)
    public IngredientDTO getIngredientByID(@Payload Long id, Channel channel) {
        MyAcknowledgement.setAcknowledgement(channel, 1L, true);
        ResponseEntity<IngredientDTO> entity;
        entity = ingredientController.getIngredientById(id);
        IngredientDTO ingredientDTO = entity.getBody();
        return ingredientDTO;
    }
}