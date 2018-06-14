package it.polimi.ingsw.view.cli;

/**
 * Holds the string constants used by the CLI.
 */
class CLIMessage {

    private CLIMessage() {}

    static final String CONNECTION_TYPE_OPTIONS = "[1] RMI\n[2] Socket";
    static final String CONNECTION_TYPE_FIRST_OPTION = "1";
    static final String USAGE = "USAGE:\n" +
            "  ?         : prints how to play\n" +
            "  view      : prints game info\n" +
            "  play      : start a game\n" +
            "  window \\d : select starting window frame\n"+
            "  exit      : disconnects from the current game";
    static final String TITLE = "" +
            "  ____                                       _         \n" +
            " / ___|    __ _    __ _   _ __    __ _    __| |   __ _ \n" +
            " \\___ \\   / _` |  / _` | | '__|  / _` |  / _` |  / _` |\n" +
            "  ___) | | (_| | | (_| | | |    | (_| | | (_| | | (_| |\n" +
            " |____/   \\__,_|  \\__, | |_|     \\__,_|  \\__,_|  \\__,_|\n" +
            "                  |___/                                \n" +
            "                  ____\n" +
            "                 /\\' .\\    _____\n" +
            "                /: \\___\\  / .  /\\\n" +
            "                \\' / . / /____/..\\\n" +
            "                 \\/___/  \\'  '\\  /\n" +
            "                          \\'__'\\/\n";
    static final String NOT_EXISTENT_PARAMETER = "There's no matching object for the passed parameter.";
    static final String UNSUPPORTED_MESSAGE = "Message not supported!";
    static final String IMAGE_NOT_FOUND = "No image was found.";
}
