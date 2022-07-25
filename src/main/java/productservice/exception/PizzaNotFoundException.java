package productservice.exception;

public class PizzaNotFoundException extends RuntimeException {

    public PizzaNotFoundException(long id){
        super("Pizza with id: " + id + " not found in Database");
    }
}
