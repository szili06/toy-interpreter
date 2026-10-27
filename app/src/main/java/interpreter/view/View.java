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
                try {
                    int response = Integer.parseInt(IO.readln("Which example program do you want to select: "));
                    var repo = getRepo(response);
                    if (repo == null) {
                        break;
                    }
                    controller = new Controller(repo, true);
                } catch (NumberFormatException e) {
                    IO.println("Invalid input!");
                }
                break;
            case 2:
                if(controller == null) {
                    IO.println("No program selected.");
                    break;
                }
                try {
                    try {
                        controller.resetProg();
                    } catch (MyException e) {
                        IO.println("Error on resetting program to original state: " + e);
                        break;
                    }
                    controller.allStep();
                } catch (MyException e) {
                    IO.println("Error on running program: " + e);
                    break;
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
        if (program == null) {
            return null;
        }
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(),
                new MyList<>(), program);
        var repo = new Repository();
        repo.addPrg(state);
        return repo;
    }

    public void run() {
        while(true) {
            printMenu();
            try {
                int response = Integer.parseInt(IO.readln("Enter your choice: "));
                if(response == 0)
                    break;
                handleInput(response);
            } catch (NumberFormatException e) {
                IO.println("Invalid input!");
            }
        }
    }
}
