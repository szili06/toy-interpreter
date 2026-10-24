package interpreter.model;

import interpreter.model.adt.*;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private final IStmt originalProgram;
    public PrgState(IStack<IStmt> exeStack, IDictionary<String, IValue> symTable,
                    IList<IValue> out, IStmt program) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = program;
        exeStack.push(program);
    }

    public IStack<IStmt> getExeStack() {
        return this.exeStack;
    }

    public void setExeStack(IStack<IStmt> newExeStack) {
        this.exeStack = newExeStack;
    }

    public IDictionary<String, IValue> getSymTable() {
        return this.symTable;
    }

    public void setSymTable(IDictionary<String, IValue> newSymTable) {
        this.symTable = newSymTable;
    }

    public IList<IValue> getOut() {
        return this.out;
    }

    public void setOut(IList<IValue> newOut) {
        this.out = newOut;
    }

    public String toString() {
        return "ExeStack: " + this.exeStack + ", SymTable: " + this.symTable + ", Out: " + this.out;
    }

    public void fullReset() {
        exeStack = new MyStack<>();
        symTable = new MyDictionary<>();
        out = new MyList<>();
        exeStack.push(originalProgram);
    }
}
