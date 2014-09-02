package it.logostech.wristbandproject.app.model.payment.protocol;

/**
 * Created by michele.schimd on 02/09/2014.
 */
public class TransitionSuccessCustomer extends TransitionSuccess {
    public TransitionSuccessCustomer(String senderId, String receiverId, String transactionId) {
        super(senderId, receiverId, transactionId);
    }
}
