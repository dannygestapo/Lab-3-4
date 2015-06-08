
package by.bsuir.serko.bettingapp.db.dao;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.model.entity.BetType;
import by.bsuir.serko.bettingapp.model.entity.BetWrapper;
import by.bsuir.serko.bettingapp.utility.container.Pair;
import java.util.List;


public interface BetDAO {
    
    List<BetWrapper> findAllAndWrap() throws DatabaseException;
    List<BetWrapper> findAllAndWrapIncludingSpare() throws DatabaseException;
    void addBet(int sportEventId, BetType betType, double coefficient) throws DatabaseException;
    void updateBetResult(int betId, boolean result) throws DatabaseException;
    List<Pair<Integer, BetType>> findAllConnectedBets(int sportEventId) throws DatabaseException;
}
