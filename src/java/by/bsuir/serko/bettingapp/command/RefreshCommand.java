
package by.bsuir.serko.bettingapp.command;

import by.bsuir.serko.bettingapp.utility.PathManager;
import by.bsuir.serko.bettingapp.constant.RequestParameterType;
import by.bsuir.serko.bettingapp.controller.SessionRequestContent;
import by.bsuir.serko.bettingapp.utility.URIUtil;


public class RefreshCommand implements Command {

    @Override
    public String execute(SessionRequestContent requestContent) {
        String callerURI = requestContent.getRequestParameter(RequestParameterType.CALLER.getName());
        String pageName = URIUtil.getPageFromURI(callerURI);
        return PathManager.getPagePath(pageName);
    }
    
}