package it.logostech.wristbandproject.app.nfc;

import java.util.Random;

import it.logostech.wristbandproject.app.model.TagModel;

/**
 * Defines a <i>NFC session</i> which is characterized by:
 * <ul>
 *     <li>
 *         a {@link it.logostech.wristbandproject.app.model.TagModel} object
 *         representing the remote NFC device,
 *     </li>
 *     <li>
 *         a random generated session identifier and
 *     </li>
 *     <li>
 *         a timestamp indicating the <i>last contact time</i>
 *     </li>
 * </ul>
 *
 * The current version does not define any constraint on the sessions (like, for
 * example, expiration time), however this class can be modified in the future to
 * to cope with anomalies of the NFC system.
 *
 * Created by Michele Schimd on 10/29/14.
 *
 * @version 1.0
 */
public class NfcSession {

    public static NfcSession createSessionFor(TagModel tag) {
        return new NfcSession(tag);
    }

    private TagModel tagModel;
    private long sessionId;
    private long aliveTimestamp;

    /**
     * Thi constructor creates a session for the given model, a randomly generated
     * <i>id</i> and initialize the timestamp to the current instant
     * @param model
     */
    public NfcSession(TagModel model) {
        this.tagModel = model;
        this.sessionId = (new Random()).nextLong();
        this.touch();
    }

    /**
     * Refreshes the timestamp to the current time so to keep the session alive.
     */
    public void touch() {
        this.aliveTimestamp = System.currentTimeMillis();
    }

    public TagModel getTagModel() {
        return tagModel;
    }
}
