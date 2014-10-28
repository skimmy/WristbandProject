package it.logostech.wristbandproject.app.model.payment;

import it.logostech.wristbandproject.app.model.TagModel;
import it.logostech.wristbandproject.app.model.payment.exceptions.PaymentProtocolException;
import it.logostech.wristbandproject.app.model.payment.protocol.IdentityMessage;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentMessageBase;

/**
 * Created by Michele Schimd on 03/09/2014.
 *
 * @version  1.1
 */
public class PaymentProtocolGate extends PaymentProtocolBase {

    private String id;
    private String authId;
    private String remoteId;

    public PaymentProtocolGate(String id, String authId) {
        this.state = STATE_IDLE;
        this.id = id;
        this.authId = authId;
        this.remoteId = null;
    }

    @Override
    public void onMessageReceived(PaymentMessageBase message) {
        // STEP 1 - "onIdentityMessage"
        if (message instanceof IdentityMessage) {
            this.onIdentityMessage((IdentityMessage) message);
        }
    }

    /**
     * This method is called when a new connection is established with a remote
     * NFC device. The "normal" behaviour of this method is to construct a proper
     * {@link it.logostech.wristbandproject.app.model.payment.protocol.IdentityMessage}
     * that will be sent back to the NFC counterpart.
     *
     * This method may encounter some unexpected behaviour (<i>i.e.</i> something
     * breaking the normal behaviour), such events are:
     * <ul>
     *     <li>A new connection for an old transaction is established: this may
     *     happen either because previous connection has been lost or because of
     *     an attack to one of the device.</li>
     *
     *     <li>The current device is (or is supposed to be) involved in a
     *     transaction that is not yet finished and a new (valid) one has been
     *     issued</li>
     *
     *     <li>...</li>
     * </ul>
     * How the device should react on such events is still under definition.
     *
     * This method correspond to Step 0 <i>Tag Discovered </i>on the payment
     * protocol definition document.
     *
     * @param remoteTag the discovered tag
     */
    public IdentityMessage onNewConnection(TagModel remoteTag) {
        IdentityMessage identityMessage = null;
        boolean validConnectionRequest = true;
        // we received a new connection even though we are not in the IDLE state
        if (this.state != STATE_IDLE) {
            // TODO check if the connection was lost and act accordingly
            validConnectionRequest = false;
        }

        // if everything is ok, than we just prepare the proper IdentityMessage
        if (validConnectionRequest) {
            identityMessage = new IdentityMessage(this.id);
            this.state = STATE_CONNECTED;
        } else {
            // TODO define reaction to unexpected events
            this.state = STATE_ERROR;
            throw new PaymentProtocolException(
                    "Unexpected tag discovered " + remoteTag.toString());
        }
        return identityMessage;
    }

    /**
     * This method is called when the <i>Daemon</i> receives an <i>identity
     * message</i> on the NFC channel.
     *
     * @param identityMessage the identity message received
     */
    public void onIdentityMessage(IdentityMessage identityMessage) {
        this.remoteId = identityMessage.getClaimedIdentity();
        this.state = STATE_CONNECTED;
    }

    /**
     * This method is called when a payment is first started (<i>e.g.</i> when
     * the operators confirms a purchase. This event marks the start of the
     * transaction between actual gate and the entity identified with the
     * <code>wearId</code> identifier.
     *
     * This methods receives a payment detail object that is used to create a
     * new one with a generated transaction id and the current gate identifier.
     *
     * This method also creates a payment issued message and inserts it into
     * the private messages map ready to be sent as soon as a tag will be
     * discovered.
     *
     * @param details
     */
    public void onPaymentStarted(PaymentDetails details) {

    }

}
