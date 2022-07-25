package productservice.exception;

public class AcknowledgementCouldNotBeSetException extends RuntimeException {

    public AcknowledgementCouldNotBeSetException() {
        super("Acknowledgement could not be set");
    }
}