package ie.sesh.Exceptions;

public class SeshException extends Exception {

    public SeshException () {

    }

    public SeshException (String message) {
        super (message);
    }

    public SeshException (Throwable cause) {
        super (cause);
    }

    public SeshException (String message, Throwable cause) {
        super (message, cause);
    }
}