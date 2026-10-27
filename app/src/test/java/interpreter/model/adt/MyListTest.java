package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    private IList<Integer> list;

    @BeforeEach
    void setup() {
        list = new MyList<>();
    }

    @Test
    void testAddDoesntThrow() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(2);
    }

    @Test
    void getThrowsOutOfRange() {
        list.add(1);
        assertThrows(ADTException.class, () -> list.get(2));
    }

    @Test
    void getWorks() throws MyException {
        list.add(2);
        list.add(1);
        assertEquals(2, list.get(0));
        assertEquals(1, list.get(1));
    }

    @Test
    void sizeWorks() {
        assertEquals(0, list.size());
        list.add(1);
        assertEquals(1, list.size());
        list.add(1);
        assertEquals(2, list.size());
        list.add(1);
        assertEquals(3, list.size());
    }

    @Test
    void negativeIndexThrows() {
        assertThrows(ADTException.class, () -> list.get(-1));
    }

    @Test
    void getOnEmptyListThrows() {
        assertThrows(ADTException.class, () -> list.get(0));
    }
}