package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarDeclStmtTest {
    private MyDictionary<String, IValue> symTable;
    private PrgState state;

    @BeforeEach
    void setupEach() {
        MyStack<IStmt> exeStack = new MyStack<>();
        symTable = new MyDictionary<>();
        MyList<IValue> out = new MyList<>();
        IStmt program = new AssignStmt("a", new ValueExp(new IntValue(3)));
        state = new PrgState(exeStack, symTable, out, program);
    }

    @Test
    void testS3() throws MyException {
        var stmt = new VarDeclStmt("a", new IntType());
        assertDoesNotThrow(() -> stmt.execute(state));
        assertTrue(symTable.isDefined("a"));
        assertEquals(new IntType().defaultValue(), symTable.lookup("a"));
    }

    @Test
    void redeclaredVariableThrows() {
        var stmt = new VarDeclStmt("a", new IntType());
        assertDoesNotThrow(() -> stmt.execute(state));
        var stmt2 = new VarDeclStmt("a", new BoolType());
        assertThrows(StatementException.class, () -> stmt2.execute(state));
    }
}