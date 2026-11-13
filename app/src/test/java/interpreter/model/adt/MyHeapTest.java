package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyHeapTest {
    public MyHeap heap;

    @BeforeEach
    void setupEach() {
        heap = new MyHeap();
    }

    @Test
    void allocateWorks() throws ADTException {
        int adr1 = heap.allocate(new IntValue(1));
        int adr2 = heap.allocate(new BoolValue(true));
        int adr3 = heap.allocate(new StringValue("yes"));
        assertEquals(new IntValue(1), heap.get(adr1));
        assertEquals(new BoolValue(true), heap.get(adr2));
        assertEquals(new StringValue("yes"), heap.get(adr3));
        assertEquals(1, adr1);
        assertEquals(2, adr2);
        assertEquals(3, adr3);
    }

    @Test
    void readingMissingAddressThrows() {
        assertThrows(ADTException.class, () -> heap.get(0));
    }
}