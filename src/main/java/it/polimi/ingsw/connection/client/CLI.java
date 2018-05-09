package it.polimi.ingsw.connection.client;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.logging.LogManager;

public class CLI extends Observable implements Runnable {

    private Scanner scanner;

    CLI(Observer o) {
        addObserver(o);
        scanner = new Scanner(System.in);
        menu();
    }

    private void menu() {
        System.out.println("\nUSAGE:\n" +
                "  ?     : prints how to play\n" +
                "  view  : prints game info\n" +
                "  exit  : disconnects from the current game\n");
    }

    void update(String o) {
        System.out.println(o);
    }

    @Override
    public void run() {
        String message;
        System.out.print("> ");
        message = scanner.nextLine();
        while (message != null) {
            setChanged();
            notifyObservers(message);
            System.out.print("> ");
            message = scanner.nextLine();
        }
    }
}
