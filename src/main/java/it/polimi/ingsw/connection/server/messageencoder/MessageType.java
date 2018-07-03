package it.polimi.ingsw.connection.server.messageencoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The MessageType enumeration contains all the typology of messages that the server can send to the client.
 * it offers the metod to encode and decode the content of the messages.
 */
public enum MessageType {
    GENERIC_MESSAGE("generic_message"),
    ERROR_MESSAGE("error_message"),
    GAME_BOARD("game_board"),
    GAME_STATS("game_stats"),
    PRE_GAME_CHOICE("pre_game_choice"),
    SESSION("session"),
    CURRENT_PLAYER("current_player");

    private String tag;
    private static final String MESSAGE_TAG="tag";
    private static final String MESSAGE_CONTENT="message_content";
    private static final String ERROR="Bad JSON file.";
    private static final String INVALID_TAG="Tag not exists";

    /**
     * Creates a new MessageType.
     * @param tag - The identification tag of the message.
     */
    MessageType (String tag){
        this.tag=tag;
    }

    /**
     * Returns the tag type of the message.
     * @return - The tag type of the message.
     */
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

    /**
     * Decodes the message, returns the content of the message.
     * @param encodedMessage - The encoded message.
     * @return - The content of the encoded message.
     */
    public static String decodeMessageContent(String encodedMessage){
        try {
            return ((JSONObject)new JSONParser().parse(encodedMessage)).get(MESSAGE_CONTENT).toString();
        } catch (ParseException e) {
            throw new IllegalArgumentException(ERROR);
        }
    }

    /**
     * Decodes the MessageType of the encodedMeesage.
     * @param encodedMessage - The encodedMessage.
     * @return - MessageType. The MessageType of the encodedMessage.
     */
    public static MessageType decodeMessageType(String encodedMessage){
        try {
            return getMessageTypeFromString(((JSONObject)new JSONParser().parse(encodedMessage)).get(MESSAGE_TAG).toString());
        } catch (ParseException e) {
            throw new IllegalArgumentException(ERROR);
        }
    }

    /**
     *
     * @param tag - The tag in String format.
     * @return - The MessageType from tag.
     */
    private static MessageType getMessageTypeFromString(String tag){
        for(MessageType messageType: MessageType.values())
            if(tag.equals(messageType.getTag()))
                return messageType;
        throw new IllegalArgumentException(INVALID_TAG);
    }
}
