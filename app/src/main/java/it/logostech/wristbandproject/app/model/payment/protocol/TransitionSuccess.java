package it.logostech.wristbandproject.app.model.payment.protocol;

/**
 * Created by michele.schimd on 02/09/2014.
 */
public class TransitionSuccess extends PaymentMessageBase {

    public TransitionSuccess(String senderId, String receiverId, String transactionId) {
        super(senderId, receiverId, transactionId);
    }

    @Override
    public int getChannel() {
        return HTTPS_CHANNEL;
    }
}