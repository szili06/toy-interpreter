package interpreter.repository;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

public interface IRepository {
    PrgState getCrtPrg() throws ADTException;

    void logPrgStateExec() throws MyException;
    void resetCrtPrg() throws ADTException;
}