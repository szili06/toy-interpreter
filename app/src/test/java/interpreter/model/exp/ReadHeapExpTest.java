package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadHeapExpTest {

    @Test
    void testExpWorks() throws MyException {
        PrgState prgState = PrgState.of(new NopStmt());
        int addr = prgState.getHeap().allocate(new IntValue(3));
        ReadHeapExp exp = new ReadHeapExp(new ValueExp(new RefValue(addr, new IntType())));
        assertEquals(new IntValue(3), exp.eval(prgState.getSymTable(), prgState.getHeap()));
    }

    @Test
    void testNonReferenceThrows() {
        PrgState prgState = PrgState.of(new NopStmt());
        int addr = prgState.getHeap().allocate(new IntValue(3));
        ReadHeapExp exp = new ReadHeapExp(new ValueExp(new IntValue(3)));
        assertThrows(ExpressionException.class, () -> exp.eval(prgState.getSymTable(), prgState.getHeap()));
    }

    @Test
    void testUndefinedReferenceThrows() {
        PrgState prgState = PrgState.of(new NopStmt());
        ReadHeapExp exp = new ReadHeapExp(new ValueExp(new RefValue(0, new IntType())));
        assertThrows(ExpressionException.class, () -> exp.eval(prgState.getSymTable(), prgState.getHeap()));
    }
}