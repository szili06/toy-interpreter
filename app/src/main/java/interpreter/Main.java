import interpreter.view.View;

void main() {
    var view = new View();
    while(true) {
        view.printMenu();
        int response = Integer.parseInt(IO.readln("Enter your choice: "));
        if(response == 0)
            break;
        view.handleInput(response);
    }
}
