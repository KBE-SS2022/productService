package productservice.api.exception;

public class IngredientNotFoundException extends RuntimeException {

    public IngredientNotFoundException(long id){
        super("Ingredient with id: " + id + " not found in Database");
    }
}
