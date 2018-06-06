package it.polimi.ingsw.model.gamedata;

import it.polimi.ingsw.model.gameboard.cards.publicobjectives.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolCard;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.roundtrack.RoundTrack;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.turns.TurnManager;
import it.polimi.ingsw.model.utility.JSONSerializable;

import java.util.List;
import java.util.Map;

public interface GameData extends JSONSerializable {
    void advance();
    Map<Player, Integer> getCurrentScore();
    RoundTrack getRoundTrack();
    DiceBag getDiceBag();
    List<Die> getDicePool();
    List<PublicObjectiveCard> getPublicObjectives();
    List<ToolCard> getTools();
    List<Player> getPlayers();
    TurnManager getTurnManager();
    Die getPickedDie();
    void setPickedDie(Die die);
    boolean isDiePlaced();
    void setDiePlaced(boolean diePlaced);
    int getActiveToolID();
    void setActiveToolID(int id);
    int getPassiveToolID();
    void setPassiveToolID(int id);
    boolean isToolActivated();
    void setToolActivated(boolean toolActivated);
    List<Die> getDiceMoved();
    boolean isUndoAvailable();
    void setUndoAvailable(boolean available);
}
