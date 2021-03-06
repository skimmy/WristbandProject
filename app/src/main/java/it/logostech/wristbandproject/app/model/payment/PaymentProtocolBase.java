package it.logostech.wristbandproject.app.model.payment;

import java.util.HashMap;

import it.logostech.wristbandproject.app.model.payment.interfaces.PaymentProtocol;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentMessageBase;

/**
 * This class is an abstract class for the <i>payment protocol</i> finite state
 * machine as implemented by the devices (<i.>i.e.</i.> not by the backend).
 *
 * The actual version of the payment protocol unifies the behaviour of both
 * <b>gate</b> and <b>wear</b> so that they go through the same set of states
 * during the execution of a single payment transaction. This allowed us to move
 * the state at this common superclass level. It should however be noted that
 * the actual advances on the different states depend on the subclasses as they
 * receive messages specific for the device (<i>i.e.</i> either <b>gate</b>  or
 * <b>wear</b>) and therefore, they act accordingly.
 *
 * Created by Michele Schimd on 03/09/2014.
 *
 * @version 1.1
 */
public abstract class PaymentProtocolBase implements PaymentProtocol {

    /** Device is ready to accept connection */
    protected final int STATE_IDLE = 0;
    /** Device is connected to another one  */
    protected final int STATE_CONNECTED = 10;
    /** The <i>payment request message</i> has been sent to <b>AUTH</b> */
    protected final int STATE_REQUESTED = 11;
    /** The payment has been authorized by <b>AUTH</b> */
    protected final int STATE_AUTHORIZED = 20;
    /** The <i>payment ok message</i> has been sent to the counterpart */
    protected final int STATE_PENDING = 21;
    /** Transaction has been confirmed on committed */
    protected final int STATE_CONFIRMED = 30;
    /** Something went wrong */
    protected final int STATE_ERROR = 99;

    protected int state = STATE_IDLE;

    // this map contains all the transactions that are pendind, the
    // key used to map messages is the actual transaction id. Note
    // that this structure should not be exposed, an it should be
    // filled and emptied using proper methods as response to specific
    // events (e.g. a new payment is issued and the corresponding
    // transaction is started)
    protected HashMap<String, PaymentMessageBase> pendingTransactions
            = new HashMap<String, PaymentMessageBase>();

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
