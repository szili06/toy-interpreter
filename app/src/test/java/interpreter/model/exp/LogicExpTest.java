package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicExpTest {
    private MyDictionary<String, IValue> symTable;

    @BeforeEach
    void setupEach() {
        symTable = new MyDictionary<>();
    }

    @Test
    void andWorks() throws MyException {
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.AND, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(true))).eval(symTable));
        assertEquals(new BoolValue(false), new LogicExp(LogicOp.AND, new ValueExp(new BoolValue(false)), new ValueExp(new BoolValue(true))).eval(symTable));
        assertEquals(new BoolValue(false), new LogicExp(LogicOp.AND, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(false))).eval(symTable));
        assertEquals(new BoolValue(false), new LogicExp(LogicOp.AND, new ValueExp(new BoolValue(false)), new ValueExp(new BoolValue(false))).eval(symTable));
    }

    @Test
    void orWorks() throws MyException {
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.OR, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(true))).eval(symTable));
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.OR, new ValueExp(new BoolValue(false)), new ValueExp(new BoolValue(true))).eval(symTable));
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.OR, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(false))).eval(symTable));
        assertEquals(new BoolValue(false), new LogicExp(LogicOp.OR, new ValueExp(new BoolValue(false)), new ValueExp(new BoolValue(false))).eval(symTable));
    }

    @Test
    void nonboolOperandThrows() {
        assertThrows(MyException.class, () -> new LogicExp(LogicOp.AND, new ValueExp(new IntValue(5)), new ValueExp(new BoolValue(true))).eval(symTable));
        assertThrows(MyException.class, () -> new LogicExp(LogicOp.AND, new ValueExp(new BoolValue(true)), new ValueExp(new IntValue(5))).eval(symTable));
    }
}