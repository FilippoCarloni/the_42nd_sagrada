package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.connection.client.ConnectionManager;
import it.polimi.ingsw.connection.client.ConnectionType;
import it.polimi.ingsw.connection.server.messageencoder.MessageType;
import it.polimi.ingsw.model.utility.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static it.polimi.ingsw.connection.constraints.ConnectionCommands.QUIT_COMMAND;
import static it.polimi.ingsw.view.ViewMessage.*;
import static it.polimi.ingsw.view.cli.CLIMessage.*;
import static java.lang.Integer.parseInt;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Terminal view of the Sagrada Game.
 * Uses ansi colors for color rendering.
 * A monospaced font is highly recommended for smooth usage of the CLI.
 */
public class CLI implements Runnable {

    // Board printer descriptors
    private static final String SEPARATOR = " ";
    private static final String TOP_SEPARATOR = "  ";
    private static final String DICE_SEPARATOR = " ";
    private static final String ROUND_TRACK_TITLE = "ROUND TRACK";
    private static final String DICE_ON_ROUND_TRACK = "Round Track dice  :  ";
    private static final String DICE_POOL = "Dice Pool         :  ";
    private static final String DRAFTED_DIE = "Drafted die       :  ";
    private static final String ACTIVE_TOOL_CARD = "Active Tool Card  :  ";
    private static final String TOOL_NOT_ACTIVE = "none";
    private static final String DIE_NOT_PICKED = "[ ]";
    private static final String EMPTY_ROUND_TRACK = "empty";
    private static final String SERVER_ERROR = "Server not reachable";
    private static final String SCORE_TITLE = "+-------------+\n" +
            "| FINAL SCORE |\n" +
            "+-------------+\n";

    // Board printer constants
    private static final int PIXEL_WIDTH = 21;
    private static final int NAME_LENGTH = 40;
    private static final int DESCRIPTION_LENGTH = 120;
    private static final int ROUND_TRACK_LENGTH = 44;
    private static final int TOOL_IMAGE_LENGTH = 9;
    private static final int OBJECTIVE_IMAGE_LENGTH = 15;
    private static final String EXT = ".cliimage";
    private static final String CLI_IMAGES_PATH = "src/main/java/res/cliimages/";
    private static final String PRE_ID = "card";
    private static final String DEFAULT_CLI_IMAGE = "default";

    private Scanner scanner;
    private ConnectionManager connectionController;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final int REFRESH_RATE = 100; // milliseconds

    public CLI() {
        scanner = new Scanner(System.in);
        print(TITLE);
        connect();
        login();
        menu();
        new Thread(this::run).start();
        startRefresh();
    }

    @Override
    public void run() {
        String message;
        message = scanner.nextLine();
        while (message != null) {
            connectionController.send(message);
            if(message.trim().equals(QUIT_COMMAND))
                System.exit(0);
            message = scanner.nextLine();
        }
    }

    private void startRefresh() {
        scheduler.scheduleAtFixedRate(this::update, 1, REFRESH_RATE, MILLISECONDS);
    }

    private void login() {
        String username;
        print(INSERT_USERNAME);
        username = scanner.nextLine();
        try {
            while (!connectionController.restore(username)) {
                print(INVALID_USERNAME);
                print(INSERT_USERNAME);
                username = scanner.nextLine();
            }
        } catch (NoSuchElementException ex) {
            print(SERVER_ERROR);
            System.exit(1);
        }
        print(LOGIN_CONFIRMATION);
    }

    private void connect() {
        ConnectionType connectionType;
        print(CONNECTION_TYPE);
        print(CONNECTION_TYPE_OPTIONS);
        connectionType = scanner.nextLine().equals(CONNECTION_TYPE_FIRST_OPTION) ? ConnectionType.RMI : ConnectionType.SOCKET;
        try {
            connectionController = new ConnectionManager(connectionType);
        } catch (Exception e) {
            print(CONNECTION_ERROR);
            System.exit(1);
        }
        print(CONNECTION_CONFIRMATION);
    }

    private void menu() {
        print(USAGE);
    }

    private void update() {
        String message = connectionController.readMessage();
        try {
            if (message.length() > 0) {
                switch (MessageType.decodeMessageType(message)) {
                    case GENERIC_MESSAGE:
                        print(MessageType.decodeMessageContent(message));
                        break;
                    case GAME_BOARD:
                        this.draw((JSONObject) new JSONParser().parse(MessageType.decodeMessageContent(message)));
                        break;
                    case ERROR_MESSAGE:
                        print(MessageType.decodeMessageContent(message));
                        break;
                    case PRE_GAME_CHOICE:
                        drawPreGame((JSONObject) new JSONParser().parse(MessageType.decodeMessageContent(message)));
                        break;
                    case CURRENT_PLAYER:
                        print(CURRENT_PLAYER_MESSAGE + MessageType.decodeMessageContent(message));
                        break;
                    case GAME_STATS:
                        drawScore((JSONObject) new JSONParser().parse(MessageType.decodeMessageContent(message)));
                        break;
                    default:
                        print(UNSUPPORTED_MESSAGE);
                }
            }
        } catch (Exception e) {
            print(e.getMessage());
        }
    }

