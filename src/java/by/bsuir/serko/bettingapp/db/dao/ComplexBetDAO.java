
package by.bsuir.serko.bettingapp.db.dao;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.model.entity.ComplexBetWrapper;
import java.util.List;


public interface ComplexBetDAO {
    
    void addComplexBet(int userId, List<Integer> betIdList, int moneyAmount, double coefficient) throws DatabaseException;
    void updateComplexBetResults() throws DatabaseException;
    List<ComplexBetWrapper> findAll(int userId) throws DatabaseException;
    
}
