package productservice.api.exception;

public class MissingRequiredIngredientException extends RuntimeException {

    public MissingRequiredIngredientException(String message){
        super(message);
    }
}
