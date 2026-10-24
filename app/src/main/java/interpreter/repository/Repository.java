package interpreter.repository;

import interpreter.exceptions.ADTException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyList;

public class Repository implements IRepository {
    MyList<PrgState> states = new MyList<>();

    @Override
    public PrgState getCrtPrg() {
        try {
            return this.states.get(0);
        } catch (ADTException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPrg(PrgState state) {
        this.states.add(state);
    }
}
