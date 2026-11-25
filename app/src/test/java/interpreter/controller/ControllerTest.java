package interpreter.controller;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Ref;
import java.util.List;
import java.util.stream.Stream;

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

    static Stream<Arguments> programs() {
        return Stream.of(
                Arguments.of(EX1, List.of(new IntValue(2))),
                Arguments.of(EX2, List.of(new IntValue(18))),
                Arguments.of(EX3, List.of(new IntValue(2))),
                Arguments.of(EX5, List.of(new IntValue(5))),
                Arguments.of(EX6, List.of(new RefValue(1, new IntType()), new RefValue(2, new RefType(new IntType())))), Arguments.of(EX7, List.of(new IntValue(20), new IntValue(25))),
                Arguments.of(EX8, List.of(new IntValue(20), new IntValue(35))),
                Arguments.of(EX9, List.of(new IntValue(20))),
                Arguments.of(EX10, List.of(new IntValue(4), new IntValue(3), new IntValue(2), new IntValue(1), new IntValue(0)))
                );
    }

    @ParameterizedTest(name = "program {index}")
    @MethodSource("programs")
    void testProg(IStmt program, List<IValue> expected) throws ADTException {
        var state = PrgState.of(program);
        var repo  = new Repository(state, dir.resolve("log.txt").toString());
        assertDoesNotThrow(() -> new Controller(repo, false).allStep());
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(expected.size(), state.getOut().size());
        assertEquals(expected, state.getOut().toList());
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