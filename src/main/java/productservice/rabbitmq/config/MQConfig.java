package productservice.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Bean
    public TopicExchange exchange() { return new TopicExchange(Constant.EXCHANGE); }

    @Bean
    public Queue pizzaQueue() { return new Queue(Constant.PIZZA_QUEUE, false); }

    @Bean
    public Queue ingredientQueue() { return new Queue(Constant.INGREDIENT_QUEUE, false); }

    @Bean
    public Binding PizzaBinding(@Qualifier("pizzaQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Constant.PIZZA_ROUTING_KEY);
    }

    @Bean
    public Binding IngredientBinding(@Qualifier("ingredientQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Constant.INGREDIENT_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() { return new Jackson2JsonMessageConverter(); }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
