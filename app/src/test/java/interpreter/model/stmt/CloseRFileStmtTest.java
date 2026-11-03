package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CloseRFileStmtTest {
    @TempDir
    Path dir;
    private PrgState state;
    private ValueExp file;

    @BeforeEach
    void setUp() throws Exception {
        Path f = dir.resolve("numbers.in");
        Files.writeString(f, "7\n");
        file = new ValueExp(new StringValue(f.toString()));
        state = PrgState.of(new NopStmt());
        new OpenRFileStmt(file).execute(state);
    }

    @Test
    void testWorks() {
        assertDoesNotThrow(() -> new CloseRFileStmt(file).execute(state));
        assertFalse(state.getFileTable().isDefined((StringValue) file.eval(state.getSymTable())));
    }

    @Test
    void assertNotStringFilenameThrows() {
        assertThrows(StatementException.class, () -> new CloseRFileStmt(new ValueExp(new IntValue(2))).execute(state));
    }

    @Test
    void assertFileNotInFileTableThrows() {
        assertThrows(StatementException.class, () -> new CloseRFileStmt(new ValueExp(new StringValue("nonexistingfile.txt"))).execute(state));
    }
}