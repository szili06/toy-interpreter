package interpreter.model.type;

import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefTypeTest {

    @Test
    void defaultValueTest() {
        IntType type = new IntType();
        assertEquals(new RefValue(0, type), new RefType(type).defaultValue());
    }
}