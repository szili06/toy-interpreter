package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        try {
            IValue fileName = exp.eval(state.getSymTable());
            if (!(fileName instanceof StringValue)) {
                throw new StatementException("filename not a string!");
            }
            if (!state.getFileTable().isDefined((StringValue) fileName)) {
              throw new StatementException("file " + fileName + " not opened!");
            }

            try {
                if (!(state.getSymTable().lookup(varName) instanceof IntValue)) {
                    throw new StatementException("variable not of type int!");
                }
            } catch (MyException e) {
                throw new StatementException("variable not defined!");
            }

            BufferedReader reader = state.getFileTable().lookup((StringValue) fileName);

            String line = reader.readLine();

            if(line == null) {
                state.getSymTable().update(varName, new IntValue(0));
                return state;
            }

            IntValue val = new IntValue(Integer.parseInt(line.strip()));

            state.getSymTable().update(varName, val);
        } catch (MyException e) {
            throw new StatementException("error evaluating filename!");
        } catch (IOException e) {
            throw new StatementException("reading from file threw " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new StatementException("reading from file didn't return integer");
        }

        return state;
    }

    @Override
    public String toString() {
        return "readfile(" + exp + ", " + varName + ")";
    }
}
