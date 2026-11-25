package interpreter.model.exp;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

public record ReadHeapExp(IExp exp) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable, IHeap heap) throws MyException {
        if (!(exp.eval(symTable, heap) instanceof RefValue value)) {
            throw new ExpressionException("Inner expression didn't evaluate to a RefValue!");
        }
        try {
            return heap.get(value.address());
        }
        catch (ADTException e) {
            throw new ExpressionException("Reference not defined in heap!");
        }
    }

    @Override
    public String toString() {
        return "rH(" + exp + ")";
    }
}
