
package by.bsuir.serko.bettingapp.db.pool;

import by.bsuir.serko.bettingapp.exception.DatabaseException;


public interface ConnectionPool<T> {
    
    ConnectionWrapper<T> getConnection() throws DatabaseException;
    void releaseConnection(ConnectionWrapper<T> connection);
    void close();
    
}