package it.polimi.ingsw.model.utility;

import org.json.simple.JSONObject;

/**
 * Represents any object that can be converted in JSON format.
 */
public interface JSONSerializable {

    /**
     * Returns the JSON encoding of the object.
     * @return A JSONObject instance that holds the object information in JSON syntax
     */
    JSONObject encode();
}
