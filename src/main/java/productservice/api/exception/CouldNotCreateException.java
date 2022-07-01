package productservice.api.exception;

public class CouldNotCreateException extends RuntimeException {

    public  CouldNotCreateException(String message){
        super(message);
    }
}
