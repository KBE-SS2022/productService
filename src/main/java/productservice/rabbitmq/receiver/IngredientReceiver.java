package productservice.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import productservice.api.controller.IngredientController;
import productservice.api.dto.IngredientDTO;
import productservice.rabbitmq.config.Constant;

import java.util.List;

@Component
@Transactional
public class IngredientReceiver {

    @Autowired
    IngredientController ingredientController;

    @RabbitListener(queues = Constant.GETALL_INGREDIENT_QUEUE)
    public List<IngredientDTO> getIngredients() {
        ResponseEntity<List<IngredientDTO>> entity;
        try {
            entity = ingredientController.getIngredients();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        List<IngredientDTO> allIngredients = entity.getBody();
        return allIngredients;
    }

    @RabbitListener(queues = Constant.GET_INGREDIENT_QUEUE)
    public IngredientDTO getIngredientByID(@Payload Long id) {
        ResponseEntity<IngredientDTO> entity;
        try {
            entity = ingredientController.getIngredientById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        IngredientDTO ingredientDTO = entity.getBody();
        return ingredientDTO;
    }
}