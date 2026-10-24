package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;

public class Controller {
    final IRepository repo;
    boolean displayFlag;
    public Controller(IRepository repo, boolean displayFlag) {
        this.repo = repo;
        this.displayFlag = displayFlag;
    }

    public PrgState oneStep(PrgState state) throws MyException {
        var stack = state.getExeStack();
        if (stack.isEmpty()) throw new StatementException("program state stack is empty");
        IStmt crtStmt = stack.pop();
        return crtStmt.execute(state);
    }

    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg();
        if(displayFlag) display(prg);
        while (!prg.getExeStack().isEmpty()) {
            oneStep(prg);
            if(displayFlag) display(prg);
        }
    }

    public void display(PrgState state) {
        IO.println(state.toString());
    }
}
