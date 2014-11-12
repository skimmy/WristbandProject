package it.logostech.wristbandproject.app.model.payment.protocol;

import com.appspot.wristband_unipd.paymentremote.model.PaymentMessagesPaymentAuthorizedMerchantMessage;

import it.logostech.wristbandproject.app.model.payment.PaymentChallenge;
import it.logostech.wristbandproject.app.model.payment.PaymentDetails;

/**
 * @author Michele Schimd
 * @since 02/09/2014
 * @version 1.0
 */
public class PaymentAuthorizationMerchant extends PaymentAuthorization {


    /**
     * Factory method that creates a {@link it.logostech.wristbandproject.app.model.payment.protocol.PaymentAuthorizationMerchant}
     * from a {@link com.appspot.wristband_unipd.paymentremote.model.PaymentMessagesPaymentAuthorizedMerchantMessage}
     * which is a message class generated as a representation of a <i>Google Cloud Endpoints</i>
     * message.
     *
     * @param msg the message from which creating the object
     * @return a {@link it.logostech.wristbandproject.app.model.payment.protocol.PaymentAuthorizationMerchant}
     * containing the same information stored in the input message
     */
    public static PaymentAuthorizationMerchant fromMerchantAuthMessage(
            PaymentMessagesPaymentAuthorizedMerchantMessage msg) {
        PaymentAuthorizationMerchant.Builder builder = new PaymentAuthorizationMerchant.Builder(
                        PaymentDetails.fromPaymentDetailMessage(msg.getDetails()), null);
        return builder.build();
    }

    /**
     * Builder class for {@link it.logostech.wristbandproject.app.model.payment.protocol.PaymentAuthorizationMerchant}
     *
     * @version 1.0
     */
    public static class Builder {

        private final String transaction;
        private final PaymentDetails details;
        private final String sender;

        private String receiver;
        private PaymentChallenge challenge;

        public Builder(PaymentDetails details, String remoteId) {
            transaction = details.getTransactionId();
            this.details = details;
            // The sender is AUTH (this will basically never be anything but null)
            this.sender = remoteId;
        }

        public Builder receiver(String r) {
            this.receiver = r;
            return this;
        }

        public Builder challenge(PaymentChallenge c) {
            this.challenge = c;
            return this;
        }

        public PaymentAuthorizationMerchant build() {
            return new PaymentAuthorizationMerchant(this.sender, this.receiver, this.transaction,
                    this.details, this.challenge);
        }

    }

    public PaymentAuthorizationMerchant(String senderId, String receiverId, String transactionId,
                                        PaymentDetails details, PaymentChallenge challenge) {
        super(senderId, receiverId, transactionId, details, challenge);
    }
}
