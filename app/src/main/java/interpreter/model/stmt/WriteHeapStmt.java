package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IType;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

public record WriteHeapStmt(String varName, IExp exp) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getSymTable().isDefined(varName)) {
            throw new StatementException("Variable " + varName + " not defined");
        }
        if (!(state.getSymTable().lookup(varName) instanceof RefValue value))
            throw new StatementException("Variable " + varName + " is not of type RefType");
        if (!state.getHeap().isDefined(value.address())) {
            throw new StatementException("Heap does not contain address" + value.address());
        }
        IValue newValue = exp.eval(state.getSymTable(), state.getHeap());
        if(!(state.getHeap().get(value.address()).getType().equals(newValue.getType()))) {
            throw new StatementException("New value provided is not of the reference's type");
        }
        state.getHeap().update(value.address(), newValue);
        return state;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + exp + ")";
    }
}
