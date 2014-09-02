package it.logostech.wristbandproject.app.model.payment.protocol;

/**
 * Created by michele.schimd on 02/09/2014.
 */
public class TransitionSuccessMerchant extends TransitionSuccess {
    public TransitionSuccessMerchant(String senderId, String receiverId, String transactionId) {
        super(senderId, receiverId, transactionId);
    }
}
