package it.polimi.ingsw.connection.client;

import it.polimi.ingsw.model.gamedata.ConcreteGameData;
import it.polimi.ingsw.model.utility.JSONFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

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
                "  play  : start a game\n" +
                "  exit  : disconnects from the current game\n");
    }

    void update(String o) {
        if(o.contains("{"))
            try {
                o = JSONFactory.getGameData((JSONObject) new JSONParser().parse(o)).toString();
            }catch (ParseException e){
                o=e.getMessage();
            }
        System.out.println(o);
        System.out.print("> ");
    }

    @Override
    public void run() {
        String message;
        System.out.print("> ");
        message = scanner.nextLine();
        while (message != null) {
            setChanged();
            notifyObservers(message);
            message = scanner.nextLine();
        }
    }
}
