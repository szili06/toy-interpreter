package interpreter.model.value;

import interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValueTest {

    @Test
    void testGetType() {
        assertEquals(new StringType(), new StringValue("").getType());
    }

    @Test
    void testToString() {
        assertEquals("abcdefg", new StringValue("abcdefg").toString());
    }

    @Test
    void testEquals() {
        assertEquals(new StringValue("abcdefg"), new StringValue("abcdefg"));
        assertNotEquals(new StringValue("abcdefg"), new StringValue("hijklmn"));
    }

    @Test
    void testHashCode() {
        assertEquals("abcd".hashCode(), new StringValue("abcd").hashCode());
        assertEquals(new StringValue("abc").hashCode(), new StringValue("abc").hashCode());
    }
}