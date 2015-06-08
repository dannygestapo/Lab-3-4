
package by.bsuir.serko.bettingapp.command;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.db.pool.ConnectionWrapper;
import by.bsuir.serko.bettingapp.db.dao.DAOFactory;
import by.bsuir.serko.bettingapp.db.dao.TransactionDAO;
import by.bsuir.serko.bettingapp.db.dao.UserDAO;
import by.bsuir.serko.bettingapp.model.entity.SimpleUser;
import by.bsuir.serko.bettingapp.model.entity.TransactionType;
import by.bsuir.serko.bettingapp.validation.MakeTransactionValidator;
import by.bsuir.serko.bettingapp.constant.PageType;
import by.bsuir.serko.bettingapp.utility.PathManager;
import by.bsuir.serko.bettingapp.constant.RequestAttributeType;
import by.bsuir.serko.bettingapp.constant.RequestParameterType;
import by.bsuir.serko.bettingapp.constant.SessionAttributeType;
import by.bsuir.serko.bettingapp.controller.SessionRequestContent;


public class MakeTransactionCommand extends TransactionCommand {
    
    private static final String NOT_ENOUGH_MONEY_ERROR = "error.label.notEnoughMoney";
    
    @Override
    public String makeTransactionAndClose(SessionRequestContent requestContent, ConnectionWrapper connection) throws DatabaseException {
        MakeTransactionValidator makeTransactionValidator = createTransactionValidator(requestContent);
        if(makeTransactionValidator.checkValidity()) {
            TransactionType validTransactionType = makeTransactionValidator.getValidTransactionType();
            int validMoneyAmount = makeTransactionValidator.getValidMoneyAmount();
            SimpleUser user = (SimpleUser)requestContent.getSessionAttribute(SessionAttributeType.USER.getName());
            addTransactionToDB(user.getId(), validMoneyAmount, validTransactionType, connection);
            performTransaction(validTransactionType, user, validMoneyAmount, requestContent, connection);
        
        } else {
            requestContent.setRequestAttribute(RequestAttributeType.ERROR_MESSAGE_KEY.getName(), makeTransactionValidator.getErrorMessageKey());
        }        
        return PathManager.getPagePath(PageType.ACCOUNT.getPageName());
    }
    
    private void addTransactionToDB(int userId, int moneyAmount, TransactionType transactionType, ConnectionWrapper connection) throws DatabaseException {
        TransactionDAO transactionDAO = DAOFactory.newInstance().getTransactionDAO(connection);
        transactionDAO.addTransaction(userId, moneyAmount, transactionType);
    }
    
    private MakeTransactionValidator createTransactionValidator(SessionRequestContent requestContent) {
        String creditCardType = requestContent.getRequestParameter(RequestParameterType.CREDIT_CARD_TYPE.getName());
        String creditCardNumber = requestContent.getRequestParameter(RequestParameterType.CREDIT_CARD_NUMBER.getName());
        String securityCode = requestContent.getRequestParameter(RequestParameterType.SECURITY_CODE.getName());
        String moneyAmount = requestContent.getRequestParameter(RequestParameterType.MONEY_AMOUNT.getName());
        String transactionType = requestContent.getRequestParameter(RequestParameterType.TRANSACTION_TYPE.getName());
        return new MakeTransactionValidator(creditCardType, creditCardNumber, securityCode, moneyAmount, transactionType);
    }
    
    private void performTransaction(TransactionType transactionType, SimpleUser user, int moneyAmount, SessionRequestContent requestContent,  ConnectionWrapper connection) throws DatabaseException {
        switch (transactionType) {
            case DEPOSIT:
                makeDeposit(user, moneyAmount, requestContent, connection);
                break;
            case WITHDRAWAL:
                makeWithdrawal(user, moneyAmount, requestContent, connection);
                break;
            default:
                throw new EnumConstantNotPresentException(TransactionType.class, String.valueOf(transactionType));
        }
    }
    
    private void makeDeposit(SimpleUser user, int moneyAmount, SessionRequestContent requestContent,  ConnectionWrapper connection) throws DatabaseException {
        int newMoneyAmount = user.getMoneyAmount() + moneyAmount;
        user.setMoneyAmount(newMoneyAmount);
        UserDAO userDAO = DAOFactory.newInstance().getUserDAO(connection);
        userDAO.updateUserMoney(user.getId(), newMoneyAmount);
    }
    
    private void makeWithdrawal(SimpleUser user, int moneyAmount, SessionRequestContent requestContent,  ConnectionWrapper connection) throws DatabaseException {
        if(user.getMoneyAmount() >= moneyAmount) {
            int newMoneyAmount = user.getMoneyAmount() - moneyAmount; 
            user.setMoneyAmount(newMoneyAmount);
            UserDAO userDAO = DAOFactory.newInstance().getUserDAO(connection);
            userDAO.updateUserMoney(user.getId(), newMoneyAmount);
        } else {
            requestContent.setRequestAttribute(RequestAttributeType.ERROR_MESSAGE_KEY.getName(), NOT_ENOUGH_MONEY_ERROR);
        }
    }
    
}