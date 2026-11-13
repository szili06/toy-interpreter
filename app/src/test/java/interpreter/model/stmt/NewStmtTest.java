package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewStmtTest {

    private IStmt stmt;
    private PrgState state;

    @BeforeEach
    void setupEach() {
        stmt = new NewStmt("a", new ValueExp(new IntValue(2)));
        state = PrgState.of(stmt);
    }

    @Test
    void executeWorks() throws MyException {
        assertDoesNotThrow(() -> (new VarDeclStmt("a", new RefType(new IntType())).execute(state)));
        assertDoesNotThrow(() -> stmt.execute(state));
        assertEquals(new IntValue(2), state.getHeap().get(1));
        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("a"));
    }

    @Test
    void notRefTypeThrows() {
        assertDoesNotThrow(() -> (new VarDeclStmt("a", new IntType()).execute(state)));
        assertThrows(StatementException.class,() -> stmt.execute(state));
    }

    @Test
    void undeclaredVarThrows() {
        assertThrows(StatementException.class,() -> stmt.execute(state));
    }

    @Test
    void wrongTypeThrows() {
        assertDoesNotThrow(() -> (new VarDeclStmt("a", new BoolType()).execute(state)));
        assertThrows(StatementException.class,() -> stmt.execute(state));
    }
}