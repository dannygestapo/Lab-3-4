
package by.bsuir.serko.bettingapp.db.dao;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.model.entity.Transaction;
import by.bsuir.serko.bettingapp.model.entity.TransactionType;
import java.util.List;


public interface TransactionDAO {
    
    List<Transaction> findAll(int userId) throws DatabaseException;
    void addTransaction(int userId, int moneyAmount, TransactionType transactionType) throws DatabaseException; 
}
