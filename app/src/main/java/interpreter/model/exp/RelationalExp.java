package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public record RelationalExp(IExp left, IExp right, RelOp op) implements IExp {

    @Override
    public IValue eval(IDictionary<String, IValue> symTable, IHeap heap) throws MyException {
        if(!(left.eval(symTable, heap) instanceof IntValue(int val)))
            throw new ExpressionException("Left operand is not an integer");
        if(!(right.eval(symTable, heap) instanceof IntValue(int val1)))
            throw new ExpressionException("Right operand is not an integer");

        return switch (op) {
            case EQ -> new BoolValue(val == val1);
            case LT -> new BoolValue(val < val1);
            case LTE -> new BoolValue(val <= val1);
            case GT -> new BoolValue(val > val1);
            case GTE -> new BoolValue(val >= val1);
            case NEQ -> new BoolValue(val != val1);
        };
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}
