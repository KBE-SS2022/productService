package productservice.api.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import productservice.api.dto.PizzaDTO;
import productservice.api.entity.Ingredient;
import productservice.api.entity.Pizza;
import productservice.api.exception.IngredientNotFoundException;
import productservice.api.exception.PizzaNotFoundException;
import productservice.api.repository.PizzaRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PizzaServiceMockPizzaRepositoryStandaloneTest {

    @Mock
    PizzaRepository pizzaRepository;
    @Mock
    DTOMapper dtoMapper;

    @InjectMocks
    private PizzaService pizzaService;

    private Pizza pizza;

    @BeforeAll
    public void init() {
        List<Ingredient> ingredientList = new LinkedList<>();
        ingredientList.add(new Ingredient(20L,"Salami","jaa","italy",'d',
                350,1,100.0,4.0));
        ingredientList.add(new Ingredient(101L,"Mozarella","jaa","germany",'c',
                200,1,200.0,2.5));
        ingredientList.add(new Ingredient(10101L,"Brot","jaa","spain",'b',
                150,1,100.0,2.5));

        this.pizza = new Pizza(10111L,"Salami", ingredientList);
    }

    @Test
    void getPizzaById_ExpectGoodCase() throws  PizzaNotFoundException {
        PizzaDTO dto = new PizzaDTO(10111L,"Salami", List.of(20L, 101L, 10101L));

        when(pizzaRepository.findById(10111L)).thenReturn(Optional.of(this.pizza));
        when(dtoMapper.toPizzaDTO(pizza)).thenReturn(dto);

        Assert.assertEquals((pizza.getId()), pizzaService.getPizza(10111L).getId());
        Assert.assertEquals(pizza.getName(), pizzaService.getPizza(10111L).getName());
        Assert.assertEquals(20L, (long)pizzaService.getPizza(10111L).getIngredientIDs().get(0));
        Assert.assertEquals(101L, (long)pizzaService.getPizza(10111L).getIngredientIDs().get(1));
        Assert.assertEquals(10101L,(long)pizzaService.getPizza(10111L).getIngredientIDs().get(2));
    }
    @Test
    void getPizzaByIdNotFoundException() throws IngredientNotFoundException {
        when(pizzaRepository.findById(20L)).thenReturn(Optional.ofNullable((null)));
        PizzaNotFoundException thrown = Assert.assertThrows(PizzaNotFoundException.class,() ->{
            pizzaService.getPizza(20L);
        });
        Assert.assertEquals("Pizza with id: 20 not found in Database", thrown.getMessage());
    }
    @Test
    void getPizzas_ShouldReturnPizzaList() {
        List<Ingredient> ingredientList1 = new LinkedList<>();
        ingredientList1.add(new Ingredient(20L,"Thunfisch","jaa","italy",'d',350,1,100.0,4.0));
        ingredientList1.add(new Ingredient(101L,"Mozarella","jaa","germany",'c',200,1,200.0,2.5));
        ingredientList1.add(new Ingredient(10101L,"Brot","jaa","spain",'b',150,1,100.0,2.5));
        Pizza pizza1 = new Pizza(3001L,"Thunfisch", ingredientList1);
        List<Pizza> pizzaList = List.of(this.pizza, pizza1);

        PizzaDTO dto = new PizzaDTO(10111L,"Salami", List.of(20L, 101L, 10101L));
        PizzaDTO dto1 = new PizzaDTO(3001L, "Thunfisch", List.of(20L, 101L, 10101L));

        when(pizzaRepository.findAll()).thenReturn(pizzaList);
        when(pizzaRepository.findById(10111L)).thenReturn(Optional.of(this.pizza));
        when(pizzaRepository.findById(3001L)).thenReturn(Optional.of(pizza1));
        when(dtoMapper.toPizzaDTO(this.pizza)).thenReturn(dto);
        when(dtoMapper.toPizzaDTO(pizza1)).thenReturn(dto1);

        Assert.assertEquals(10111L, (long)pizzaService.getPizzas().get(0).getId());
        Assert.assertEquals(3001L, (long)pizzaService.getPizzas().get(1).getId());
        Assert.assertEquals("Salami", pizzaService.getPizza(10111L).getName());
        Assert.assertEquals("Thunfisch", pizzaService.getPizza(3001L).getName());
        Assert.assertEquals(20L, (long)pizzaService.getPizza(10111L).getIngredientIDs().get(0));
        Assert.assertEquals(101L, (long)pizzaService.getPizza(10111L).getIngredientIDs().get(1));
        Assert.assertEquals(10101L, (long)pizzaService.getPizza(10111L).getIngredientIDs().get(2));
        Assert.assertEquals(20L, (long)pizzaService.getPizza(3001L).getIngredientIDs().get(0));
        Assert.assertEquals(101L, (long)pizzaService.getPizza(3001L).getIngredientIDs().get(1));
        Assert.assertEquals(10101L,(long)pizzaService.getPizza(3001L).getIngredientIDs().get(2));
    }
    @Test
    void getPizzas_ShouldReturnEmptyPizzaList() {
        List<Pizza>emptyPizzaList = new LinkedList<>();
        when(pizzaRepository.findAll()).thenReturn(emptyPizzaList);
        Assert.assertEquals(0, pizzaService.getPizzas().size());
    }
}
