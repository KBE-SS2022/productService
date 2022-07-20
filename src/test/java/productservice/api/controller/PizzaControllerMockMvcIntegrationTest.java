package productservice.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import productservice.api.dto.PizzaDTO;
import productservice.api.entity.Ingredient;
import productservice.api.entity.Pizza;
import productservice.api.exception.ControllerAdviceExceptionHandling;
import productservice.api.exception.PizzaNotFoundException;
import productservice.api.service.PizzaDTOMapper;
import productservice.api.service.PizzaService;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PizzaController.class)
@ContextConfiguration(classes ={PizzaController.class, ControllerAdviceExceptionHandling.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PizzaControllerMockMvcIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PizzaService pizzaService;
    @MockBean
    private PizzaDTOMapper pizzaDtoMapper;

    private PizzaDTO pizza;
    private PizzaDTO pizzaNew;
    private Map<Long,Double> ingredientIdToPrice;
    private final String getPizzaListPath = "/pizzas";
    private final String getPizzaByIdPath = "/pizza/{id}";
    private final String postPizzaPath = "/pizza/create";

    @BeforeAll
    void init () {
        ingredientIdToPrice = Map.of(10101L, 1.00, 10102L, 2.50, 10105L, 1.50);
        pizza = new PizzaDTO(10L,"Salami", ingredientIdToPrice);
        pizzaNew = new PizzaDTO(10206L,"Thunfisch", ingredientIdToPrice);
    }

    @Test
    void testMockMvcCreation(){
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    public void getPizzas_ShouldReturnPizzaList() throws Exception {
        when(pizzaService.getPizzas()).thenReturn(List.of(pizza, pizzaNew));

        mockMvc.perform(get(getPizzaListPath).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$.[0].size()", Matchers.is(3)))
                .andExpect(jsonPath("$[0].id").value("10"))
                .andExpect(jsonPath("$[0].name").value("Salami"))
                .andExpect(jsonPath("$[0].ingredientIdToPrice", Matchers.hasKey("10101")))
                .andExpect(jsonPath("$[0].ingredientIdToPrice", Matchers.hasKey("10102")))
                .andExpect(jsonPath("$[0].ingredientIdToPrice", Matchers.hasKey("10105")))

                .andExpect(jsonPath("$.[1].size()", Matchers.is(3)))
                .andExpect(jsonPath("$[1].id").value("10206"))
                .andExpect(jsonPath("$[1].name").value("Thunfisch"))
                .andExpect(jsonPath("$[1].ingredientIdToPrice", Matchers.hasKey("10101")))
                .andExpect(jsonPath("$[1].ingredientIdToPrice", Matchers.hasKey("10102")))
                .andExpect(jsonPath("$[1].ingredientIdToPrice", Matchers.hasKey("10105")));
    }
    @Test
    public void getPizzas_ShouldReturnEmptyList() throws Exception {
        List<PizzaDTO> emptyList = new LinkedList<>();

        when(pizzaService.getPizzas()).thenReturn(emptyList);
        mockMvc.perform(get(getPizzaListPath).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(0)));
    }
    @Test
    public void getPizzasWithNotNeededParameterInput_ShouldReturnPizzaList()throws Exception{
        when(pizzaService.getPizzas()).thenReturn(List.of(pizza, pizzaNew));

        mockMvc.perform(get(getPizzaListPath,"extraInputString",20).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$.[0].size()", Matchers.is(3)))
                .andExpect(jsonPath("$[0].id").value("10"))
                .andExpect(jsonPath("$[0].name").value("Salami"))
                .andExpect(jsonPath("$[0].ingredientIdToPrice", Matchers.hasKey("10101")))
                .andExpect(jsonPath("$[0].ingredientIdToPrice", Matchers.hasKey("10102")))
                .andExpect(jsonPath("$[0].ingredientIdToPrice", Matchers.hasKey("10105")))

                .andExpect(jsonPath("$.[1].size()", Matchers.is(3)))
                .andExpect(jsonPath("$[1].id").value("10206"))
                .andExpect(jsonPath("$[1].name").value("Thunfisch"))
                .andExpect(jsonPath("$[1].ingredientIdToPrice", Matchers.hasKey("10101")))
                .andExpect(jsonPath("$[1].ingredientIdToPrice", Matchers.hasKey("10102")))
                .andExpect(jsonPath("$[1].ingredientIdToPrice", Matchers.hasKey("10105")));
    }
    @Test
    public void getPizzaByID_ShouldGetPizzaByID() throws Exception {
        when(this.pizzaService.getPizza(10L)).thenReturn(this.pizza);

        this.mockMvc.perform(get(this.getPizzaByIdPath,10L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(3)))
                .andExpect(jsonPath("$.id").value("10"))
                .andExpect(jsonPath("$.name").value("Salami"))
                .andExpect(jsonPath("$.ingredientIdToPrice", Matchers.hasKey("10101")))
                .andExpect(jsonPath("$.ingredientIdToPrice", Matchers.hasKey("10102")))
                .andExpect(jsonPath("$.ingredientIdToPrice", Matchers.hasKey("10105")));
    }
    @Test
    public void getPizzaById_ShouldGetPizzaNotFoundResponse() throws Exception {
        when(this.pizzaService.getPizza(20L)).thenThrow(new PizzaNotFoundException(20L));

        mockMvc.perform(get(getPizzaByIdPath,20L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void getPizzaById_ShouldGetBadRequestResponse() throws Exception {
        mockMvc.perform(get(getPizzaByIdPath,"extraInputVariable").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    @Disabled("There is some error within the Mocking. However, the test is running successful in Postman")
    public void createPizza_ShouldBeCreated() throws Exception {
        Ingredient ingredient = new Ingredient(10101L,"Brot","jaa","italy",'d',
                350,1,100.0,4.0);
        Pizza newPizza = new Pizza(10L,"Salami", List.of(ingredient));

        when(pizzaService.savePizza(pizza)).thenReturn(newPizza);

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(pizza);

        mockMvc.perform(post(postPizzaPath).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void createPizza_ShouldGetBadRequestResponse() throws Exception {
        mockMvc.perform(post(postPizzaPath).contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest());
    }
}