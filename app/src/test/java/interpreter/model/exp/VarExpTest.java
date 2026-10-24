package interpreter.model.exp;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarExpTest {
    private MyDictionary<String, IValue> symTable;

    @BeforeEach
    void setupEach() throws MyException {
        symTable = new MyDictionary<>();
        symTable.put("a", new IntValue(2));
        symTable.put("b", new IntValue(-2));
        symTable.put("OK", new BoolValue(true));
    }

    @Test
    void testLookupWorks() throws ADTException {
        assertEquals(new IntValue(2), new VarExp("a").eval(symTable));
        assertEquals(new IntValue(-2), new VarExp("b").eval(symTable));
        assertEquals(new BoolValue(true), new VarExp("OK").eval(symTable));
    }

    @Test
    void undefinedVariableThrows() {
        assertThrows(ExpressionException.class, () -> new VarExp("c").eval(symTable));
    }

}