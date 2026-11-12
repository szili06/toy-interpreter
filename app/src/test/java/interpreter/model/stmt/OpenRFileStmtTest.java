package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class OpenRFileStmtTest {

    @TempDir
    private Path dir;
    private ValueExp file;

    @BeforeEach
    void setupEach() throws IOException {
        Path f = dir.resolve("testfile");
        Files.writeString(f, "123\n");
        file = new ValueExp(new StringValue(f.toString()));
    }

    @Test
    void testWorks() {
        IStmt program = new OpenRFileStmt(file);
        var state = PrgState.of(program);
        assertDoesNotThrow(() -> program.execute(state));
        assertTrue(state.getFileTable().isDefined((StringValue) file.val()));
    }

    @Test
    void testNonStringThrows() {
        IStmt program = new OpenRFileStmt(new ValueExp(new BoolValue(true)));
        var state = PrgState.of(program);
        assertThrows(StatementException.class, () -> program.execute(state));
    }

    @Test
    void testReopenedFileThrows() {
        IStmt program = new OpenRFileStmt(file);
        var state = PrgState.of(program);
        assertDoesNotThrow(() -> program.execute(state));
        IStmt program2 = new OpenRFileStmt(file);
        assertThrows(StatementException.class, () -> program2.execute(state));
    }

    @Test
    void testMissingFileThrows() {
        IStmt program = new OpenRFileStmt(new ValueExp(new StringValue("nonexisitingfile.txt")));
        var state = PrgState.of(program);
        assertThrows(StatementException.class, ()-> program.execute(state));
    }
}