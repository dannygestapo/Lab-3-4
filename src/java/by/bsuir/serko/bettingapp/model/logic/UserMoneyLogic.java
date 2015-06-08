
package by.bsuir.serko.bettingapp.model.logic;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.db.pool.ConnectionWrapper;
import by.bsuir.serko.bettingapp.db.dao.DAOFactory;
import by.bsuir.serko.bettingapp.db.dao.UserDAO;
import by.bsuir.serko.bettingapp.model.entity.SimpleUser;
import by.bsuir.serko.bettingapp.controller.SessionRequestContent;


public class UserMoneyLogic {
    
    public static void updateUserMoney(SessionRequestContent requestContent, ConnectionWrapper connection) throws DatabaseException {   
        SimpleUser user = (SimpleUser) requestContent.getSessionAttribute("user");
        UserDAO userDAO = DAOFactory.newInstance().getUserDAO(connection);
        int moneyAmount = userDAO.findUserMoney(user.getId());
        if (moneyAmount != -1) {
            user.setMoneyAmount(moneyAmount);
        }
    }
    
}