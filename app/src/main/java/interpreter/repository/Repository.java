package interpreter.repository;

import interpreter.exceptions.ADTException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyList;

public class Repository implements IRepository {
    MyList<PrgState> states = new MyList<>();

    @Override
    public PrgState getCrtPrg() throws ADTException {
        return this.states.get(0);
    }

    @Override
    public void resetCrtPrg() throws ADTException {
        getCrtPrg().fullReset();
    }

    public void addPrg(PrgState state) {
        this.states.add(state);
    }
}
