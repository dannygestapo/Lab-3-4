
package by.bsuir.serko.bettingapp.command;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.db.pool.ConnectionWrapper;
import by.bsuir.serko.bettingapp.db.dao.DAOFactory;
import by.bsuir.serko.bettingapp.db.dao.TransactionDAO;
import by.bsuir.serko.bettingapp.model.entity.Transaction;
import by.bsuir.serko.bettingapp.model.entity.User;
import by.bsuir.serko.bettingapp.model.logic.UserMoneyLogic;
import by.bsuir.serko.bettingapp.constant.PageType;
import by.bsuir.serko.bettingapp.utility.PathManager;
import by.bsuir.serko.bettingapp.constant.SessionAttributeType;
import by.bsuir.serko.bettingapp.controller.SessionRequestContent;
import java.util.List;


public class ShowAllTransactionsCommand extends CloseableCommand {

    @Override
    public String executeAndClose(SessionRequestContent requestContent, ConnectionWrapper connection) throws DatabaseException {
        UserMoneyLogic.updateUserMoney(requestContent, connection);
        TransactionDAO transactionDAO = DAOFactory.newInstance().getTransactionDAO(connection);
        User currentUser = (User) requestContent.getSessionAttribute(SessionAttributeType.USER.getName());
        List<Transaction> transactions = transactionDAO.findAll(currentUser.getId());
        requestContent.setSessionAttribute(SessionAttributeType.TRANSACTIONS.getName(), transactions);
        return PathManager.getPagePath(PageType.TRANSACTIONS.getPageName());
    }
    
}
