package it.polimi.ingsw.MVCdemo;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class CLI extends Observable implements Runnable {

    private String ID;
    private String message;
    Scanner s;
    CLI(Observer o) {
        addObserver(o);
        s=new Scanner(System.in);
        System.out.println("Welcome, please insert your name");
        ID=s.nextLine();
        menu();
    }

    private void menu() {
        System.out.println("you can use these three commands:");
        System.out.println("1 set <value_without_space>");
        System.out.println("2 view");
        System.out.println("3 extt ");

    }
    public void update(String o) {
        System.out.println(o);
    }

    @Override
    public void run() {
        message = s.nextLine();
        while (message != null) {
            setChanged();
            notifyObservers(message);
            message = s.nextLine();
        }
    }
}