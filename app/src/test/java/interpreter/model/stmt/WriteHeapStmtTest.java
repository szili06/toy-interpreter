package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.type.StringType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WriteHeapStmtTest {
    PrgState state;

    @BeforeEach
    void setupEach() {
        state = PrgState.of(new NopStmt());
    }

    @Test
    void writeHeapStmtWorks() throws MyException {
        VarDeclStmt varDeclStmt = new VarDeclStmt("a", new RefType(new IntType()));
        NewStmt newStmt = new NewStmt("a", new ValueExp(new IntValue(0)));
        WriteHeapStmt stmt = new WriteHeapStmt("a", new ValueExp(new IntValue(2)));
        assertDoesNotThrow(() -> varDeclStmt.execute(state));
        assertDoesNotThrow(() -> newStmt.execute(state));
        assertDoesNotThrow(() -> stmt.execute(state));
        assertEquals(new IntValue(2), state.getHeap().get(1));
    }

    @Test
    void writeHeapStmtUndefinedVariableThrows() {
        WriteHeapStmt stmt = new WriteHeapStmt("a", new ValueExp(new IntValue(2)));
        assertThrows(StatementException.class,() -> stmt.execute(state));
    }

    @Test
    void writeHeapStmtNotReferenceVarThrows() throws MyException {
        VarDeclStmt varDeclStmt = new VarDeclStmt("a", new IntType());
        WriteHeapStmt stmt = new WriteHeapStmt("a", new ValueExp(new IntValue(2)));
        assertDoesNotThrow(() -> varDeclStmt.execute(state));
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }

    @Test
    void writeHeapStmtNewValueWrongTypeThrows() throws MyException {
        VarDeclStmt varDeclStmt = new VarDeclStmt("a", new RefType(new StringType()));
        NewStmt newStmt = new NewStmt("a", new ValueExp(new StringValue("hii")));
        WriteHeapStmt stmt = new WriteHeapStmt("a", new ValueExp(new IntValue(2)));
        assertDoesNotThrow(() -> varDeclStmt.execute(state));
        assertDoesNotThrow(() -> newStmt.execute(state));
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }
}