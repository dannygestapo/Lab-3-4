
package by.bsuir.serko.bettingapp.db.dao.mysql;


import by.bsuir.serko.bettingapp.db.dao.BetDAO;
import by.bsuir.serko.bettingapp.db.dao.ComplexBetDAO;
import by.bsuir.serko.bettingapp.db.pool.ConnectionWrapper;
import by.bsuir.serko.bettingapp.db.dao.DAOFactory;
import by.bsuir.serko.bettingapp.db.dao.SportEventDAO;
import by.bsuir.serko.bettingapp.db.dao.TransactionDAO;
import by.bsuir.serko.bettingapp.db.dao.UserDAO;
import java.sql.Connection;


public class MySQLDAOFactory extends DAOFactory<Connection> {
    
    @Override
    public UserDAO getUserDAO(ConnectionWrapper<Connection> connectionWrapper) {
        return new MySQLUserDAO(connectionWrapper.getConnection());
    }

    @Override
    public BetDAO getBetDAO(ConnectionWrapper<Connection> connectionWrapper) {
        return new MySQLBetDAO(connectionWrapper.getConnection());
    }
    
    @Override
    public ComplexBetDAO getComplexBetDAO(ConnectionWrapper<Connection> connectionWrapper) {
        return new MySQLComplexBetDAO(connectionWrapper.getConnection());
    }

    @Override
    public SportEventDAO getSportEventDAO(ConnectionWrapper<Connection> connectionWrapper) {
        return new MySQLSportEventDAO(connectionWrapper.getConnection());
    }

    @Override
    public TransactionDAO getTransactionDAO(ConnectionWrapper<Connection> connectionWrapper) {
        return new MySQLTransactionDAO(connectionWrapper.getConnection());
    }
    
}