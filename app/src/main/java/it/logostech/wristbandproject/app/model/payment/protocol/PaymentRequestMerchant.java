package it.logostech.wristbandproject.app.model.payment.protocol;

import it.logostech.wristbandproject.app.model.payment.PaymentDetails;

/**
 * Message sent from <b>gate</b> to <b>auth</b> to obtain a <i>payment
 * authorization</i>.
 *
 * @author Michele Schimd
 * @version 1.0
 * @since 02/09/2014.
 */
public class PaymentRequestMerchant extends PaymentRequest {


    public static PaymentRequestMerchant fromPaymentDetailsAndIds(PaymentDetails details, String gateId, String authId) {
        return new PaymentRequestMerchant(gateId, authId, details.getTransactionId(), details);
    }

    public PaymentRequestMerchant(String senderId, String receiverId, String transactionId, PaymentDetails details) {
        super(senderId, receiverId, transactionId, details);
    }
}
