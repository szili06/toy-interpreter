package interpreter.model.value;

import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import org.junit.jupiter.api.Test;

import java.sql.Ref;

import static org.junit.jupiter.api.Assertions.*;

class RefValueTest {

    @Test
    void nestedRefEqualsTest() {
        RefValue ref1 = new RefValue(0, new IntType());
        RefValue ref2 = new RefValue(0, ref1.getType());

        assertEquals(new RefValue(0, new IntType()), ref1);
        assertEquals(new RefValue(0, new RefType(new IntType())), ref2);
        assertNotEquals(ref1, ref2);
    }

}