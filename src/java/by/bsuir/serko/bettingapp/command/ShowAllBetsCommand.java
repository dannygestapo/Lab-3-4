
package by.bsuir.serko.bettingapp.command;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.db.dao.BetDAO;
import by.bsuir.serko.bettingapp.db.pool.ConnectionWrapper;
import by.bsuir.serko.bettingapp.db.dao.DAOFactory;
import by.bsuir.serko.bettingapp.model.entity.BetWrapper;
import by.bsuir.serko.bettingapp.model.logic.UserMoneyLogic;
import by.bsuir.serko.bettingapp.constant.PageType;
import by.bsuir.serko.bettingapp.utility.PathManager;
import by.bsuir.serko.bettingapp.constant.SessionAttributeType;
import by.bsuir.serko.bettingapp.controller.SessionRequestContent;
import java.util.List;

public class ShowAllBetsCommand extends CloseableCommand {

    @Override
    public String executeAndClose(SessionRequestContent requestContent, ConnectionWrapper connection) throws DatabaseException {
        UserMoneyLogic.updateUserMoney(requestContent, connection);
        DAOFactory daoFactory = DAOFactory.newInstance();
        BetDAO betDAO = daoFactory.getBetDAO(connection);
        List<BetWrapper> bets = betDAO.findAllAndWrap();
        requestContent.setSessionAttribute(SessionAttributeType.BETS.getName(), bets);
        return PathManager.getPagePath(PageType.ALL_BETS.getPageName());
    }
    
}