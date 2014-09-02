package it.logostech.wristbandproject.app.model.payment.protocol;

/**
 * Created by michele.schimd on 02/09/2014.
 */
public abstract class PaymentMessageBase {

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
}
