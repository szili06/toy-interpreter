package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;

public record PrintStmt(IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var val = exp.eval(state.getSymTable());
        state.getOut().add(val);
        return state;
    }

    @Override
    public String toString() {
        return "print(" + exp + ")";
    }
}
