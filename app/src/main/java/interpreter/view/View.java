package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;

public class View {
    Controller controller;

    public void printMenu() {
        IO.println("1. Select program");
        IO.println("2. Print selected program");
        IO.println("0. Exit");
    }

    public void handleInput(int input) {
        switch (input) {
            case 1:
                int response = Integer.parseInt(IO.readln("Which example program do you want to select: "));
                var repo = getRepo(response);
                controller = new Controller(repo, true);
                break;
            case 2:
                try {
                    controller.allStep();
                } catch (MyException e) {
                    System.err.println(e);
                }
                break;
        }
    }

    private static Repository getRepo(int response) {
        IStmt program = switch (response) {
            case 1 -> Examples.ex1;
            case 2 -> Examples.ex2;
            case 3 -> Examples.ex3;
            default -> null;
        };
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(),
                new MyList<>(), program);
        var repo = new Repository();
        repo.addPrg(state);
        return repo;
    }
}
