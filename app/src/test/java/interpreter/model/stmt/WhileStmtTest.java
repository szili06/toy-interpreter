package interpreter.model.stmt;

import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhileStmtTest {

    PrgState state;

    @BeforeEach
    void setupEach() {
        state = PrgState.of(new NopStmt());
        state.setExeStack(new MyStack<>());
    }

    @Test
    void whileOnFalseWorks() {
        WhileStmt stmt = new WhileStmt(new ValueExp(new BoolValue(false)), new VarDeclStmt("a", new IntType()));
        assertTrue(state.getExeStack().isEmpty());
        assertDoesNotThrow(() -> stmt.execute(state));
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void whileOnTrueWorks() {
        WhileStmt stmt = new WhileStmt(new ValueExp(new BoolValue(true)), new VarDeclStmt("a", new IntType()));
        assertTrue(state.getExeStack().isEmpty());
        assertDoesNotThrow(() -> stmt.execute(state));
        assertFalse(state.getExeStack().isEmpty());
    }
}