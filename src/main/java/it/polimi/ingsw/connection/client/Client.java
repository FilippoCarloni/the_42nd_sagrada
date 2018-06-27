package it.polimi.ingsw.connection.client;

import it.polimi.ingsw.view.cli.CLI;

public class Client {
    public static void main(String []args) {
        if(args.length > 1 ) {
            if (args[1].equals("cli"))
                new CLI();
        }else
            new CLI();
    }
}