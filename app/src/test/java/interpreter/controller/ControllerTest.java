package interpreter.controller;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
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

    private PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        var repo = new Repository(state, dir.resolve("log.txt").toString());
        new Controller(repo, false).allStep();
        return state;
    }

    @Test
    void TestProgOne() throws ADTException {
        var state = PrgState.of(EX1);
        var repo = new Repository(state, dir.resolve("log1.txt").toString());
        var controller = new Controller(repo, false);
        assertDoesNotThrow(controller::allStep);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2),state.getOut().get(0));
    }

    @Test
    void TestProgTwo() throws ADTException {
        var state = PrgState.of(EX2);
        var repo = new Repository(state, dir.resolve("log2.txt").toString());
        var controller = new Controller(repo, false);
        assertDoesNotThrow(controller::allStep);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(18),state.getOut().get(0));
    }

    @Test
    void TestProgThree() throws ADTException {
        var state = PrgState.of(EX3);
        var repo = new Repository(state, dir.resolve("log3.txt").toString());
        var controller = new Controller(repo, false);
        assertDoesNotThrow(controller::allStep);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2),state.getOut().get(0));
    }

    @Test
    void TestProgFour() throws MyException, IOException {
        Path in = dir.resolve("test.in");
        Files.writeString(in, "15\n50\n");
        PrgState s = run(Examples.fileExample(in.toString()));
        assertEquals(new IntValue(15), s.getOut().get(0));
        assertEquals(new IntValue(50), s.getOut().get(1));
        assertTrue(s.getFileTable().toMap().isEmpty());
    }

    @Test
    void TestProgFourOnEmpty() throws MyException, IOException {
        Path in = dir.resolve("test.in");
        Files.writeString(in, "");
        PrgState s = run(Examples.fileExample(in.toString()));
        assertEquals(new IntValue(0), s.getOut().get(0));
        assertEquals(new IntValue(0), s.getOut().get(1));
        assertTrue(s.getFileTable().toMap().isEmpty());
    }

    @Test
    void TestProgFive() throws ADTException {
        var state = PrgState.of(EX5);
        var repo = new Repository(state, dir.resolve("log5.txt").toString());
        var controller = new Controller(repo, false);
        assertDoesNotThrow(controller::allStep);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(5),state.getOut().get(0));
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