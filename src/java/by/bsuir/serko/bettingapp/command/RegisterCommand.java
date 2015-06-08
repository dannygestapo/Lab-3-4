
package by.bsuir.serko.bettingapp.command;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.exception.TechnicalException;
import by.bsuir.serko.bettingapp.db.pool.ConnectionWrapper;
import by.bsuir.serko.bettingapp.db.dao.DAOFactory;
import by.bsuir.serko.bettingapp.db.dao.UserDAO;
import by.bsuir.serko.bettingapp.model.entity.SimpleUser;
import by.bsuir.serko.bettingapp.validation.RegistrationValidator;
import by.bsuir.serko.bettingapp.constant.PageType;
import by.bsuir.serko.bettingapp.utility.PathManager;
import by.bsuir.serko.bettingapp.constant.RequestAttributeType;
import by.bsuir.serko.bettingapp.constant.RequestParameterType;
import by.bsuir.serko.bettingapp.utility.encrypter.SALTEncrypter;
import by.bsuir.serko.bettingapp.controller.SessionRequestContent;


public class RegisterCommand extends CloseableCommand {

    @Override
    public String executeAndClose(SessionRequestContent requestContent, ConnectionWrapper connection) throws DatabaseException {
        PageType page;
        try {
            String login = requestContent.getRequestParameter(RequestParameterType.LOGIN.getName());
            String password = requestContent.getRequestParameter(RequestParameterType.PASSWORD.getName());
            String firstName = requestContent.getRequestParameter(RequestParameterType.FIRST_NAME.getName());
            String lastName = requestContent.getRequestParameter(RequestParameterType.LAST_NAME.getName());
            RegistrationValidator registrationValidator = new RegistrationValidator(login, password, firstName, lastName, connection);
            if(registrationValidator.checkValidity()) {
                registerNewUser(login, password, firstName, lastName, connection);
                page = PageType.LOGIN;
            } else {
                page = PageType.REGISTRATION;
                requestContent.setRequestAttribute(RequestAttributeType.ERROR_MESSAGE_KEY.getName(), registrationValidator.getErrorMessageKey());
            }
        } catch(TechnicalException e) {
            page = PageType.ERROR;
        }
        return PathManager.getPagePath(page.getPageName());
    }
    
    private void registerNewUser(String login, String password, String firstName, String lastName, ConnectionWrapper connection) throws DatabaseException, TechnicalException {
        SimpleUser newUser = new SimpleUser();
        newUser.setLogin(login);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setPassword(new SALTEncrypter().encrypt(password));
        DAOFactory daoFactory = DAOFactory.newInstance();
        UserDAO userDAO = daoFactory.getUserDAO(connection);
        userDAO.addUser(newUser);
    }
    
}
