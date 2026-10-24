package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
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

class CompStmtTest {
    private MyStack<IStmt> exeStack;
    private PrgState state;

    @BeforeEach
    void setupEach() {
        exeStack = new MyStack<>();
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyList<IValue> out = new MyList<>();
        IStmt program = new AssignStmt("a", new ValueExp(new IntValue(3)));
        state = new PrgState(exeStack, symTable, out, program);
    }

    @Test
    void testS1() throws ADTException {
        var left = new AssignStmt("a", new ValueExp(new IntValue(3)));
        var right = new AssignStmt("a", new ValueExp(new IntValue(3)));
        new CompStmt(left, right).execute(state);
        assertEquals(right, exeStack.pop());
        assertEquals(left, exeStack.pop());
    }
}