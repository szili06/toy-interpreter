package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.StringValue;

import java.io.IOException;

public record CloseRFileStmt(IExp exp) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        StringValue fileName;
        try {
            var temp = exp.eval(state.getSymTable());
            if(!(temp instanceof StringValue)) {
                throw new StatementException("filename not a string");
            }
            fileName = (StringValue) temp;
        } catch (MyException e) {
            throw new StatementException("Error when evaluating filename!");
        }
        if(!state.getFileTable().isDefined(fileName)) {
            throw new StatementException("File " + fileName + "not opened!");
        }

        try {
            var file = state.getFileTable().lookup(fileName);
            file.close();
            state.getFileTable().remove(fileName);
        } catch (MyException e) {
            throw new StatementException("Error on closing file!");
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
