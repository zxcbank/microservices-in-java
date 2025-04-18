package kkkombinator.Controller.Exceptions;

public class CatNotFoundException extends RuntimeException {

    public CatNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
