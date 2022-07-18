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
    public TopicExchange pizzaExchange() { return new TopicExchange(Constant.PIZZA_EXCHANGE); }

    @Bean
    public TopicExchange ingredientExchange() { return new TopicExchange(Constant.INGREDIENT_EXCHANGE); }

    @Bean
    public Queue getAllPizzaQueue() { return new Queue(Constant.GETALL_PIZZA_QUEUE, false); }

    @Bean
    public Queue getPizzaQueue() { return new Queue(Constant.GET_PIZZA_QUEUE, false); }

    @Bean
    public Queue createPizzaQueue() { return new Queue(Constant.CREATE_PIZZA_QUEUE, false); }

    @Bean
    public Binding getPizzasBinding(@Qualifier("getAllPizzaQueue") Queue queue,
                                    @Qualifier("pizzaExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Constant.GETALL_KEY);
    }

    @Bean
    public Binding getPizzaByIDBinding(@Qualifier("getPizzaQueue") Queue queue,
                                   @Qualifier("pizzaExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Constant.GET_KEY);
    }

    @Bean
    public Binding createPizzaBinding(@Qualifier("createPizzaQueue") Queue queue,
                                      @Qualifier("pizzaExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Constant.CREATE_KEY);
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