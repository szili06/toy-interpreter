package interpreter.model.exp;

import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueExpTest {
    @Test
    void returnsValue() {
        var exp = new ValueExp(new IntValue(5));
        assertEquals(new IntValue(5), exp.eval(null));
    }
}