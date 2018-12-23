package server;

import com.google.gson.JsonObject;

import java.util.Calendar;
import java.util.Date;

/**
 * A message sent between Players and Listeners. The Message class
 * is mainly used to wrap the message's contents with the message's author
 * and creation date.
 *
 * @author Alston
 * last updated 12/22/2018
 */
class Message {

    /**
     * The player that is sending the message
     */
    final Player author;

    /**
     * The date the messages was constructed
     */
    final Date creationTime;

    /**
     * The contents of the message in JsonObject format
     */
    final JsonObject contents;

    /**
     * Constructs a message written by a specified author and contents.
     * The creationTIme of the message is set to the time that this constructor is called.
     *
     * @param author   the author of the message
     * @param contents the contents of the message
     */
    Message(Player author, JsonObject contents) {
        this.author = author;
        this.creationTime = Calendar.getInstance().getTime();
        this.contents = contents;
    }

}
