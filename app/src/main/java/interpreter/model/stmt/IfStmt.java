package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;

public record IfStmt(IExp exp, IStmt thenS, IStmt elseS) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var cond = exp.eval(state.getSymTable());
        if(!(cond instanceof BoolValue(boolean b1))) {
            throw new StatementException("condition's result is not BoolType");
        }
        var exeStack = state.getExeStack();
        if(b1) {
            exeStack.push(thenS);
        }
        else {
            exeStack.push(elseS);
        }
        return state;
    }

    @Override
    public String toString() {
        return "if " + exp + " then " + thenS + " else " + elseS;
    }
}
