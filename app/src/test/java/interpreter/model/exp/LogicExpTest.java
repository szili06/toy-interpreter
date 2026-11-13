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

class LogicExpTest {
    private MyDictionary<String, IValue> symTable;
    private MyHeap heap;

    @BeforeEach
    void setupEach() {
        symTable = new MyDictionary<>();
        heap = new MyHeap();
    }

    @Test
    void andWorks() throws MyException {
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.AND, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(true))).eval(symTable, heap));
        assertEquals(new BoolValue(false), new LogicExp(LogicOp.AND, new ValueExp(new BoolValue(false)), new ValueExp(new BoolValue(true))).eval(symTable, heap));
        assertEquals(new BoolValue(false), new LogicExp(LogicOp.AND, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(false))).eval(symTable, heap));
        assertEquals(new BoolValue(false), new LogicExp(LogicOp.AND, new ValueExp(new BoolValue(false)), new ValueExp(new BoolValue(false))).eval(symTable, heap));
    }

    @Test
    void orWorks() throws MyException {
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.OR, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(true))).eval(symTable, heap));
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.OR, new ValueExp(new BoolValue(false)), new ValueExp(new BoolValue(true))).eval(symTable, heap));
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.OR, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(false))).eval(symTable, heap));
        assertEquals(new BoolValue(false), new LogicExp(LogicOp.OR, new ValueExp(new BoolValue(false)), new ValueExp(new BoolValue(false))).eval(symTable, heap));
    }

    @Test
    void nonboolOperandThrows() {
        assertThrows(ExpressionException.class, () -> new LogicExp(LogicOp.AND, new ValueExp(new IntValue(5)), new ValueExp(new BoolValue(true))).eval(symTable, heap));
        assertThrows(ExpressionException.class, () -> new LogicExp(LogicOp.AND, new ValueExp(new BoolValue(true)), new ValueExp(new IntValue(5))).eval(symTable, heap));
    }
}