package productservice.api.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import productservice.dto.PizzaDTO;
import productservice.api.entity.Ingredient;
import productservice.api.entity.Pizza;
import productservice.api.exception.IngredientNotFoundException;
import productservice.api.exception.PizzaNotFoundException;
import productservice.api.repository.PizzaRepository;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PizzaServiceMockPizzaRepositoryStandaloneTest {

    @Mock
    PizzaRepository pizzaRepository;
    @Mock
    PizzaDTOMapper pizzaDtoMapper;

    @InjectMocks
    private PizzaService pizzaService;

    private Pizza pizza;
    private Pizza pizza1;
    private PizzaDTO dto;
    private PizzaDTO dto1;

    @BeforeAll
    public void init() {
        List<Ingredient> ingredientList = new LinkedList<>();
        ingredientList.add(new Ingredient(20L,"Salami","jaa","italy",'d',
                350,1,100.0,4.0));
        ingredientList.add(new Ingredient(101L,"Mozarella","jaa","germany",'c',
                200,1,200.0,2.5));
        ingredientList.add(new Ingredient(10101L,"Brot","jaa","spain",'b',
                150,1,100.0,2.5));

        pizza = new Pizza(10111L,"Salami", ingredientList);
        dto = new PizzaDTO(10111L,"Salami", Map.of(20L,4.0, 101L,2.5, 10101L,2.5));

        List<Ingredient> ingredientList1 = new LinkedList<>();
        ingredientList1.add(new Ingredient(20L,"Thunfisch","jaa","italy",'d',
                350,1,100.0,4.0));
        ingredientList1.add(new Ingredient(101L,"Mozarella","jaa","germany",'c',
                200,1,200.0,2.5));
        ingredientList1.add(new Ingredient(10101L,"Brot","jaa","spain",'b',
                150,1,100.0,2.5));
        pizza1 = new Pizza(3001L,"Thunfisch", ingredientList1);
        dto1 = new PizzaDTO(3001L, "Thunfisch", Map.of(20L,4.0, 101L,2.5, 10101L,2.5));
    }

    @Test
    void getPizzaById_ExpectGoodCase() throws  PizzaNotFoundException {
        when(pizzaRepository.findById(10111L)).thenReturn(Optional.of(pizza));
        when(pizzaDtoMapper.toPizzaDTO(pizza)).thenReturn(dto);

        Assertions.assertEquals((pizza.getId()), pizzaService.getPizza(10111L).getId());
        Assertions.assertEquals(pizza.getName(), pizzaService.getPizza(10111L).getName());
        Assertions.assertTrue(pizzaService.getPizza(10111L).getIngredientIdToPrice().containsKey(20L));
        Assertions.assertTrue(pizzaService.getPizza(10111L).getIngredientIdToPrice().containsKey(101L));
        Assertions.assertTrue(pizzaService.getPizza(10111L).getIngredientIdToPrice().containsKey(10101L));
    }
    @Test
    void getPizzaByIdNotFoundException() throws IngredientNotFoundException {
        when(pizzaRepository.findById(20L)).thenReturn(Optional.ofNullable((null)));
        PizzaNotFoundException thrown = Assert.assertThrows(PizzaNotFoundException.class,() ->{
            pizzaService.getPizza(20L);
        });
        Assertions.assertEquals("Pizza with id: 20 not found in Database", thrown.getMessage());
    }
    @Test
    void getPizzas_ShouldReturnPizzaList() {
        List<Pizza> pizzaList = List.of(pizza, pizza1);

        when(pizzaRepository.findAll()).thenReturn(pizzaList);
        when(pizzaRepository.findById(10111L)).thenReturn(Optional.of(pizza));
        when(pizzaRepository.findById(3001L)).thenReturn(Optional.of(pizza1));
        when(pizzaDtoMapper.toPizzaDTO(pizza)).thenReturn(dto);
        when(pizzaDtoMapper.toPizzaDTO(pizza1)).thenReturn(dto1);

        Assertions.assertEquals(10111L, (long)pizzaService.getPizzas().get(0).getId());
        Assertions.assertEquals(3001L, (long)pizzaService.getPizzas().get(1).getId());
        Assertions.assertEquals("Salami", pizzaService.getPizza(10111L).getName());
        Assertions.assertEquals("Thunfisch", pizzaService.getPizza(3001L).getName());
        Assertions.assertTrue(pizzaService.getPizza(10111L).getIngredientIdToPrice().containsKey(20L));
        Assertions.assertTrue(pizzaService.getPizza(10111L).getIngredientIdToPrice().containsKey(101L));
        Assertions.assertTrue(pizzaService.getPizza(10111L).getIngredientIdToPrice().containsKey(10101L));

        Assertions.assertTrue(pizzaService.getPizza(3001L).getIngredientIdToPrice().containsKey(20L));
        Assertions.assertTrue(pizzaService.getPizza(3001L).getIngredientIdToPrice().containsKey(101L));
        Assertions.assertTrue(pizzaService.getPizza(3001L).getIngredientIdToPrice().containsKey(10101L));
    }
    @Test
    void getPizzas_ShouldReturnEmptyPizzaList() {
        List<Pizza>emptyPizzaList = new LinkedList<>();
        when(pizzaRepository.findAll()).thenReturn(emptyPizzaList);
        Assertions.assertEquals(0, pizzaService.getPizzas().size());
    }
    @Test
    void createPizza_ShouldReturnPizza() {
        when(pizzaDtoMapper.toPizza(dto)).thenReturn(pizza);
        when(pizzaRepository.save(pizza)).thenReturn(pizza);

        Assertions.assertEquals(pizza.getId(), pizzaService.savePizza(dto).getId());
        Assertions.assertEquals(pizza.getName(), pizzaService.savePizza(dto).getName());
        Assertions.assertEquals(20L, (long)pizzaService.savePizza(dto).getIngredients().get(0).getId());
        Assertions.assertEquals(101L, (long)pizzaService.savePizza(dto).getIngredients().get(1).getId());
        Assertions.assertEquals(10101L,(long)pizzaService.savePizza(dto).getIngredients().get(2).getId());
    }
    @Test
    void createPizza_ShouldReturnNull() {
        when(pizzaRepository.save(pizza)).thenReturn(null);
        when(pizzaDtoMapper.toPizza(dto)).thenReturn(pizza);

        Assertions.assertNull(pizzaService.savePizza(dto));
    }
}
