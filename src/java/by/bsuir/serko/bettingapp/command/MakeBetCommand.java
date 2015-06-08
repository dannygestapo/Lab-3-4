
package by.bsuir.serko.bettingapp.command;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.db.dao.ComplexBetDAO;
import by.bsuir.serko.bettingapp.db.pool.ConnectionWrapper;
import by.bsuir.serko.bettingapp.db.dao.DAOFactory;
import by.bsuir.serko.bettingapp.db.dao.UserDAO;
import by.bsuir.serko.bettingapp.model.entity.SimpleUser;
import by.bsuir.serko.bettingapp.validation.MakeBetValidator;
import by.bsuir.serko.bettingapp.model.logic.UserMoneyLogic;
import by.bsuir.serko.bettingapp.constant.PageType;
import by.bsuir.serko.bettingapp.utility.PathManager;
import by.bsuir.serko.bettingapp.constant.RequestAttributeType;
import by.bsuir.serko.bettingapp.constant.RequestParameterType;
import by.bsuir.serko.bettingapp.constant.SessionAttributeType;
import by.bsuir.serko.bettingapp.controller.SessionRequestContent;


public class MakeBetCommand extends TransactionCommand {
    

    @Override
    public String makeTransactionAndClose(SessionRequestContent requestContent, ConnectionWrapper connection) throws DatabaseException {
        UserMoneyLogic.updateUserMoney(requestContent, connection);
        SimpleUser currentUser = (SimpleUser) requestContent.getSessionAttribute(SessionAttributeType.USER.getName());
        MakeBetValidator makeBetValidator = createMakeBetValidator(currentUser,  requestContent);
        if(makeBetValidator.checkValidity()) {
            makeBet(makeBetValidator, currentUser, connection);
        } else {
            requestContent.setRequestAttribute(RequestAttributeType.ERROR_MESSAGE_KEY.getName(), makeBetValidator.getErrorMessageKey());
        }
        return PathManager.getPagePath(PageType.ACCOUNT.getPageName());
    }
    
    private MakeBetValidator createMakeBetValidator(SimpleUser currentUser, SessionRequestContent requestContent) {
        String resultBet = requestContent.getRequestParameter(RequestParameterType.RESULT_BET.getName());
        String resultBetCoefficient = requestContent.getRequestParameter(RequestParameterType.RESULT_BET_COEFFICIENT.getName());
        String earliestBetStartTime = requestContent.getRequestParameter(RequestParameterType.EARLIEST_BET_START_TIME.getName());
        String betMoney = requestContent.getRequestParameter(RequestParameterType.MONEY_AMOUNT.getName());
        return new MakeBetValidator(betMoney, resultBetCoefficient, currentUser, resultBet, earliestBetStartTime);
    }
    
    private void makeBet(MakeBetValidator makeBetValidator, SimpleUser currentUser, ConnectionWrapper connection) throws DatabaseException {
        addComplexBetToDB(makeBetValidator, currentUser, connection);
        updateUserMoney(makeBetValidator, currentUser, connection);
    }
    
    private void addComplexBetToDB(MakeBetValidator makeBetValidator, SimpleUser currentUser, ConnectionWrapper connection) throws DatabaseException {
        ComplexBetDAO complexBetDAO = DAOFactory.newInstance().getComplexBetDAO(connection);
        complexBetDAO.addComplexBet(currentUser.getId(), makeBetValidator.getValidBetIdList(), 
                makeBetValidator.getValidBetMoneyAmount(), makeBetValidator.getValidBetCoefficient());
    }
    
    private void updateUserMoney(MakeBetValidator makeBetValidator, SimpleUser currentUser, ConnectionWrapper connection) throws DatabaseException {
        UserDAO userDAO = DAOFactory.newInstance().getUserDAO(connection);
        int currentMoneyAmount = currentUser.getMoneyAmount() - makeBetValidator.getValidBetMoneyAmount();
        userDAO.updateUserMoney(currentUser.getId(), currentMoneyAmount);
        currentUser.setMoneyAmount(currentMoneyAmount);
    }
    
}