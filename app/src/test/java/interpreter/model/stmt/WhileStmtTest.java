package interpreter.model.stmt;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.*;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class WhileStmtTest {

    PrgState state;
    @TempDir
    Path dir;

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

    @Test
    void testMultipleWorks() throws MyException {
        VarDeclStmt varDeclStmt = new VarDeclStmt("a", new IntType());
        WhileStmt stmt = new WhileStmt(
                new RelationalExp(
                        new VarExp("a"),
                        new ValueExp(new IntValue(3)),
                        RelOp.LT),
                new CompStmt(
                        new PrintStmt(new VarExp("a")),
                        new AssignStmt(
                                "a",
                                new ArithExp(
                                        ArithOp.ADD,
                                        new VarExp("a"),
                                        new ValueExp(new IntValue(1)))
                        )));
        CompStmt program = new CompStmt(varDeclStmt, stmt);
        Path f = dir.resolve("testLog");
        PrgState state = PrgState.of(program);
        Controller controller = new Controller(new Repository(state, f.toString()), false);
        controller.allStep();
        assertEquals(3, state.getOut().size());
        for (int i = 0; i < 3; i++)
            assertEquals(new IntValue(i), state.getOut().get(i));
    }
}