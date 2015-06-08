
package by.bsuir.serko.bettingapp.utility.encrypter;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;
import by.bsuir.serko.bettingapp.exception.TechnicalException;



public class SALTEncrypter implements Encrypter {

    public static final String SALT = "vhg234fgfg4566";
    public static final String ENCRYPTER_ALGORITHM = "SHA-256";
    
    @Override
    public String encrypt(String stringToEncrypt) throws TechnicalException {
        try{
            MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPTER_ALGORITHM);
            messageDigest.update(stringToEncrypt.concat(SALT).getBytes());
            byte[] encryptedStringBytes = messageDigest.digest();
            return DatatypeConverter.printHexBinary(encryptedStringBytes);
        } catch(NoSuchAlgorithmException e) {
            throw new TechnicalException(e);
        }
    }
    
    public static void main(String[] args) throws Exception {
        SALTEncrypter salte = new SALTEncrypter();
        System.out.println(salte.encrypt("admin"));
    }
    
}