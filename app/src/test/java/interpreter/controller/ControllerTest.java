package interpreter.controller;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;

import static interpreter.view.Examples.*;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @Test
    void TestProgOne() throws ADTException {
        var state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex1);
        var repo = new Repository();
        repo.addPrg(state);
        var controller = new Controller(repo, false);
        assertDoesNotThrow(controller::allStep);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2),state.getOut().get(0));
    }

    @Test
    void TestProgTwo() throws ADTException {
        var state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex2);
        var repo = new Repository();
        repo.addPrg(state);
        var controller = new Controller(repo, false);
        assertDoesNotThrow(controller::allStep);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(18),state.getOut().get(0));
    }

    @Test
    void TestProgThree() throws ADTException {
        var state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex3);
        var repo = new Repository();
        repo.addPrg(state);
        var controller = new Controller(repo, false);
        assertDoesNotThrow(controller::allStep);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2),state.getOut().get(0));
    }

    @Test
    void TestStepOneOnEmptyThrows() {
        var state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(),
                new PrintStmt(new ValueExp(new IntValue(1))));
        var repo = new Repository();
        repo.addPrg(state);
        var controller = new Controller(repo, false);
        var emptyState = assertDoesNotThrow(() -> controller.oneStep(state));
        assertThrows(StatementException.class, () -> controller.oneStep(emptyState));
    }
}