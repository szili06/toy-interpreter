package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {
    private static IStack<Integer> stack;

    @BeforeAll
    static void setup() {
        stack = new MyStack<>();
    }

    @BeforeEach
    void setupEach() {
        while(!stack.isEmpty()) {
            try {
                stack.pop();
            } catch (MyException e){
                throw new RuntimeException("");
            }
        }
    }

    @Test
    void LIFOOrder() throws ADTException {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertFalse(() -> stack.isEmpty());
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void popOnEmptyStackThrows() {
        assertTrue(stack.isEmpty());
        assertThrows(ADTException.class, () -> stack.pop());
    }
}