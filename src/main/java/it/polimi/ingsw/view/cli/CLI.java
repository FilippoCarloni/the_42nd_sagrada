package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.gameboard.windowframes.Coordinate;
import it.polimi.ingsw.model.utility.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.Integer.parseInt;

public class CLI {

    public void draw(JSONObject obj) {
        JSONObject json;
        try {
            String path = "src/test/java/res/model/gen_2p_02.json";
            String content = new String(Files.readAllBytes(Paths.get(path)));
            json=(JSONObject) new JSONParser().parse(content);
            System.out.println(drawBoard(json));

        } catch (IOException e) {
            throw new IllegalArgumentException("Bad file name.");
        } catch (ParseException e) {
            throw new IllegalArgumentException("Bad JSON file.");
        }
    }

    private String drawDice(JSONArray jsonArray) {
        StringBuilder sb = new StringBuilder();
        for (Object o : jsonArray)
            sb.append(drawDie((JSONObject) o));
        return sb.toString();
    }

    private String drawDie(JSONObject jsonObject){
        CliShade shade=CliShade.findByValue(parseInt(jsonObject.get(JSONTag.SHADE).toString()));
        CliColor color=CliColor.findByLabel(jsonObject.get(JSONTag.COLOR).toString());
        if(shade!=null&&color!=null)
            return color.paint(shade.toString());
        throw new NullPointerException();
    }

