
package by.bsuir.serko.bettingapp.command;

import by.bsuir.serko.bettingapp.controller.SessionRequestContent;

public interface Command {
    
    String execute(SessionRequestContent requestContent);
}
