package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithExpTest {
    private MyDictionary<String, IValue> dict;
    private MyHeap heap;

    @BeforeEach
    void setupEach() {
        dict = new MyDictionary<>();
        heap = new MyHeap();
    }
    @Test
    void addWorks() throws MyException {
        var exp = new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(2)), new ValueExp(new IntValue(3)));
        var exp2 = new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(-2)), new ValueExp(new IntValue(2)));
        assertEquals(new IntValue(5), exp.eval(dict, heap));
        assertEquals(new IntValue(0), exp2.eval(dict, heap));
    }

    @Test
    void subWorks() throws MyException {
        var exp = new ArithExp(ArithOp.SUB, new ValueExp(new IntValue(5)), new ValueExp(new IntValue(2)));
        var exp2 = new ArithExp(ArithOp.SUB, new ValueExp(new IntValue(5)), new ValueExp(new IntValue(-2)));
        assertEquals(new IntValue(3), exp.eval(dict, heap));
        assertEquals(new IntValue(7), exp2.eval(dict, heap));
    }

    @Test
    void mulWorks() throws MyException {
        var exp = new ArithExp(ArithOp.MUL, new ValueExp(new IntValue(2)), new ValueExp(new IntValue(3)));
        var exp2 = new ArithExp(ArithOp.MUL, new ValueExp(new IntValue(-2)), new ValueExp(new IntValue(2)));
        assertEquals(new IntValue(6), exp.eval(dict, heap));
        assertEquals(new IntValue(-4), exp2.eval(dict, heap));
    }

    @Test
    void divWorks() throws MyException {
        var exp = new ArithExp(ArithOp.DIV, new ValueExp(new IntValue(6)), new ValueExp(new IntValue(3)));
        var exp2 = new ArithExp(ArithOp.DIV, new ValueExp(new IntValue(-10)), new ValueExp(new IntValue(3)));
        assertEquals(new IntValue(2), exp.eval(dict, heap));
        assertEquals(new IntValue(-3), exp2.eval(dict, heap));
    }

    @Test
    void divisionByZeroThrows() {
        var exp = new ArithExp(ArithOp.DIV, new ValueExp(new IntValue(2)), new ValueExp(new IntValue(0)));
        assertThrows(ExpressionException.class, () -> exp.eval(dict, heap));
    }

    @Test
    void nonIntegerOperandThrows() {
        var exp = new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(2)), new ValueExp(new BoolValue(false)));
        assertThrows(ExpressionException.class, () -> exp.eval(dict, heap));
        var exp2 = new ArithExp(ArithOp.ADD, new ValueExp(new BoolValue(true)), new ValueExp(new IntValue(3)));
        assertThrows(ExpressionException.class, () -> exp2.eval(dict, heap));
    }
}