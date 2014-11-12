package it.logostech.wristbandproject.app.model.payment.protocol;

import it.logostech.wristbandproject.app.model.payment.PaymentDetails;

/**
 * @author Michele Schimd
 * @since 02/09/2014
 * @version 1.1
 */
public class PaymentRequestCustomer extends PaymentRequest {

    public static PaymentRequestCustomer fromPaymentDetails(PaymentDetails details) {
        return (new Builder(details)).build();
    }

    public static class Builder {
        private final PaymentDetails details;

        public Builder(PaymentDetails details) {
            this.details = details;
        }

        public PaymentRequestCustomer build() {
            return new PaymentRequestCustomer(details.getWearId(), null,
                    details.getTransactionId(), details);
        }
    }

    public PaymentRequestCustomer(String senderId, String receiverId, String transactionId, PaymentDetails details) {
        super(senderId, receiverId, transactionId, details);
    }
}
