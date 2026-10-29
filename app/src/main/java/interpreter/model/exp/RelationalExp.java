package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public record RelationalExp(IExp left, IExp right, RelOp op) implements IExp {

    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        if(!(left.eval(symTable) instanceof IntValue))
            throw new ExpressionException("Left operand is not an integer");
        if(!(right.eval(symTable) instanceof IntValue))
            throw new ExpressionException("Right operand is not an integer");

        IntValue leftValue = (IntValue) left.eval(symTable);
        IntValue rightValue = (IntValue) right.eval(symTable);

        return switch (op) {
            case EQ -> new BoolValue(leftValue.val() == rightValue.val());
            case LT -> new BoolValue(leftValue.val() < rightValue.val());
            case LTE -> new BoolValue(leftValue.val() <= rightValue.val());
            case GT -> new BoolValue(leftValue.val() > rightValue.val());
            case GTE -> new BoolValue(leftValue.val() >= rightValue.val());
            case NEQ -> new BoolValue(leftValue.val() != rightValue.val());
        };
    }

    @Override
    public String toString() {
        return left + " " + op + " " + right;
    }
}
