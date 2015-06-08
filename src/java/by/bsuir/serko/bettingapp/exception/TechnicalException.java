
package by.bsuir.serko.bettingapp.exception;


public class TechnicalException extends Exception {

    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TechnicalException(Throwable cause) {
        super(cause);
    }
    
}
