
package by.bsuir.serko.bettingapp.command;


import by.bsuir.serko.bettingapp.constant.PageType;
import by.bsuir.serko.bettingapp.utility.PathManager;
import by.bsuir.serko.bettingapp.controller.SessionRequestContent;

public class EmptyCommand implements Command {

    @Override
    public String execute(SessionRequestContent requestContent) {
        return PathManager.getPagePath(PageType.LOGIN.getPageName());
    }
    
}
