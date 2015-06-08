
package by.bsuir.serko.bettingapp.validation;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.exception.TechnicalException;



public abstract class Validator {
    
    private String errorMessageKey;

    public Validator() {
        this.errorMessageKey = "";
    }
    
    public abstract boolean checkValidity() throws DatabaseException, TechnicalException;

    public String getErrorMessageKey() {
        return errorMessageKey;
    }

    public void setErrorMessageKey(String errorMessageKey) {
        this.errorMessageKey = errorMessageKey;
    }
    
}
