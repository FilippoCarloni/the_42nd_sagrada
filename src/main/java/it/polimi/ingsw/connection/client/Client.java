package it.polimi.ingsw.connection.client;

import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.view.gui.Gui;

/**
 * The Client class is the main class of the client application.
 * It permits to create a CLI or a GUI interface with the parameter choice.
 */
public class Client {
    private static final String GUI_PARAMETERS = "-gui";
    private static final String CLI_PARAMETERS = "-cli";
    private static final String ERROR_USAGE = "Not a valid graphic choice";

    /**
     * Permits to run the CLI or GUI from command line.
     * With args[0] it is possible to choose if run a CLI or GUI.
     * -cli to run the CLI -gui to run GUI.
     * @param args - the command line parameters args[0] contains the graphic choice.
     */
    public static void main(String []args) {
        if(args.length == 1 ) {
            switch (args[0]) {
                case CLI_PARAMETERS:
                    new CLI();
                    break;
                case GUI_PARAMETERS:
                    Gui.main(args);
                    break;
                default:
                    System.out.println(ERROR_USAGE);
                    break;
            }
        }else
            System.out.println(ERROR_USAGE);
    }
}