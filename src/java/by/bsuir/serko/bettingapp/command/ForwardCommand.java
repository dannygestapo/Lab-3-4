
package by.bsuir.serko.bettingapp.command;

import by.bsuir.serko.bettingapp.utility.PathManager;
import by.bsuir.serko.bettingapp.constant.RequestParameterType;
import by.bsuir.serko.bettingapp.controller.SessionRequestContent;

public class ForwardCommand implements Command {

    @Override
    public String execute(SessionRequestContent requestContent) {
        String pageName = requestContent.getRequestParameter(RequestParameterType.PAGE.getName());
        return PathManager.getPagePath(pageName);
    }
    
}