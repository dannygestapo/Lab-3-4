
package by.bsuir.serko.bettingapp.db.dao;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.model.entity.SportEvent;
import java.util.List;


public interface SportEventDAO {
        
    void addSportEvent(SportEvent sportEvent) throws DatabaseException;
    List<SportEvent> findAllEndedSportEvents() throws DatabaseException;
    void updateSportEventResult(int sportEventId, String result) throws DatabaseException;
    
}
