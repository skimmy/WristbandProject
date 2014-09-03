package it.logostech.wristbandproject.app.model.payment;

import java.security.Key;
import java.util.HashMap;

import it.logostech.wristbandproject.app.model.payment.protocol.PaymentMessageBase;

/**
 * Created by michele.schimd on 03/09/2014.
 */
public abstract class PaymentProtocolBase {

    // this map contains all the transactions that are pendind, the
    // key used to map messages is the actual transaction id. Note
    // that this structure should not be exposed, an it should be
    // filled and emptied using proper methods as response to specific
    // events (e.g. a new payment is issued and the corresponding
    // transaction is started)
    protected HashMap<String, PaymentMessageBase> pendingTransactions
            = new HashMap<String, PaymentMessageBase>();

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
    public abstract void onMessageReceived(PaymentMessageBase message);

    /**
     * Retrieves, but does not remove, the proper message from the internal
     * messages map using the transaction id as key.
     *
     * @param transactionId the id for the transaction to be retrieved
     * @return the message retrieved, <code>null</code> if the given transaction
     * id is not present in the map.
     */
    protected PaymentMessageBase getMessageForTransaction(String transactionId) {
        return this.pendingTransactions.get(transactionId);
    }

    /**
     * Retrieves, and removes, the proper message from the internal
     * messages map using the transaction id as key.
     *
     * @param transactionId the id for the transaction to be retrieved
     * @return the message retrieved, <code>null</code> if the given transaction
     * id is not present in the map.
     */
    protected PaymentMessageBase removeMessageForTransaction(String transactionId) {
        return this.pendingTransactions.remove(transactionId);
    }

    /**
     * Inserts a the message passed as parameter into the pending transactions map.
     * The key used for the mapping is retrieved from
     * {@link it.logostech.wristbandproject.app.model.payment.protocol.PaymentMessageBase#getTransactionId()},
     * if the already exists, the methods throws an {@link java.lang.IllegalArgumentException} and
     * does not insert the message.
     *
     * @param message the message to be inserted
     *
     * @throws java.lang.IllegalArgumentException when the corresponding transaction id is already
     *
     */
    protected void addPendingTransaction(PaymentMessageBase message) {
        String tId = message.getTransactionId();
        if (this.pendingTransactions.containsKey(tId)) {
            throw new IllegalArgumentException("Transaction id already present");
        }
        this.pendingTransactions.put(tId, message);
    }
}
