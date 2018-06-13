package it.polimi.ingsw.connection.server.messageencoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public enum MessageType {
    GENERIC_MESSAGE("generic_message"),
    ERROR_MESSAGE("error_message"),
    GAME_BOARD("game_board"),
    GAME_STATS("game_stats"),
    PRE_GAME_CHOICE("pre_game_choice"),
    SESSION("session");
    private String tag;
    private static final String MESSAGE_TAG="tag";
    private static final String MESSAGE_CONTENT="message_content";
    private static final String ERROR="Bad JSON file.";
    private static final String INVALID_TAG="Tag not exists";
    MessageType (String tag){
        this.tag=tag;
    }

    public String getTag() {
        return tag;
    }

    @SuppressWarnings("unchecked")
    public static String encodeMessage(String message, MessageType messageType){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(MESSAGE_TAG,messageType.getTag());
        jsonObject.put(MESSAGE_CONTENT,message);
        return jsonObject.toString();
    }

    public static String decodeMessageContent(String encodedMessage){
        try {
            return ((JSONObject)new JSONParser().parse(encodedMessage)).get(MESSAGE_CONTENT).toString();
        } catch (ParseException e) {
            throw new IllegalArgumentException(ERROR);
        }
    }

    public static MessageType decodeMessageType(String encodedMessage){
        try {
            return getMessageTypeFromString(((JSONObject)new JSONParser().parse(encodedMessage)).get(MESSAGE_TAG).toString());
        } catch (ParseException e) {
            throw new IllegalArgumentException(ERROR);
        }
    }

    private static MessageType getMessageTypeFromString(String tag){
        for(MessageType messageType: MessageType.values())
            if(tag.equals(messageType.getTag()))
                return messageType;
        throw new IllegalArgumentException(INVALID_TAG);
    }
}
