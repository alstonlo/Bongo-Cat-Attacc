package protocol;

import java.util.Calendar;
import java.util.Date;

/**
 * Abstract representation of a message between the client and server.
 *
 * @author Alston
 * last updated 12/19/2018
 */
public abstract class Message {

    /**
     * True, if the message is meant to be sent over TCP (default); false,
     * if the message is meant to be sent over UDP.
     */
    public boolean isTCP = true;

    /**
     * The time this Message is constructed.
     */
    public final Date timeCreated;

    /**
     * The ID of the message, if necessary (default null).
     */
    public String id = null;

    /**
     * Constructs a Message. Sets {@link Message#timeCreated} to the time
     * that this constructor is called.
     */
    public Message() {
        this.timeCreated = Calendar.getInstance().getTime();
    }
}
