package it.logostech.wristbandproject.app.model.payment;

import it.logostech.wristbandproject.app.model.payment.exceptions.PaymentProtocolMessageException;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentAuthorizationCustomer;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentIssuedMessage;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentMessageBase;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentMessageFactory;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentOkCustomer;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentOkMerchant;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentPerformed;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentRequestCustomer;
import it.logostech.wristbandproject.app.model.payment.protocol.TransitionConfirmedCustomer;
import it.logostech.wristbandproject.app.model.payment.protocol.TransitionSuccessCustomer;

/**
 * Created by Michele Schimd on 03/09/2014.
 *
 * This class contains the protocol implemented by the wear entity during the
 * contactless payment procedures.
 */
public class PaymentProtocolWear extends PaymentProtocolBase {

    /**
     * {@link #getStatus()} return value when no transaction is pending
     */
    public static final int WEAR_STATE_IDLE = 0;
    /**
     * {@link #getStatus()} return value when a payment has been issued
     */
    public static final int WEAR_STATE_PAYMENT_ISSUED = 1;
    /**
     * {@link #getStatus()} return value when a payment has been authorized
     */
    public static final int WEAR_STATE_PAYMENT_AUTHORIZED = 2;
    /**
     * {@link #getStatus()} return value when a payment has been confirmed
     */
    public static final int WEAR_STATE_PAYMENT_OK = 3;
    /**
     * {@link #getStatus()} return value when a transaction has been successfully
     * concluded and it is performing last operations before returning idle
     */
    public static final int WEAR_STATE_TRANSITION_SUCCESS = 4;

    private int status;
    private String id;
    // TODO: consider substituting with an actual Auth class
    private String authId;

    public PaymentProtocolWear(String id, String authId) {
        this.id = id;
        this.authId = authId;
        this.status = WEAR_STATE_IDLE;
    }

    @Override
    public void onMessageReceived(PaymentMessageBase message) {
        try {
            if (message instanceof PaymentIssuedMessage) {
                this.onPaymentIssuedMessage((PaymentIssuedMessage) message);
            }
            if (message instanceof PaymentAuthorizationCustomer) {
                this.onPaymentAuthorizedMessage((PaymentAuthorizationCustomer) message);
            }
            if (message instanceof PaymentOkMerchant) {
                this.onPaymentOkMessage((PaymentOkMerchant) message);
            }
            if (message instanceof TransitionConfirmedCustomer) {
                this.onTransitionSuccessMessage((TransitionSuccessCustomer) message);
            }
            if (message instanceof PaymentPerformed) {
                // TODO: this is an optional message nothing to do right now
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onPaymentIssuedMessage(PaymentIssuedMessage message) {
        this.addPendingTransaction(message);
        this.status = WEAR_STATE_PAYMENT_ISSUED;
        PaymentRequestCustomer paymentRequest =
                PaymentMessageFactory.createPaymentRequestCustomerMessage(
                        this.id, this.authId, message);
        this.sendMessageToAuth(paymentRequest);
    }

    public void onPaymentAuthorizedMessage(PaymentAuthorizationCustomer message) throws PaymentProtocolMessageException {
        String transactionId = message.getTransactionId();
        // this happens if the message is for a transaction not locally stored. It may
        // indicate either that previous message(s) has been lost and or that an attack
        // is happening (e.g. forging messages with fictitious transaction)
        if (!this.pendingTransactions.containsKey(transactionId)) {
            throw new PaymentProtocolMessageException("Wear transaction not found");
        }
        this.status = WEAR_STATE_PAYMENT_AUTHORIZED;
        PaymentOkCustomer paymentOk = PaymentMessageFactory.createPaymentOkCustomer(
                this.id, message.getPaymentDetails().getGateId(), message.getPaymentDetails(),
                message.getPaymentChallenge());
        this.sendMessgaeToGate(paymentOk);
    }

    public void onPaymentOkMessage(PaymentOkMerchant paymentOk) {
        this.status = WEAR_STATE_PAYMENT_OK;
        PaymentChallenge challenge = paymentOk.getChallenge();
        PaymentChallengeResolved solvedChallenge = this.solveChallenge(challenge);
        TransitionConfirmedCustomer transitionConfirmed =
                PaymentMessageFactory.createTransitionConfirmedCustomer(this.id,
                this.authId, paymentOk.getPaymentDetails(), solvedChallenge);
        this.sendMessageToAuth(transitionConfirmed);
    }

    public void onTransitionSuccessMessage(TransitionSuccessCustomer message) throws PaymentProtocolMessageException {
        // temporary state while cleaning operations are performed
        this.status = WEAR_STATE_TRANSITION_SUCCESS;
        String transactionId = message.getTransactionId();
        PaymentMessageBase pendingPayment = this.pendingTransactions.get(transactionId);
        if (pendingPayment == null) {
            throw new PaymentProtocolMessageException("Wear transaction not found");
        }
        // TODO: add here the code to notify user of transaction completed
        // now that the transition is done we can remove it from the pending map
        this.pendingTransactions.remove(transactionId);
        // the transaction is finally complete and wear can back to idle state
        this.status = WEAR_STATE_IDLE;
    }

    private void sendMessageToAuth(PaymentMessageBase message) {
        // TODO: add actual communication to AUTH code as soon as it is available
    }

    private void sendMessgaeToGate(PaymentMessageBase messageBase) {
        // TODO: add actual communication to GATE code as soon as it is available
    }

    private PaymentChallengeResolved solveChallenge(PaymentChallenge challenge) {
        // TODO: implement challenge resolved (define challenge before)
        return null;
    }

    /**
     * Returns the status
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }
}
