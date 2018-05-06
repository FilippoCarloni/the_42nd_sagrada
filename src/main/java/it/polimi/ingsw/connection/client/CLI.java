package it.polimi.ingsw.connection.client;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class CLI extends Observable implements Runnable {

    private String message;
    Scanner scanner;

    CLI(Observer o) {
        addObserver(o);
        scanner = new Scanner(System.in);;
        menu();
    }

    private void menu() {
        System.out.println("The game is started. Here you will see the game commands.");

    }

    public void update(String o) {
        System.out.println(o);
    }

    @Override
    public void run() {
        message = scanner.nextLine();
        while (message != null) {
            setChanged();
            notifyObservers(message);
            message = scanner.nextLine();
        }
    }
}
