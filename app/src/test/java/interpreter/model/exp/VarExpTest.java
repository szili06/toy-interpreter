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

class VarExpTest {
    private MyDictionary<String, IValue> symTable;
    private MyHeap heap;

    @BeforeEach
    void setupEach() throws MyException {
        symTable = new MyDictionary<>();
        symTable.put("a", new IntValue(2));
        symTable.put("b", new IntValue(-2));
        symTable.put("OK", new BoolValue(true));
        heap = new MyHeap();
    }

    @Test
    void testLookupWorks() throws MyException {
        assertEquals(new IntValue(2), new VarExp("a").eval(symTable, heap));
        assertEquals(new IntValue(-2), new VarExp("b").eval(symTable, heap));
        assertEquals(new BoolValue(true), new VarExp("OK").eval(symTable, heap));
    }

    @Test
    void undefinedVariableThrows() {
        assertThrows(ExpressionException.class, () -> new VarExp("c").eval(symTable, heap));
    }

}