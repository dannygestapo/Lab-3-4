
package by.bsuir.serko.bettingapp.command;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.db.pool.ConnectionPool;
import by.bsuir.serko.bettingapp.db.pool.ConnectionWrapper;
import by.bsuir.serko.bettingapp.db.dao.DAOFactory;
import by.bsuir.serko.bettingapp.constant.PageType;
import by.bsuir.serko.bettingapp.utility.PathManager;
import by.bsuir.serko.bettingapp.controller.SessionRequestContent;
import org.apache.log4j.Logger;


public abstract class CloseableCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(CloseableCommand.class);

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page = null;
        DAOFactory daoFactory = DAOFactory.newInstance();
        ConnectionPool connectionPool = daoFactory.getConnectionPool();
        ConnectionWrapper connection = null;
        try {
            connection = connectionPool.getConnection();
            page = executeAndClose(requestContent, connection);
        } catch (DatabaseException e) {
            LOGGER.error(e);
            page = PathManager.getPagePath(PageType.ERROR.getPageName());
        } finally {
            if(connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        return page;
    }
    
    public abstract String executeAndClose(SessionRequestContent requestContent, ConnectionWrapper connection) throws DatabaseException;
    
}
