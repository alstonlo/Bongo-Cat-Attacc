package protocol;

import java.util.Calendar;
import java.util.Date;

/**
 * Abstract representation of a protocol message between the client and server.
 *
 * @author Alston
 * last updated 12/19/2018
 */
public abstract class Protocol {

    /**
     * True, if the protocol is meant to be sent over TCP (default); false,
     * if the protocol is meant to be sent over UDP.
     */
    public boolean isTCP = true;

    /**
     * The time this Protocol is constructed.
     */
    public final Date timeCreated;

    /**
     * Constructs a Protocol. Sets {@link Protocol#timeCreated} to the time
     * that this constructor is called.
     */
    public Protocol() {
        this.timeCreated = Calendar.getInstance().getTime();
    }

}
