package interpreter.controller;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static interpreter.view.Examples.*;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @TempDir
    Path dir;

    @Test
    void TestProgOne() throws ADTException {
        var state = PrgState.of(ex1);
        var repo = new Repository(state, "log1.txt");
        var controller = new Controller(repo, false);
        assertDoesNotThrow(controller::allStep);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2),state.getOut().get(0));
    }

    @Test
    void TestProgTwo() throws ADTException {
        var state = PrgState.of(ex2);
        var repo = new Repository(state, "log2.txt");
        var controller = new Controller(repo, false);
        assertDoesNotThrow(controller::allStep);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(18),state.getOut().get(0));
    }

    @Test
    void TestProgThree() throws ADTException {
        var state = PrgState.of(ex3);
        var repo = new Repository(state, "log3.txt");
        var controller = new Controller(repo, false);
        assertDoesNotThrow(controller::allStep);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2),state.getOut().get(0));
    }

    @Test
    void TestStepOneOnEmptyThrows() {
        var state = PrgState.of(new PrintStmt(new ValueExp(new IntValue(1))));
        var repo = new Repository(state, "log0.txt");
        var controller = new Controller(repo, false);
        var emptyState = assertDoesNotThrow(() -> controller.oneStep(state));
        assertThrows(StatementException.class, () -> controller.oneStep(emptyState));
    }

    @Test
    void logFileContainsTheFourSectionsInOrder() throws IOException {
        var state = PrgState.of(new PrintStmt(new ValueExp(new IntValue(2))));
        var repo = new Repository(state, dir.resolve("log.txt").toString());
        var controller = new Controller(repo, false);
        assertDoesNotThrow(controller::allStep);
        String log = Files.readString(dir.resolve("log.txt"));
        int a = log.indexOf("ExeStack:"), b = log.indexOf("SymTable:"),
                c = log.indexOf("Out:"),
                d = log.indexOf("FileTable:");
        assertTrue(0 <= a && a < b && b < c && c < d);
    }
}