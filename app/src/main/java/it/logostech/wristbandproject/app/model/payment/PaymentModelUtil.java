package it.logostech.wristbandproject.app.model.payment;

import java.util.Random;

/**
 * Collection of helper methods for the <code>model</code> package.
 *
 * Created by Michele Schimd on 10/23/14.
 *
 * @version  1.0
 */
public class PaymentModelUtil {

    /** Byte size for transaction ids (to be possibly tuned) */
    private static final int TRANSACTION_ID_SIZE = 64;

    /** Maximum number of bytes allowed for a device id */
    public static final int DEVICE_ID_MAXIMUM_SIZE = 64;


    /**
     * Generates a new (pseudo random) <i>transaction id</i> that can be used to
     * create a transaction between NFC devices.
     * The method requires a seed to generate pseudo random transaction id so to
     * provide testing facilities (<i>i.e.</i> passing the same seed results in
     * the same generated transaction id).
     * To make the generated ID suitable for NFC communications (using NDEF
     * protocol and NDEF messages), the size of the generated id is kept as low as
     * 64 bytes which is a value (to be tested) supposedly  low enough to keep
     * NFC communication timely and efficient while big enough to avoid collisions
     * on the backend size.
     *
     * @param seed the seed used to generate ID
     * @return a (pseudo) random generated ID
     */
    public static byte[] newTransactionId(long seed) {
        Random r = new Random(seed);
        byte[] id = new byte[TRANSACTION_ID_SIZE];
        r.nextBytes(id);
        return id;
    }

    /**
     * Generates a new random <i>transaction id</i> that can be used to create
     * transactions between NFC devices.
     * This is just a convenience method calling
     * {@link it.logostech.wristbandproject.app.model.payment.PaymentModelUtil#newTransactionId(long)}
     * with <code>seed obtained</code> using <code>currentTimeMillis</code> system call.
     *
     * @return
     */
    public static byte[] newTransactionId() {
        return newTransactionId(System.currentTimeMillis());
    }
}
