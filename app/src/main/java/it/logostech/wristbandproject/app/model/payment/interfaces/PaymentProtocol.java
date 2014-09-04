package it.logostech.wristbandproject.app.model.payment.interfaces;

import it.logostech.wristbandproject.app.model.payment.protocol.PaymentMessageBase;

/**
 * Created by michele.schimd on 04/09/2014.
 */
public interface PaymentProtocol {
    /**
     * Every time a message arrives on the system queue (either from the NFC or
     * from the secured channel), it is dispatched to this method which based
     * on the actual implementation, should activate the proper code.
     * <p/>
     * This method is set abstract so that each implementation (Gate, Wear or
     * Auth) can define proper actions for different received messages
     *
     * @param message the next message to be processed
     */
    void onMessageReceived(PaymentMessageBase message);
}
