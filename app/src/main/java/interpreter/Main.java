package interpreter;

import interpreter.view.View;

public class Main {
    public static void main(String[] args) {
        var view = new View();
        while(true) {
            view.printMenu();
            int response = Integer.parseInt(IO.readln("Enter your choice: "));
            if(response == 0)
                break;
            view.handleInput(response);
        }
    }
}