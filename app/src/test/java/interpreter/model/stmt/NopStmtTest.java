package interpreter.model.stmt;

import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NopStmtTest {
    private PrgState state;

    @BeforeEach
    void setupEach() {
        IStmt program = new AssignStmt("a", new ValueExp(new IntValue(3)));
        state = PrgState.of(program);
    }
    @Test
    void testS6() {
        var stmt = new NopStmt();
        assertDoesNotThrow(() -> stmt.execute(state));
    }
}