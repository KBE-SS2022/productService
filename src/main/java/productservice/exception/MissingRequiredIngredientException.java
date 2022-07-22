package productservice.exception;

public class MissingRequiredIngredientException extends RuntimeException {

    public MissingRequiredIngredientException(long id, long requiredIngredient){
        super("Pizza with ID " + id + " doesn´t contain Ingredient with ID " + requiredIngredient);
    }
}
