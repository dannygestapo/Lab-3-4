
package by.bsuir.serko.bettingapp.utility.encrypter;

import by.bsuir.serko.bettingapp.exception.TechnicalException;

public interface Encrypter {
    
    String encrypt(String stringToEncrypt) throws TechnicalException;
    
}

