package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {
    private IDictionary<String, IValue> dict;
    @BeforeEach
    void setupEach() {
        dict = new MyDictionary<>();
    }

    @Test
    void testPut() throws MyException {
        var val = new IntValue(1);
        assertDoesNotThrow(() -> dict.put("a", val));
        var val2 = new BoolValue(true);
        assertDoesNotThrow(() -> dict.put("ok", val2));
        assertEquals(val, dict.lookup("a"));
        assertEquals(val2, dict.lookup("ok"));
    }

    @Test
    void putExistingKeyThrows() {
        var val = new IntValue(1);
        assertDoesNotThrow(() -> dict.put("a", val));
        assertThrows(ADTException.class, () -> dict.put("a", val));
    }

    @Test
    void lookupNonexistingKeyThrows() {
        assertThrows(ADTException.class, () -> dict.lookup("a"));
    }

    @Test
    void testUpdate() throws MyException {
        var val1 = new IntValue(1);
        var val2 = new IntValue(2);
        dict.put("a", val1);
        assertDoesNotThrow(() -> dict.update("a", val2));
        assertEquals(val2, dict.lookup("a"));
    }

    @Test
    void undefinedKeyUpdateThrows() {
        assertThrows(ADTException.class, () -> dict.update("a", new BoolValue(false)));
    }

    @Test
    void testIsDefined() {
        assertDoesNotThrow(() -> dict.put("a", new IntValue(2)));
        assertDoesNotThrow(() -> dict.put("b", new BoolValue(true)));
        assertTrue(dict.isDefined("a"));
        assertTrue(dict.isDefined("b"));
        assertFalse(dict.isDefined("ok"));
    }
}