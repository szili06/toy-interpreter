package interpreter.model.stmt;

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

class IfStmtTest {
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
    void testConditionTrueThen() {
        var cond = new ValueExp(new BoolValue(true));
        var stmtThen = new VarDeclStmt("a", new IntType());
        var stmtElse = new VarDeclStmt("b", new IntType());
        var stmt = new IfStmt(cond, stmtThen, stmtElse);
        assertDoesNotThrow(() -> stmt.execute(state));
        assertDoesNotThrow(() -> state.getExeStack().pop().execute(state));
        assertTrue(symTable.isDefined("a"));
        assertFalse(symTable.isDefined("b"));
    }

    @Test
    void testConditionFalseElse() {
        var cond = new ValueExp(new BoolValue(false));
        var stmtThen = new VarDeclStmt("a", new IntType());
        var stmtElse = new VarDeclStmt("b", new IntType());
        var stmt = new IfStmt(cond, stmtThen, stmtElse);
        assertDoesNotThrow(() -> stmt.execute(state));
        assertDoesNotThrow(() -> state.getExeStack().pop().execute(state));
        assertFalse(symTable.isDefined("a"));
        assertTrue(symTable.isDefined("b"));
    }

    @Test
    void testConditionNotBoolThrows() {
        var cond = new ValueExp(new IntValue(3));
        var stmtThen = new VarDeclStmt("a", new IntType());
        var stmtElse = new VarDeclStmt("b", new IntType());
        var stmt = new IfStmt(cond, stmtThen, stmtElse);
        assertThrows(StatementException.class,() -> stmt.execute(state));
    }
}