    private void print(String data) {
        System.out.println(data);
    }

    private void drawPreGame(JSONObject jsonObject) {
        StringBuilder sb = new StringBuilder();
        String s = "";
        JSONArray windowsFrames = (JSONArray) jsonObject.get(JSONTag.WINDOW_FRAMES);
        sb.append(drawCard((JSONObject) jsonObject.get(JSONTag.PRIVATE_OBJECTIVE)));
        sb.append("\n");
        for (Object o : windowsFrames)
            s = convertToHorizontal(s, drawWindowFrame((JSONObject) o), SEPARATOR);
        sb.append(s);
        print(sb.toString());
    }

    private void draw(JSONObject obj) {
        print(drawBoard(obj));
    }

    private String drawDice(JSONArray jsonArray) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Object o : jsonArray) {
            sb.append(i).append(drawDie((JSONObject) o)).append(DICE_SEPARATOR);
            i++;
        }
        return sb.toString();
    }

    private String drawDie(JSONObject jsonObject) {
        CLIShade shade = CLIShade.findByValue(parseInt(jsonObject.get(JSONTag.SHADE).toString()));
        CLIColor color = CLIColor.findByLabel(jsonObject.get(JSONTag.COLOR).toString());
        if (shade != null && color != null)
            return color.paint(shade.toString());
        throw new NullPointerException();
    }

    private String drawRoundTrack(JSONObject jsonObject) {
        JSONArray dice = (JSONArray) jsonObject.get(JSONTag.ALL_DICE);
        int currentRoundNumber = parseInt(jsonObject.get(JSONTag.CURRENT_ROUND_NUMBER).toString());
        int[] diceOnSlot = new int[Parameters.TOTAL_NUMBER_OF_ROUNDS];
        JSONArray numOfDiceOnSlot = (JSONArray) jsonObject.get(JSONTag.NUMBER_OF_DICE_ON_SLOT);
        for (int i = 0; i < numOfDiceOnSlot.size(); i++)
            diceOnSlot[i] = parseInt(numOfDiceOnSlot.get(i).toString());
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int i = 0; i < ROUND_TRACK_LENGTH; i++) sb.append("_");
        sb.append("_ \n|");
        for (int i = 0; i < (ROUND_TRACK_LENGTH - ROUND_TRACK_TITLE.length()) / 2; i++) sb.append(" ");
        sb.append(ROUND_TRACK_TITLE);
        for (int i = 0; i < ROUND_TRACK_LENGTH - ROUND_TRACK_TITLE.length() - (ROUND_TRACK_LENGTH - ROUND_TRACK_TITLE.length()) / 2; i++)
            sb.append(" ");
        sb.append(" |\n|");
        for (int i = 0; i < ROUND_TRACK_LENGTH; i++) sb.append("-");
        sb.append("-|\n|  ");
        int visibleDice = 0;
        int index = -1;
        for (int i = 0; i < currentRoundNumber - 1; i++) {
            index += diceOnSlot[i];
            sb.append(" ").append(drawDie((JSONObject) dice.get(index)));
            visibleDice++;
        }
        for (int i = 0; i < Parameters.TOTAL_NUMBER_OF_ROUNDS - visibleDice; i++)
            sb.append(" [").append(visibleDice + i + 1).append("]");
        sb.append("  |\n|");
        for (int i = 0; i < ROUND_TRACK_LENGTH; i++) sb.append("_");
        sb.append("_|");
        return sb.toString();
    }

    private String drawWindowFrame(JSONObject jsonObject) {
        JSONArray coordinates = (JSONArray) jsonObject.get(JSONTag.COORDINATES);
        String name = jsonObject.get(JSONTag.NAME).toString();
        int difficulty = parseInt(jsonObject.get(JSONTag.DIFFICULTY).toString());
        int leftEmptySide = (PIXEL_WIDTH - Parameters.MAX_COLUMNS * 3) / 2 - 2;
        int rightEmptySide = PIXEL_WIDTH - Parameters.MAX_COLUMNS * 3 - leftEmptySide - 2;
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int i = 0; i < PIXEL_WIDTH; i++) sb.append("_");
        sb.append(" \n|");
        for (int i = 0; i < leftEmptySide; i++) sb.append(" ");
        sb.append("  ");
        for (int i = 0; i < Parameters.MAX_COLUMNS; i++) sb.append(" ").append(i + 1).append(" ");
        for (int i = 0; i < rightEmptySide; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            for (int j = 0; j < leftEmptySide; j++) sb.append(" ");
            sb.append(i + 1).append(" ");
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                JSONObject coordinate = (JSONObject) coordinates.get(i * Parameters.MAX_COLUMNS + j);
                if (coordinate.get(JSONTag.DIE) != null)
                    sb.append(drawDie((JSONObject) coordinate.get(JSONTag.DIE)));
                else if (coordinate.get(JSONTag.COLOR_CONSTRAINT) != null)
                    sb.append("[").append(CLIColor.findByLabel((String) coordinate.get(JSONTag.COLOR_CONSTRAINT)).paint("■")).append("]");
                else if (coordinate.get(JSONTag.SHADE_CONSTRAINT) != null)
                    sb.append(CLIShade.findByValue(parseInt(coordinate.get(JSONTag.SHADE_CONSTRAINT).toString())));
                else
                    sb.append("[ ]");
            }
            for (int j = 0; j < rightEmptySide; j++) sb.append(" ");
            sb.append("|\n|");
        }
        for (int i = 0; i < PIXEL_WIDTH; i++) sb.append("-");
        sb.append("|\n|");
        sb.append(name);
        for (int i = 0; i < PIXEL_WIDTH - name.length(); i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < difficulty; i++) sb.append("*");
        for (int i = 0; i < PIXEL_WIDTH - difficulty; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < PIXEL_WIDTH; i++) sb.append("_");
        sb.append("|\n");
        return sb.toString();
    }

    private String drawPlayer(JSONObject jsonObject) {
        String username = jsonObject.get(JSONTag.USERNAME).toString();
        JSONObject window = (JSONObject) jsonObject.get(JSONTag.WINDOW_FRAME);
        int favorPoints = parseInt(jsonObject.get(JSONTag.FAVOR_POINTS).toString());
        String printableUsername = "{ " + username + " } (FP:" + favorPoints + ")";
        StringBuilder sb = new StringBuilder();
        if (window != null) {
            String frame = drawWindowFrame(window);
            sb.append(frame);
            int len = frame.indexOf('\n');
            sb.append("\n");
            for (int i = 0; i < (len - printableUsername.length()) / 2; i++)
                sb.append(" ");
            sb.append(printableUsername);
            for (int i = 0; i < len - printableUsername.length() - (len - printableUsername.length()) / 2; i++)
                sb.append(" ");
        } else sb.append("Null window.");
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
        StringBuilder turnStateData = new StringBuilder();
        turnStateData.append("\n").append(DICE_ON_ROUND_TRACK);
        if (visibleDice.isEmpty()) turnStateData.append(EMPTY_ROUND_TRACK);
        else turnStateData.append(drawDice(visibleDice));
        turnStateData.append("\n").append(DICE_POOL);
        turnStateData.append(drawDice(dicePool));
        turnStateData.append("\n").append(DRAFTED_DIE);
        if (pickedDie != null) turnStateData.append(drawDie(pickedDie));
        else turnStateData.append(DIE_NOT_PICKED);
        turnStateData.append("\n").append(ACTIVE_TOOL_CARD);
        if (parseInt(jsonObject.get(JSONTag.ACTIVE_TOOL_ID).toString()) == 0)
            turnStateData.append(TOOL_NOT_ACTIVE);
        else turnStateData.append(jsonObject.get(JSONTag.ACTIVE_TOOL_ID).toString());
        turnStateData.append(" \n");
        sb.append(convertToHorizontal(drawRoundTrack(roundTrack), turnStateData.toString(), TOP_SEPARATOR));
        String s = "";
        JSONArray publicObjectives = (JSONArray) jsonObject.get(JSONTag.PUBLIC_OBJECTIVES);
        for (Object po : publicObjectives)
            s = convertToHorizontal(s, drawCard((JSONObject) po), SEPARATOR);
        JSONArray tools = (JSONArray) jsonObject.get(JSONTag.TOOLS);
        for (Object tool : tools)
            s = convertToHorizontal(s, drawCard((JSONObject) tool), SEPARATOR);
        sb.append(s);
        s = "";
        for (Object p : players)
            s = convertToHorizontal(s, drawPlayer((JSONObject) p), SEPARATOR);
        sb.append(s);
        return sb.toString();
    }

    private String convertToHorizontal(String s1, String s2, String separator) {
        if (s1.length() == 0) return s2;
        if (s2.length() == 0) return s1;
        StringBuilder sb = new StringBuilder();
        String[] splitS1 = s1.split("\n");
        String[] splitS2 = s2.split("\n");
        int min = splitS1.length > splitS2.length ? splitS2.length : splitS1.length;
        for (int i = 0; i < min; i++) {
            sb.append(splitS1[i]);
            sb.append(separator);
            sb.append(splitS2[i]);
            sb.append("\n");
        }
        return sb.toString();
    }

    private String drawCard(JSONObject jsonObject) {
        int id = parseInt(jsonObject.get(JSONTag.CARD_ID).toString());
        String image = loadImage(id);
        String name = "[" + id + "] " + jsonObject.get(JSONTag.NAME).toString();
        String description = jsonObject.get(JSONTag.DESCRIPTION).toString();
        Long points = (Long) jsonObject.get(JSONTag.FAVOR_POINTS);
        if (points != null)
            name = name + " (FP:" + points.intValue() + ")";
        StringBuilder sb = new StringBuilder();
        String[] splitImage = image.split("\n");
        int imageLength;
        if (splitImage.length == 3) {
            sb.append("|");
            for (int i = 0; i < PIXEL_WIDTH; i++) sb.append(" ");
            sb.append("|\n");
            imageLength = TOOL_IMAGE_LENGTH;
        } else imageLength = OBJECTIVE_IMAGE_LENGTH;
        for (String line : splitImage) {
            sb.append("|");
            for (int i = 0; i < (PIXEL_WIDTH - imageLength) / 2; i++) sb.append(" ");
            sb.append(line);
            for (int i = 0; i < PIXEL_WIDTH - imageLength - (PIXEL_WIDTH - imageLength) / 2; i++) sb.append(" ");
            sb.append("|\n");
        }
        return getUpperCard() + sb.toString() + getLowerCard(name, description);
    }

    private String loadImage(int id) {
        try {
            return new String(Files.readAllBytes(Paths.get(CLI_IMAGES_PATH + PRE_ID + id + EXT)));
        } catch (IOException e) {
            return loadDefaultImage();
        }
    }

    private String loadDefaultImage() {
        try {
            return new String(Files.readAllBytes(Paths.get(CLI_IMAGES_PATH + DEFAULT_CLI_IMAGE + EXT)));
        } catch (IOException e) {
            throw new IllegalArgumentException(IMAGE_NOT_FOUND);
        }
    }

    private String getUpperCard() {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int i = 0; i < PIXEL_WIDTH; i++) sb.append("_");
        sb.append(" \n");
        return sb.toString();
    }

    private String getLowerCard(String name, String description) {
        StringBuilder nameBuilder = new StringBuilder(name);
        StringBuilder descriptionBuilder = new StringBuilder(description);
        for (int i = 0; i < NAME_LENGTH - name.length(); i++)
            nameBuilder.append(" ");
        for (int i = 0; i < DESCRIPTION_LENGTH - description.length(); i++)
            descriptionBuilder.append(" ");
        String longName = nameBuilder.toString();
        String longDescription = descriptionBuilder.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        for (int i = 0; i < PIXEL_WIDTH; i++) sb.append("-");
        sb.append("|\n");
        int i;
        for (i = 0; i < longName.length() / PIXEL_WIDTH; i++)
            sb.append("|").append(longName.substring(i * PIXEL_WIDTH, PIXEL_WIDTH * (i + 1))).append("|\n");
        sb.append("|").append(longName.substring(
                i * PIXEL_WIDTH, longName.length()));
        for (int j = 0; j < PIXEL_WIDTH - longName.length() + i * PIXEL_WIDTH; j++)
            sb.append(" ");
        sb.append("|\n|");
        for (i = 0; i < PIXEL_WIDTH; i++) sb.append("-");
        sb.append("|\n");
        for (i = 0; i < longDescription.length() / PIXEL_WIDTH; i++)
            sb.append("|").append(longDescription.substring(i * PIXEL_WIDTH, PIXEL_WIDTH * (i + 1))).append("|\n");
        sb.append("|").append(longDescription.substring(
                i * PIXEL_WIDTH, longDescription.length()));
        for (int j = 0; j < PIXEL_WIDTH - longDescription.length() + i * PIXEL_WIDTH; j++)
            sb.append(" ");
        sb.append("|\n|");
        for (int j = 0; j < PIXEL_WIDTH; j++) sb.append("_");
        sb.append("|\n");
        return sb.toString();
    }

    private void drawScore(JSONObject score) {
        StringBuilder sb = new StringBuilder();
        sb.append(SCORE_TITLE);
        JSONArray players = (JSONArray) score.get(JSONTag.PLAYERS);
        for (Object o : players) {
            sb.append("> ");
            sb.append(((JSONObject) o).get(JSONTag.USERNAME));
            sb.append("\t:\t");
            sb.append(((JSONObject) o).get(JSONTag.SCORE));
            sb.append("\n");
        }
        print(sb.toString());
    }
}