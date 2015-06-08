
package by.bsuir.serko.bettingapp.model.logic;

import by.bsuir.serko.bettingapp.controller.SessionRequestContent;
import java.util.Arrays;


public class SessionAttributesCleaner {
    
    private static final String[] temporaryAttributeNames = {"bets", "endedSportEvents", "transactions"};
    
    private SessionRequestContent sessionRequestContent;

    public SessionAttributesCleaner(SessionRequestContent sessionRequestContent) {
        this.sessionRequestContent = sessionRequestContent;
    }
    
    public void clearTemporaryAttributes() {
        Arrays.stream(temporaryAttributeNames).forEach(i -> sessionRequestContent.removeSessionAttribute(i));
    }
    
}
