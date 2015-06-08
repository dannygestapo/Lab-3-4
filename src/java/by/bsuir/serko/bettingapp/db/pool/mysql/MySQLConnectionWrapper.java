
package by.bsuir.serko.bettingapp.db.pool.mysql;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.db.pool.ConnectionWrapper;
import java.sql.Connection;
import java.sql.SQLException;


public class MySQLConnectionWrapper extends ConnectionWrapper<Connection> {

    public MySQLConnectionWrapper(Connection connection) {
        super(connection);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws DatabaseException {
        try {
            getConnection().setAutoCommit(autoCommit);
        } catch(SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void rollback() throws DatabaseException {
        try {
            getConnection().rollback();
        } catch(SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void commit() throws DatabaseException {
        try {
            getConnection().commit();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
    
}
