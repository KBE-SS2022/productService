package productservice.api.exception;

public class CouldNotCreatePizzaException extends RuntimeException {

    public CouldNotCreatePizzaException(){
        super("Pizza could not be created");
    }
}