    private String drawRoundTrack(JSONObject jsonObject){
        int totalNumberOfRounds=Parameters.TOTAL_NUMBER_OF_ROUNDS;
        JSONArray dice = (JSONArray) jsonObject.get(JSONTag.ALL_DICE);
        int currentRoundNumber = parseInt(jsonObject.get(JSONTag.CURRENT_ROUND_NUMBER).toString());
        int[] diceOnSlot = new int[totalNumberOfRounds];
        JSONArray numOfDiceOnSlot = (JSONArray) jsonObject.get(JSONTag.NUMBER_OF_DICE_ON_SLOT);
        for (int i = 0; i < numOfDiceOnSlot.size(); i++)
            diceOnSlot[i] = parseInt(numOfDiceOnSlot.get(i).toString());
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int i = 0; i < totalNumberOfRounds; i++) sb.append("___");
        sb.append("_ \n| ROUND TRACK");
        for (int i = 0; i < totalNumberOfRounds * 3 - 12; i++) sb.append(" ");
        sb.append(" |\n|");
        for (int i = 0; i < totalNumberOfRounds; i++) sb.append("---");
        sb.append("-|\n|");
        int visibleDice = 0;
        int index = -1;
        for (int i = 0; i < currentRoundNumber - 1; i++) {
            index += diceOnSlot[i];
            sb.append(drawDie((JSONObject) dice.get(index)));
            visibleDice++;
        }
        for (int i = 0; i < totalNumberOfRounds - visibleDice; i++)
            sb.append("[").append(visibleDice + i + 1).append("]");
        sb.append("|\n|");
        for (int i = 0; i < totalNumberOfRounds; i++) sb.append("___");
        sb.append("_|");
        return sb.toString();
    }

    private String drawWindowFrame(JSONObject jsonObject) {
        JSONArray coordinates = (JSONArray) jsonObject.get(JSONTag.COORDINATES);
        String name = jsonObject.get(JSONTag.NAME).toString();
        int difficulty = parseInt(jsonObject.get(JSONTag.DIFFICULTY).toString());
        int pixelWidth = 21;
        int leftEmptySide = (pixelWidth - Parameters.MAX_COLUMNS * 3) / 2;
        int rightEmptySide = pixelWidth - Parameters.MAX_COLUMNS * 3 - leftEmptySide;
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int i = 0; i < pixelWidth; i++) sb.append("_");
        sb.append(" \n|");
        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            for (int j = 0; j < leftEmptySide; j++) sb.append(" ");
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                JSONObject coordinate = (JSONObject) coordinates.get(i * Parameters.MAX_COLUMNS + j);
                if (coordinate.get(JSONTag.DIE) != null)
                    sb.append(drawDie((JSONObject) coordinate.get(JSONTag.DIE)));
                else if (coordinate.get(JSONTag.COLOR_CONSTRAINT) != null)
                    sb.append("[").append(CliColor.findByLabel((String) coordinate.get(JSONTag.COLOR_CONSTRAINT)).paint("■")).append("]");
                else if (coordinate.get(JSONTag.SHADE_CONSTRAINT) != null)
                    sb.append(CliShade.findByValue(parseInt(coordinate.get(JSONTag.SHADE_CONSTRAINT).toString())));
                else
                    sb.append("[ ]");
            }
            for (int j = 0; j < rightEmptySide; j++) sb.append(" ");
            sb.append("|\n|");
        }
        for (int i = 0; i < pixelWidth; i++) sb.append("-");
        sb.append("|\n|");
        sb.append(name);
        for (int i = 0; i < pixelWidth - name.length(); i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < difficulty; i++) sb.append("*");
        for (int i = 0; i < pixelWidth - difficulty; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < pixelWidth; i++) sb.append("_");
        sb.append("|\n");
        return sb.toString();
    }

    private String drawPlayer(JSONObject jsonObject) {
        String username = jsonObject.get(JSONTag.USERNAME).toString();
        JSONObject window = (JSONObject) jsonObject.get(JSONTag.WINDOW_FRAME);
        int favorPoints = parseInt(jsonObject.get(JSONTag.FAVOR_POINTS).toString());
        String printableUsername = username + " (FP:" + favorPoints + ")";
        StringBuilder sb = new StringBuilder();
        if (window != null) {
            String frame = drawWindowFrame(window);
            int len = frame.indexOf('\n');
            for (int i = 0; i < len; i++)
                sb.append("-");
            sb.append("\n");
            for (int i = 0; i < (len - printableUsername.length()) / 2; i++)
                sb.append(" ");
            sb.append(printableUsername);
            for (int i = 0; i < len - printableUsername.length() - (len - printableUsername.length()) / 2; i++)
                sb.append(" ");
            sb.append("\n");
            sb.append(frame);
        }
        return sb.toString();
    }

    private String drawBoard(JSONObject jsonObject) {
        JSONObject roundTrack = (JSONObject) jsonObject.get(JSONTag.ROUND_TRACK);
        JSONArray visibleDice = (JSONArray) roundTrack.get(JSONTag.ALL_DICE);
        JSONArray dicePool = (JSONArray) jsonObject.get(JSONTag.DICE_POOL);
        JSONObject pickedDie = (JSONObject) jsonObject.get(JSONTag.PICKED_DIE);
        JSONObject turnManager = (JSONObject) jsonObject.get(JSONTag.TURN_MANAGER);
        JSONArray players = (JSONArray) turnManager.get(JSONTag.PLAYERS);
        StringBuilder sb = new StringBuilder();
        sb.append(drawRoundTrack(roundTrack));
        sb.append("\n\n");
        sb.append("DICE ON ROUND TRACK: ");
        sb.append(drawDice(visibleDice));
        sb.append("\n");
        sb.append("DICE POOL: ");
        sb.append(drawDice(dicePool));
        sb.append("\n");
        sb.append("PICKED DIE: ");
        if (pickedDie != null) sb.append(drawDie(pickedDie));
        sb.append("\n");
        String s = "";
        /*for (PublicObjectiveCard c : publicObjectives)
            s = convertToHorizontal(s, c.toString());
        for (ToolCard c : tools)
            s = convertToHorizontal(s, c.toString());
        sb.append(s);
        sb.append("\n");
        s = "";*/
        for (Object p : players)
            s = s + drawPlayer((JSONObject) p);
        sb.append(s);
        return sb.toString();
    }

    public static void main(String[] args){
        new CLI().draw(new JSONObject());
    }
}
