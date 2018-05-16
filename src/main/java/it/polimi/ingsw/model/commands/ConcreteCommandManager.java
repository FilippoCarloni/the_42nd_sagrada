package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.gameboard.cards.ToolCard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConcreteCommandManager implements CommandManager {

    private ConcreteGameStatus status;

    public ConcreteCommandManager(ConcreteGameStatus status) {
        this.status = status;
    }

    private List<Command> allocateCommands(String cmd) {
        List<Command> commands = new ArrayList<>();
        commands.add(new Pick(status, cmd));
        commands.add(new Place(status, cmd));
        commands.add(new Pass(status, cmd));
        commands.add(new Tool(status, cmd));
        for (ToolCard t : status.getTools())
            commands.addAll(t.getCommands(cmd));
        return commands;
    }

    private List<Command> commandsFromString(String cmd) {
        List<Command> commands =  allocateCommands(cmd).stream().filter(Command::isValid).collect(Collectors.toList());
        return commands.isEmpty() ? new ArrayList<>() : commands;
    }

    private Command getLegalCommand(String cmd) {
        List<Command> commands = commandsFromString(cmd);
        for (Command c : commands)
            if (c.isLegal())
                return c;
        return null;
    }

    @Override
    public boolean isValid(String cmd) {
        return !commandsFromString(cmd).isEmpty();
    }

    @Override
    public boolean isLegal(String cmd) {
        return getLegalCommand(cmd) != null;
    }

    @Override
    public void execute(String cmd) {
        Command c = getLegalCommand(cmd);
        if (c != null) c.execute();
    }
}
