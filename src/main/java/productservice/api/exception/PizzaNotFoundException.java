package productservice.api.exception;

public class PizzaNotFoundException extends RuntimeException {

    public PizzaNotFoundException(String message){
        super(message);
    }
}
