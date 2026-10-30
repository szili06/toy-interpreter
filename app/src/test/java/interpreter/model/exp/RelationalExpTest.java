package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {
    private RelationalExp createExp(int left, int right, RelOp op) {
        return new RelationalExp(new ValueExp(new IntValue(left)), new ValueExp(new IntValue(right)), op);
    }

    @Test
    void testEqual() throws MyException {
        RelationalExp exp = createExp(5, 5, RelOp.EQ);
        assertEquals(new BoolValue(true), exp.eval(new MyDictionary<>()));
        exp = createExp(5, 10, RelOp.EQ);
        assertEquals(new BoolValue(false), exp.eval(new MyDictionary<>()));
    }

    @Test
    void testNotEqual() throws MyException {
        RelationalExp exp = createExp(5, 10, RelOp.NEQ);
        assertEquals(new BoolValue(true), exp.eval(new MyDictionary<>()));
        exp = createExp(5, 5, RelOp.NEQ);
        assertEquals(new BoolValue(false), exp.eval(new MyDictionary<>()));
    }

    @Test
    void testGreaterThan() throws MyException {
        RelationalExp exp = createExp(5, 4, RelOp.GT);
        assertEquals(new BoolValue(true), exp.eval(new MyDictionary<>()));
        exp = createExp(4, 5, RelOp.GT);
        assertEquals(new BoolValue(false), exp.eval(new MyDictionary<>()));
    }

    @Test
    void testGreaterThanOrEquals() throws MyException {
        RelationalExp exp = createExp(5, 4, RelOp.GTE);
        assertEquals(new BoolValue(true), exp.eval(new MyDictionary<>()));
        exp = createExp(4, 5, RelOp.GTE);
        assertEquals(new BoolValue(false), exp.eval(new MyDictionary<>()));
    }

    @Test
    void testLessThan() throws MyException {
        RelationalExp exp = createExp(5,10, RelOp.LT);
        assertEquals(new BoolValue(true), exp.eval(new MyDictionary<>()));
        exp = createExp(10, 5, RelOp.LT);
        assertEquals(new BoolValue(false), exp.eval(new MyDictionary<>()));
    }

    @Test
    void testLessThanOrEquals() throws MyException {
        RelationalExp exp = createExp(5, 10, RelOp.LTE);
        assertEquals(new BoolValue(true), exp.eval(new MyDictionary<>()));
        exp = createExp(10, 5, RelOp.LTE);
        assertEquals(new BoolValue(false), exp.eval(new MyDictionary<>()));
    }

    @Test
    void testNonIntegerOperandThrows() {
        var exp1 = new RelationalExp(new ValueExp(new IntValue(5)), new ValueExp(new BoolValue(false)), RelOp.EQ);
        assertThrows(ExpressionException.class, () -> exp1.eval(new MyDictionary<>()) );
        var exp2 = new RelationalExp(new ValueExp(new BoolValue(true)), new ValueExp(new IntValue(10)), RelOp.EQ);
        assertThrows(ExpressionException.class, () -> exp2.eval(new MyDictionary<>()));
    }
}