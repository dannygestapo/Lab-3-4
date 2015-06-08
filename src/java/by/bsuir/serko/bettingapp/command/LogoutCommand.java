
package by.bsuir.serko.bettingapp.command;

import by.bsuir.serko.bettingapp.constant.PageType;
import by.bsuir.serko.bettingapp.utility.PathManager;
import by.bsuir.serko.bettingapp.constant.RequestAttributeType;
import by.bsuir.serko.bettingapp.constant.SessionAttributeType;
import by.bsuir.serko.bettingapp.controller.SessionRequestContent;


public class LogoutCommand implements Command {

    @Override
    public String execute(SessionRequestContent requestContent) {
        Object sessionLocale = requestContent.getSessionAttribute(SessionAttributeType.LOCALE.getName());
        requestContent.setRequestAttribute(RequestAttributeType.LOCALE.getName(), sessionLocale);
        requestContent.invalidateSession();
        return PathManager.getPagePath(PageType.LOGIN.getPageName());
    }
    
}
