package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ReadFileStmtTest {
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
        new VarDeclStmt("x", new IntType()).execute(state);
    }
    @Test
    void readGivesTheNextNumberThenZeroAtTheEnd() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(7), state.getSymTable().lookup("x"));
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void variableNotDeclaredThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "b").execute(state));
    }

    @Test
    void nonIntegerVariableThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new VarDeclStmt("str", new StringType()).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "str").execute(state));
    }

    @Test
    void fileNotOpenThrows() {
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "x").execute(state));
    }

    @Test
    void notIntegerLineThrows() throws Exception {
        Path f2 = dir.resolve("helloworld.in");
        Files.writeString(f2, "7\nHello World!\n");
        file = new ValueExp(new StringValue(f2.toString()));

        new OpenRFileStmt(file).execute(state);
        assertDoesNotThrow(() -> new ReadFileStmt(file, "x").execute(state));
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "x").execute(state));
    }

}