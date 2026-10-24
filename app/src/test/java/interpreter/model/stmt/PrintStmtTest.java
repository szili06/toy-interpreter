package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrintStmtTest {
    private PrgState state;

    @BeforeEach
    void setupEach() {
        MyStack<IStmt> exeStack = new MyStack<>();
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyList<IValue> out = new MyList<>();
        IStmt program = new AssignStmt("a", new ValueExp(new IntValue(3)));
        state = new PrgState(exeStack, symTable, out, program);
    }

    @Test
    void testS4() throws ADTException {
        var stmt = new PrintStmt(new ValueExp(new IntValue(2)));
        assertDoesNotThrow(() -> stmt.execute(state));
        assertEquals(new IntValue(2), state.getOut().get(0));
        var stmt2 = new PrintStmt(new ValueExp(new BoolValue(true)));
        assertDoesNotThrow(() -> stmt2.execute(state));
        assertEquals(new BoolValue(true), state.getOut().get(1));
    }

    @Test
    void execErrorThrows() {
        var stmt = new PrintStmt(new VarExp("a"));
        assertThrows(ExpressionException.class, () -> stmt.execute(state));
    }
}