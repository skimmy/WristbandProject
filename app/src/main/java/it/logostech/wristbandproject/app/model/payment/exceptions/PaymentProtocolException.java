package it.logostech.wristbandproject.app.model.payment.exceptions;

/**
 * Created by Michele Schimd on 28/10/2014.
 *
 * @version 1.0
 */
public class PaymentProtocolException extends RuntimeException {

    public PaymentProtocolException(String detailMessage) {
        super(detailMessage);
    }
}