
package by.bsuir.serko.bettingapp.command;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.exception.TechnicalException;
import by.bsuir.serko.bettingapp.db.pool.ConnectionWrapper;
import by.bsuir.serko.bettingapp.db.dao.DAOFactory;
import by.bsuir.serko.bettingapp.db.dao.SportEventDAO;
import by.bsuir.serko.bettingapp.model.entity.SportEvent;
import by.bsuir.serko.bettingapp.validation.AddSportEventsValidator;
import by.bsuir.serko.bettingapp.utility.JSONUtil;
import by.bsuir.serko.bettingapp.constant.PageType;
import by.bsuir.serko.bettingapp.utility.PathManager;
import by.bsuir.serko.bettingapp.controller.SessionRequestContent;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import org.apache.log4j.Logger;


public class AddSportEventsCommand extends CloseableCommand {

    public static Logger LOGGER = Logger.getLogger(AddSportEventsCommand.class);
    
    @Override
    public String executeAndClose(SessionRequestContent requestContent, ConnectionWrapper connection) throws DatabaseException {
        PageType page;
        try {
            List<SportEvent> validSportEvents = fetchValidSportEvents(requestContent);
            addSportEventsToDB(validSportEvents, connection);
            page = PageType.ADMIN_ACCOUNT;
        } catch (TechnicalException ex) {
            page = PageType.ERROR;
            LOGGER.error(ex);
        }
        return PathManager.getPagePath(page.getPageName());
    }
    
    private List<SportEvent> fetchValidSportEvents(SessionRequestContent requestContent) throws TechnicalException, DatabaseException {
        List<SportEvent> newSportEvents = fetchNewSportEvents(requestContent);
        AddSportEventsValidator addSportEventsValidator = new AddSportEventsValidator(newSportEvents);
        checkNewSportEventsValidity(addSportEventsValidator, requestContent);
        return addSportEventsValidator.getValidSportEvents();
    }
    
    private List<SportEvent> fetchNewSportEvents(SessionRequestContent requestContent) throws TechnicalException{
        String newSportEventsJSON = requestContent.getRequestParameter("newSportEvents");
        return JSONUtil.fromJSON(newSportEventsJSON, new TypeReference<List<SportEvent>>() {});
    }
    
    private void checkNewSportEventsValidity(AddSportEventsValidator addSportEventsValidator, SessionRequestContent requestContent) throws DatabaseException {
        if (!addSportEventsValidator.checkValidity()) {
            requestContent.setRequestAttribute("errorMessageKey", addSportEventsValidator.getErrorMessageKey());
        }
    }
    
    private void addSportEventsToDB(List<SportEvent> validSportEvents, ConnectionWrapper connection) throws DatabaseException{
        SportEventDAO sportEventDAO = DAOFactory.newInstance().getSportEventDAO(connection);
        for (SportEvent validSportEvent : validSportEvents) {
            sportEventDAO.addSportEvent(validSportEvent);
        }
    }
    
}
