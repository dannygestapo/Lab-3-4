
package by.bsuir.serko.bettingapp.validation;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.exception.TechnicalException;
import by.bsuir.serko.bettingapp.db.pool.ConnectionWrapper;
import by.bsuir.serko.bettingapp.db.dao.DAOFactory;
import by.bsuir.serko.bettingapp.db.dao.UserDAO;
import by.bsuir.serko.bettingapp.model.entity.User;
import by.bsuir.serko.bettingapp.utility.encrypter.SALTEncrypter;


public class LogInValidator extends Validator {
    
    private static final String WRONG_LOGIN_ERROR_KEY = "login.label.logInErrorMessage";
    
    private String login;
    private String password;
    private ConnectionWrapper connection;
    private User user;
    
    public LogInValidator(String login, String password, ConnectionWrapper connection) {
        super();
        this.login = login;
        this.password = password;
        this.connection = connection;
    }
    
    @Override
    public boolean checkValidity() throws DatabaseException, TechnicalException {
        DAOFactory daoFactory = DAOFactory.newInstance();
        UserDAO userDAO = daoFactory.getUserDAO(connection);
        user = userDAO.findUser(login, new SALTEncrypter().encrypt(password));
        boolean valid = (user != null);
        if(!valid) {
            setErrorMessageKey(WRONG_LOGIN_ERROR_KEY);
        }
        return valid;
    }
    
    public User getLoggedInUser() {
        return user;
    }
    
}