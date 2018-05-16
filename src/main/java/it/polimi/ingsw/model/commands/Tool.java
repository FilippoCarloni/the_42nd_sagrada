package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.gameboard.cards.ToolCard;

import static java.lang.Integer.parseInt;

public class Tool extends AbstractCommand {

    Tool(ConcreteGameStatus status, String cmd) {
        super(status, cmd);
        setRegExp("tool \\d");
        setLegalPredicate(s ->
                parseInt(s[1]) <= status.getTools().size() &&
                        parseInt(s[1]) >= 1 &&
                        status.getTools().get(parseInt(s[1]) - 1).isLegal());
    }

    @Override
    public void execute() {
        ToolCard toolCard = getStatus().getTools().get(parseInt(getCmd().split(" ")[1]) - 1);
        if (toolCard.isLegal())
            toolCard.execute();
    }
}
