
package by.bsuir.serko.bettingapp.command;

import by.bsuir.serko.bettingapp.exception.DatabaseException;
import by.bsuir.serko.bettingapp.exception.TechnicalException;
import by.bsuir.serko.bettingapp.db.dao.BetDAO;
import by.bsuir.serko.bettingapp.db.dao.ComplexBetDAO;
import by.bsuir.serko.bettingapp.db.pool.ConnectionWrapper;
import by.bsuir.serko.bettingapp.db.dao.DAOFactory;
import by.bsuir.serko.bettingapp.db.dao.SportEventDAO;
import by.bsuir.serko.bettingapp.db.dao.UserDAO;
import by.bsuir.serko.bettingapp.model.entity.BetType;
import by.bsuir.serko.bettingapp.model.entity.Score;
import by.bsuir.serko.bettingapp.validation.AddSportEventResultsValidator;
import by.bsuir.serko.bettingapp.utility.JSONUtil;
import by.bsuir.serko.bettingapp.constant.PageType;
import by.bsuir.serko.bettingapp.utility.container.Pair;
import by.bsuir.serko.bettingapp.utility.PathManager;
import by.bsuir.serko.bettingapp.constant.RequestAttributeType;
import by.bsuir.serko.bettingapp.constant.RequestParameterType;
import by.bsuir.serko.bettingapp.controller.SessionRequestContent;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import org.apache.log4j.Logger;


public class AddSportEventResultsCommand extends TransactionCommand {
    
    private static final Logger LOGGER = Logger.getLogger(AddSportEventResultsCommand.class);

    @Override
    public String makeTransactionAndClose(SessionRequestContent requestContent, ConnectionWrapper connection) throws DatabaseException {
        PageType page;
        try {
            List<Pair<Integer, Score>> validSportEventResults = fetchValidSportEventResults(requestContent);
            updateSportEventResults(validSportEventResults, connection);
            updateBetResults(validSportEventResults, connection);
            List<Pair<Integer, Integer>> usersPrizeMoney = fetchWinners(connection);
            updateWinnersMoney(usersPrizeMoney, connection);
            updateComplexBetsResults(connection);
            page = PageType.ADMIN_ACCOUNT;
        } catch (TechnicalException ex) {
            LOGGER.error(ex);
            page = PageType.ERROR;
        }
        return PathManager.getPagePath(page.getPageName());
    }
    
    private List<Pair<Integer, Score>> fetchSportEvents(SessionRequestContent requestContent) throws TechnicalException {
        String jsonSportEventResults = requestContent.getRequestParameter(RequestParameterType.SPORT_EVENT_RESULTS.getName());
        return JSONUtil.fromJSON(jsonSportEventResults, new TypeReference<List<Pair<Integer, Score>>>(){});
    }
    
    private void checkSportEventResultsValidity(AddSportEventResultsValidator addSportEventResultsValidator, SessionRequestContent requestContent) throws DatabaseException {
        if (!addSportEventResultsValidator.checkValidity()) {
            requestContent.setRequestAttribute(RequestAttributeType.ERROR_MESSAGE_KEY.getName(), addSportEventResultsValidator.getErrorMessageKey());
        }
    }
    
    public List<Pair<Integer, Score>> fetchValidSportEventResults(SessionRequestContent requestContent) throws DatabaseException, TechnicalException {
        List<Pair<Integer, Score>> sportEventResults = fetchSportEvents(requestContent);
        AddSportEventResultsValidator addSportEventResultsValidator = new AddSportEventResultsValidator(sportEventResults);
        checkSportEventResultsValidity(addSportEventResultsValidator, requestContent);
        return addSportEventResultsValidator.getValidSportEventResults();
    }
    
    private void updateSportEventResults(List<Pair<Integer, Score>> validSportEventResults, ConnectionWrapper connection) throws DatabaseException {
        SportEventDAO sportEventDAO = DAOFactory.newInstance().getSportEventDAO(connection);
        for (Pair<Integer, Score> sportEventResult : validSportEventResults) {
            sportEventDAO.updateSportEventResult(sportEventResult.getFirst(), sportEventResult.getSecond().toString());
        }
    }
    
    private void updateBetResults(List<Pair<Integer, Score>> validSportEventResults, ConnectionWrapper connection) throws DatabaseException {
        BetDAO betDAO = DAOFactory.newInstance().getBetDAO(connection);
        for (Pair<Integer, Score> sportEventResult : validSportEventResults) {
            List<Pair<Integer, BetType>> betsInfo = betDAO.findAllConnectedBets(sportEventResult.getFirst());
            Score score = sportEventResult.getSecond();
            for (Pair<Integer, BetType> betInfo : betsInfo) {
                betDAO.updateBetResult(betInfo.getFirst(), betInfo.getSecond().hasWon(score));
            }
        }
    }
    
    private void updateComplexBetsResults(ConnectionWrapper connection) throws DatabaseException {
        ComplexBetDAO complexBetDAO = DAOFactory.newInstance().getComplexBetDAO(connection);
        complexBetDAO.updateComplexBetResults();
    }
     
    private List<Pair<Integer, Integer>> fetchWinners(ConnectionWrapper connection) throws DatabaseException {
        UserDAO userDAO = DAOFactory.newInstance().getUserDAO(connection);
        return userDAO.findUserPrizeMoney();
    }
    
    private void updateWinnersMoney(List<Pair<Integer, Integer>> usersPrizeMoney, ConnectionWrapper connection) throws DatabaseException {
        UserDAO userDAO = DAOFactory.newInstance().getUserDAO(connection);
        for (Pair<Integer, Integer> userPrizeMoney : usersPrizeMoney) {
            userDAO.addUserMoney(userPrizeMoney.getFirst(), userPrizeMoney.getSecond());
        }
    }
}