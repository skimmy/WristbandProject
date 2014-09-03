package it.logostech.wristbandproject.app.model.payment.protocol;

import it.logostech.wristbandproject.app.model.payment.PaymentChallenge;
import it.logostech.wristbandproject.app.model.payment.PaymentDetails;

/**
 * Created by Michele Schimd on 03/09/2014.
 * <p/>
 * This class contains helper methods to create payment messages
 */
public class PaymentMessageFactory {

    public static PaymentIssuedMessage createPaymentIssuedMessage(
            String sender, String receiver, String tId, PaymentDetails details) {
        return new PaymentIssuedMessage(sender, receiver, tId, details);
    }

    public static PaymentRequestCustomer createPaymentRequestCustomerMessage(
            PaymentIssuedMessage issuedMessage, String sender, String receiver) {
        if (issuedMessage == null || sender == null || receiver == null) {
            throw new IllegalArgumentException();
        }
        String senderId = sender;
        String receiverId = receiver;
        // WARNING: getting transaction id from the issue message may be used
        // to attack the system
        String transactionId = issuedMessage.getTransactionId();
        PaymentDetails paymentDetails = issuedMessage.getPaymentDetails();
        return new PaymentRequestCustomer(senderId, receiverId, transactionId, paymentDetails);
    }

    public static PaymentRequestCustomer createPaymentRequestCustomerMessage(
            String sender, String receiver, PaymentDetails details) {
        if (details == null || sender == null || receiver == null) {
            throw new IllegalArgumentException();
        }
        // WARNING: getting transaction id from the payment details may be used
        // to attack the system
        return new PaymentRequestCustomer(sender, receiver, details.getTransactionId(), details);
    }

    public static PaymentAuthorizationCustomer createPaymentAuthorizationCustomerMessage(
            String sender, PaymentRequestCustomer request, PaymentChallenge challenge) {
        if (request == null || challenge == null || sender == null) {
            throw new IllegalArgumentException();
        }
        return new PaymentAuthorizationCustomer(sender, request.getSenderId(),
                request.getTransactionId(), request.getPaymentDetails(), challenge);
    }

    public static PaymentAuthorizationMerchant createPaymentAuthorizationMerchantMessage(
            String sender, PaymentRequestMerchant request, PaymentChallenge challenge) {
        if (sender == null || request == null || challenge == null) {
            throw new IllegalArgumentException();
        }
        return new PaymentAuthorizationMerchant(sender, request.getSenderId(),
                request.getTransactionId(), request.getPaymentDetails(), challenge);
    }

    public static PaymentOkCustomer createPaymentOkCustomer() {
        return null;
    }

    public static PaymentOkMerchant createPaymentOkMerchant() {
        return null;
    }

    public static TransitionConfirmedCustomer createTransitionConfirmedCustomer() {
        return null;
    }

    public static TransitionConfirmedMerchant createTransitionConfirmedMerchant() {
        return null;
    }

    public static TransitionSuccessCustomer createTransitionSuccessCustomer() {
        return null;
    }

    public static TransitionSuccessMerchant createTransitionSuccessMerchant() {
        return null;
    }

    public static PaymentPerformed createPaymentPerformedMessage() {
        return null;
    }
}

