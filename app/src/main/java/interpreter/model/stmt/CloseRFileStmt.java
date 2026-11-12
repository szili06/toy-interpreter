package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.StringValue;

import java.io.IOException;

public record CloseRFileStmt(IExp exp) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!(exp.eval(state.getSymTable()) instanceof StringValue fileName)) {
            throw new StatementException("closeRFile: " + exp + " is not a string");
        }
        if (!state.getFileTable().isDefined(fileName)) {
            throw new StatementException("closeRFile: file " + fileName + " is not open");
        }

        try {
            var file = state.getFileTable().lookup(fileName);
            file.close();
            state.getFileTable().remove(fileName);
        } catch (IOException e) {
            throw new StatementException("IO error when closing file!");
        }
        return state;
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp + ")";
    }
}
