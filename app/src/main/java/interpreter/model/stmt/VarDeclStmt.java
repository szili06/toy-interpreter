package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.type.IType;

public record VarDeclStmt(String name, IType type) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        if(symTable.isDefined(name)) {
            throw new StatementException("variable " + name + " already declared");
        }
        var val = type.defaultValue();
        symTable.put(name, val);
        return state;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }
}
