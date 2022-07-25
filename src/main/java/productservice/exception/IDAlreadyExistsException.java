package productservice.exception;

public class IDAlreadyExistsException extends RuntimeException {

    public IDAlreadyExistsException(long id){
        super("Could not save Object. The following ID already exists: " + id);
    }
}
