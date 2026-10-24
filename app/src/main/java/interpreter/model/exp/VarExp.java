package interpreter.model.exp;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public record VarExp(String var) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws ADTException {
        try {
            return symTable.lookup(var);
        } catch (MyException e) {
            throw new ExpressionException("variable not defined");
        }
    }

    @Override
    public String toString() {
        return var;
    }
}
