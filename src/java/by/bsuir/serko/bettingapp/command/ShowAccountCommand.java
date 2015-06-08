
package by.bsuir.serko.bettingapp.command;

import by.bsuir.serko.bettingapp.model.entity.User;
import by.bsuir.serko.bettingapp.model.entity.UserType;
import static by.bsuir.serko.bettingapp.model.entity.UserType.ADMIN;
import static by.bsuir.serko.bettingapp.model.entity.UserType.USER;
import by.bsuir.serko.bettingapp.constant.PageType;
import by.bsuir.serko.bettingapp.utility.PathManager;
import by.bsuir.serko.bettingapp.constant.SessionAttributeType;
import by.bsuir.serko.bettingapp.controller.SessionRequestContent;


public class ShowAccountCommand implements Command {
    
    @Override
    public String execute(SessionRequestContent requestContent) {
        PageType page;
        User user = (User)requestContent.getSessionAttribute(SessionAttributeType.USER.getName());
        if(user != null) {
            UserType userType = user.getUserType();
            page = determineUserAccountPage(userType);
        } else {
            page = PageType.LOGIN;
        }
        return PathManager.getPagePath(page.getPageName());
    }
    
    private PageType determineUserAccountPage(UserType userType) {
        PageType page;
        switch(userType) {
                case USER:
                    page = PageType.ACCOUNT;
                    break;
                case ADMIN:
                    page = PageType.ADMIN_ACCOUNT;
                    break;
                default:
                    throw new EnumConstantNotPresentException(userType.getClass(), userType.name());
        }
        return page;
    }
    
}
