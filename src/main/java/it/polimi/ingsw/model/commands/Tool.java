package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.ConcreteGameStatus;

import static java.lang.Integer.parseInt;

public class Tool extends AbstractCommand {

    Tool(ConcreteGameStatus status, String cmd) {
        super(status, cmd);
        regExp = "tool \\d";
        legalPredicate = s ->
                        parseInt(s[1]) <= status.getTools().size() &&
                        parseInt(s[1]) >= 1 &&
                        status.getTools().get(parseInt(s[1]) - 1).isLegal();
    }

    @Override
    public void execute() {
        status.getTools().get(parseInt(cmd.split(" ")[1]) - 1).execute();
    }
}
