package it.logostech.wristbandproject.app.model.payment.protocol;

import com.appspot.wristband_unipd.paymentremote.model.PaymentMessagesPaymentAuthorizedCustomerMessage;

import it.logostech.wristbandproject.app.model.payment.PaymentChallenge;
import it.logostech.wristbandproject.app.model.payment.PaymentDetails;

/**
 * @author Michele Schimd
 * @since 02/09/2014.
 * @version 1.1
 */
public class PaymentAuthorizationCustomer extends PaymentAuthorization {

    /**
     *  Construct a {@link it.logostech.wristbandproject.app.model.payment.protocol.PaymentAuthorizationCustomer}
     *  starting from a message received from a call to the Google Cloud Endpoints client library.
     *
     * @param msg The message received by remote service
     * @return A model object
     */
    public static PaymentAuthorizationCustomer fromCustomerAuthMessage(
            PaymentMessagesPaymentAuthorizedCustomerMessage msg) {

        if (msg == null) {
            return null;
        }
        PaymentAuthorizationCustomer.Builder builder = new
                PaymentAuthorizationCustomer.Builder(
                PaymentDetails.fromPaymentDetailMessage(msg.getDetails()), null);
        return builder.build();
    }

    public static class Builder {
        private final String transaction;
        private final PaymentDetails details;
        private final String sender;

        private String receiver;
        private PaymentChallenge paymentChallenge;

        public Builder(PaymentDetails details, String remoteId) {
            this.transaction = details.getTransactionId();
            this.details = details;
            // The sender is AUTH (this will basically never be anything but null)
            this.sender = remoteId;
        }

        public Builder receiver(String r) {
            this.receiver = r;
            return this;
        }

        public Builder challenge(PaymentChallenge c) {
            this.paymentChallenge = c;
            return this;
        }

        public PaymentAuthorizationCustomer build() {
            return new PaymentAuthorizationCustomer(sender, receiver, details.getTransactionId(),
                    details, this.paymentChallenge);
        }
    }

    public PaymentAuthorizationCustomer(String senderId, String receiverId, String transactionId,
                                        PaymentDetails details, PaymentChallenge challenge) {
        super(senderId, receiverId, transactionId, details, challenge);
    }
}
