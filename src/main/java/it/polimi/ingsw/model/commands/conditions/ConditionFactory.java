package it.polimi.ingsw.model.commands.conditions;

import it.polimi.ingsw.model.commands.conditions.indexconditions.IndexGreaterThan;
import it.polimi.ingsw.model.commands.conditions.indexconditions.IndexSmallerThan;
import it.polimi.ingsw.model.commands.conditions.toolconditions.FavorPointsCheck;
import it.polimi.ingsw.model.commands.conditions.toolconditions.ToolNotActive;
import it.polimi.ingsw.model.commands.conditions.turnstateconditions.DieNotPicked;
import it.polimi.ingsw.model.commands.conditions.turnstateconditions.DieNotPlaced;
import it.polimi.ingsw.model.commands.conditions.turnstateconditions.DiePicked;
import it.polimi.ingsw.model.commands.conditions.turnstateconditions.ToolNotActivated;
import it.polimi.ingsw.model.commands.conditions.windowframeconditions.FreeSlot;
import it.polimi.ingsw.model.commands.conditions.windowframeconditions.PlacingRuleCheck;
import it.polimi.ingsw.model.commands.conditions.windowframeconditions.ValidCoordinates;
import it.polimi.ingsw.model.gamedata.GameData;

public final class ConditionFactory {

    private ConditionFactory() {}

    public static Condition getIndexGreaterThan(GameData gameData, int[] args, int index, int bound) {
        return new IndexGreaterThan(gameData, args, index, bound);
    }

    public static Condition getIndexSmallerThan(GameData gameData, int[] args, int index, int bound) {
        return new IndexSmallerThan(gameData, args, index, bound);
    }

    public static Condition getFavorPointsCheck(GameData gameData, int[] args) {
        return new FavorPointsCheck(gameData, args);
    }

    public static Condition getToolNotActive(GameData gameData) {
        return new ToolNotActive(gameData);
    }

    public static Condition getDieNotPicked(GameData gameData) {
        return new DieNotPicked(gameData);
    }

    public static Condition getDieNotPlaced(GameData gameData) {
        return new DieNotPlaced(gameData);
    }

    public static Condition getDiePicked(GameData gameData) {
        return new DiePicked(gameData);
    }

    public static Condition getToolNotActivated(GameData gameData) {
        return new ToolNotActivated(gameData);
    }

    public static Condition getFreeSlot(GameData gameData, int[] args) {
        return new FreeSlot(gameData, args);
    }

    public static Condition getPlacingRuleCheck(GameData gameData, int[] args) {
        return new PlacingRuleCheck(gameData, args);
    }

    public static Condition getValidCoordinates(GameData gameData, int[] args) {
        return new ValidCoordinates(gameData, args);
    }
}
