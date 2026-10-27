package interpreter.repository;

import interpreter.exceptions.ADTException;
import interpreter.model.PrgState;

public interface IRepository {
    PrgState getCrtPrg() throws ADTException;

    void resetCrtPrg() throws ADTException;

    void addPrg(PrgState state);
}