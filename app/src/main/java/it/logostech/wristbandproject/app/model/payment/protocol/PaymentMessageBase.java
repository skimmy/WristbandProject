package it.logostech.wristbandproject.app.model.payment.protocol;

import java.io.Serializable;

/**
 * This is the base class for all payment messages.
 *
 * Created by Michele Schimd on 02/09/2014.
 *
 * @version 1.0
 */
public abstract class PaymentMessageBase {

    /** Generic operation code used when no other is available */
    public static  final byte OP_CODE = 100;

    public static final int NFC_CHANNEL = 1;
    public static final int HTTPS_CHANNEL = 2;

    private String senderId;
    private String receiverId;
    private String transactionId;

    public PaymentMessageBase(String senderId, String receiverId, String transactionId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.transactionId = transactionId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public abstract int getChannel();

    public abstract byte[] toByteArray();

}
