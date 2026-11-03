package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class OpenRFileStmtTest {

    @Test
    void testWorks() {
        IStmt program = new OpenRFileStmt(new ValueExp(new StringValue("build.gradle.kts")));
        var state = PrgState.of(program);
        assertDoesNotThrow(() -> program.execute(state));
        assertTrue(state.getFileTable().isDefined(new StringValue("build.gradle.kts")));
    }

    @Test
    void testNonStringThrows() {
        IStmt program = new OpenRFileStmt(new ValueExp(new BoolValue(true)));
        var state = PrgState.of(program);
        assertThrows(StatementException.class, () -> program.execute(state));
    }

    @Test
    void testReopenedFileThrows() {
        IStmt program = new OpenRFileStmt(new ValueExp(new StringValue("build.gradle.kts")));
        var state = PrgState.of(program);
        assertDoesNotThrow(() -> program.execute(state));
        IStmt program2 = new OpenRFileStmt(new ValueExp(new StringValue("build.grade.kts")));
        var state2 = PrgState.of(program);
        assertThrows(StatementException.class, () -> program2.execute(state));
    }

    @Test
    void testMissingFileThrows() {
        IStmt program = new OpenRFileStmt(new ValueExp(new StringValue("nonexisitingfile.txt")));
        var state = PrgState.of(program);
        assertThrows(StatementException.class, ()-> program.execute(state));
    }
}