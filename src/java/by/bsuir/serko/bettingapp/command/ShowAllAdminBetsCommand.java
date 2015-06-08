
package by.bsuir.serko.bettingapp.command;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.db.dao.BetDAO;
import by.bsuir.serko.bettingapp.db.pool.ConnectionWrapper;
import by.bsuir.serko.bettingapp.db.dao.DAOFactory;
import by.bsuir.serko.bettingapp.model.entity.BetWrapper;
import by.bsuir.serko.bettingapp.constant.PageType;
import by.bsuir.serko.bettingapp.utility.PathManager;
import by.bsuir.serko.bettingapp.constant.SessionAttributeType;
import by.bsuir.serko.bettingapp.controller.SessionRequestContent;
import java.util.List;


public class ShowAllAdminBetsCommand extends CloseableCommand {

    @Override
    public String executeAndClose(SessionRequestContent requestContent, ConnectionWrapper connection) throws DatabaseException {
        DAOFactory daoFactory = DAOFactory.newInstance();
        BetDAO betDAO = daoFactory.getBetDAO(connection);
        List<BetWrapper> adminBets = betDAO.findAllAndWrapIncludingSpare();
        requestContent.setSessionAttribute(SessionAttributeType.ADMIN_BETS.getName(), adminBets);
        return PathManager.getPagePath(PageType.ADMIN_ADD_BETS.getPageName());
    }
    
}
