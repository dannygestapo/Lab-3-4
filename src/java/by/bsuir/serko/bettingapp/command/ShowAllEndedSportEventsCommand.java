
package by.bsuir.serko.bettingapp.command;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.db.pool.ConnectionWrapper;
import by.bsuir.serko.bettingapp.db.dao.DAOFactory;
import by.bsuir.serko.bettingapp.db.dao.SportEventDAO;
import by.bsuir.serko.bettingapp.model.entity.SportEvent;
import by.bsuir.serko.bettingapp.constant.PageType;
import by.bsuir.serko.bettingapp.utility.PathManager;
import by.bsuir.serko.bettingapp.constant.SessionAttributeType;
import by.bsuir.serko.bettingapp.controller.SessionRequestContent;
import java.util.List;

public class ShowAllEndedSportEventsCommand extends CloseableCommand {

    @Override
    public String executeAndClose(SessionRequestContent requestContent, ConnectionWrapper connection) throws DatabaseException {
        SportEventDAO sportEventDAO = DAOFactory.newInstance().getSportEventDAO(connection);
        List<SportEvent> endedSportEvents = sportEventDAO.findAllEndedSportEvents();
        requestContent.setSessionAttribute(SessionAttributeType.ENDED_SPORT_EVENTS.getName(), endedSportEvents);
        return PathManager.getPagePath(PageType.ADMIN_SET_RESULTS.getPageName());
    }
    
}
