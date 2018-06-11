package it.polimi.ingsw.view.cli;

class CLIMessage {

    private CLIMessage() {}

    static final String CONNECTION_TYPE_OPTIONS = "[1] RMI\n[2] Socket";
    static final String CONNECTION_TYPE_FIRST_OPTION = "1";
    static final String USAGE = "\nUSAGE:\n" +
            "  ?         : prints how to play\n" +
            "  view      : prints game info\n" +
            "  play      : start a game\n" +
            "  window \\d : select starting window frame\n"+
            "  exit  : disconnects from the current game\n";
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
}
