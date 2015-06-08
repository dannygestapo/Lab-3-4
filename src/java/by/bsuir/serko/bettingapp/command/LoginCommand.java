
package by.bsuir.serko.bettingapp.command;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.exception.TechnicalException;
import by.bsuir.serko.bettingapp.db.pool.ConnectionWrapper;
import by.bsuir.serko.bettingapp.model.entity.User;
import by.bsuir.serko.bettingapp.model.entity.UserType;
import static by.bsuir.serko.bettingapp.model.entity.UserType.ADMIN;
import static by.bsuir.serko.bettingapp.model.entity.UserType.USER;
import by.bsuir.serko.bettingapp.validation.LogInValidator;
import by.bsuir.serko.bettingapp.constant.PageType;
import by.bsuir.serko.bettingapp.utility.PathManager;
import by.bsuir.serko.bettingapp.constant.RequestAttributeType;
import by.bsuir.serko.bettingapp.constant.RequestParameterType;
import by.bsuir.serko.bettingapp.constant.SessionAttributeType;
import by.bsuir.serko.bettingapp.controller.SessionRequestContent;

public class LoginCommand extends CloseableCommand {

    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "password";
    
    @Override
    public String executeAndClose(SessionRequestContent requestContent, ConnectionWrapper connection) throws DatabaseException {
        PageType page;
        try {
            String login = requestContent.getRequestParameter(RequestParameterType.LOGIN.getName());
            String password = requestContent.getRequestParameter(RequestParameterType.PASSWORD.getName());
            LogInValidator logInValidator = new LogInValidator(login, password, connection);
            if (logInValidator.checkValidity()) {
                User user = logInValidator.getLoggedInUser();
                requestContent.setSessionAttribute(SessionAttributeType.USER.getName(), user, true);
                page = determineUserPage(user.getUserType());
            } else {
                requestContent.setRequestAttribute(RequestAttributeType.ERROR_MESSAGE_KEY.getName(), logInValidator.getErrorMessageKey());
                page = PageType.LOGIN;
            }
        } catch(TechnicalException e) {
            page = PageType.ERROR;
        }
        return PathManager.getPagePath(page.getPageName());
    }
    
    private PageType determineUserPage(UserType userType) {
        PageType page;
        switch (userType) {
            case USER:
                page = PageType.ACCOUNT;
                break;
            case ADMIN:
                page = PageType.ADMIN_ACCOUNT;
                break;
            default:
                throw new EnumConstantNotPresentException(UserType.class, String.valueOf(userType));
        }
        return page;
    }
    
}