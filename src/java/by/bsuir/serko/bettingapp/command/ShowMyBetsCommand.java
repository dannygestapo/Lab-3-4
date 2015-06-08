
package by.bsuir.serko.bettingapp.command;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.db.dao.ComplexBetDAO;
import by.bsuir.serko.bettingapp.db.pool.ConnectionWrapper;
import by.bsuir.serko.bettingapp.db.dao.DAOFactory;
import by.bsuir.serko.bettingapp.model.entity.ComplexBetWrapper;
import by.bsuir.serko.bettingapp.model.entity.User;
import by.bsuir.serko.bettingapp.constant.PageType;
import by.bsuir.serko.bettingapp.utility.PathManager;
import by.bsuir.serko.bettingapp.constant.SessionAttributeType;
import by.bsuir.serko.bettingapp.controller.SessionRequestContent;
import java.util.List;


public class ShowMyBetsCommand extends CloseableCommand {

    @Override
    public String executeAndClose(SessionRequestContent requestContent, ConnectionWrapper connection) throws DatabaseException {
        ComplexBetDAO complexBetDAO = DAOFactory.newInstance().getComplexBetDAO(connection);
        User user = (User)requestContent.getSessionAttribute(SessionAttributeType.USER.getName());
        List<ComplexBetWrapper> complexBets = complexBetDAO.findAll(user.getId());
        requestContent.setSessionAttribute(SessionAttributeType.COMPLEX_BETS.getName(), complexBets);
        return PathManager.getPagePath(PageType.MY_BETS.getPageName());
    }
    
}
