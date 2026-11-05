package interpreter.repository;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.RepositoryException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Repository implements IRepository {
    private final MyList<PrgState> states = new MyList<>();
    private final String logFilePath;

    public Repository(PrgState state, String logFilePath) {
        states.add(state);
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCrtPrg() throws ADTException {
        return this.states.get(0);
    }

    @Override
    public void logPrgStateExec() throws MyException {
        try (var logFile = new PrintWriter(
                new BufferedWriter(new FileWriter(logFilePath, true)))) { // append
            logFile.println(getCrtPrg().toLogString());
        } catch (IOException e) {
            throw new RepositoryException("cannot write the log file " + logFilePath
                    + ": " + e.getMessage());
        }
    }

    @Override
    public void resetCrtPrg() throws ADTException {
        getCrtPrg().fullReset();
    }
}
