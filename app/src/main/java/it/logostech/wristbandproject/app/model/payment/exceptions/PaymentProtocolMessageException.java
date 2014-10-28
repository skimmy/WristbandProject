package it.logostech.wristbandproject.app.model.payment.exceptions;

/**
 * Error during parse of the payment protocol messages
 *
 * Created by Michele Schimd on 04/09/2014.
 *
 * @version 1.1
 */
public class PaymentProtocolMessageException extends PaymentProtocolException {

    public PaymentProtocolMessageException(String detailMessage) {
        super(detailMessage);
    }
}
