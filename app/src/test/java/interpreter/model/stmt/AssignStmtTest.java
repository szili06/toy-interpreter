package interpreter.model.stmt;


import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignStmtTest {
    private PrgState state;

    @BeforeEach
    void setupEach() {
        IStmt program = new AssignStmt("a", new ValueExp(new IntValue(3)));
        state = PrgState.of(program);
    }

    @Test
    void testS2() throws MyException {
        var stmt = new AssignStmt("a", new ValueExp(new IntValue(3)));
        state.getSymTable().put("a", new IntType().defaultValue());
        stmt.execute(state);
        assertTrue(state.getSymTable().isDefined("a"));
        assertEquals(new IntValue(3), state.getSymTable().lookup("a"));
    }

    @Test
    void testUnassignedVariableThrows() {
        var stmt = new AssignStmt("a", new ValueExp(new IntValue(3)));
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }

    @Test
    void wrongValueTypeThrows() throws MyException {
        var stmt = new AssignStmt("a", new ValueExp(new BoolValue(false)));
        state.getSymTable().put("a", new IntType().defaultValue());
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }
}