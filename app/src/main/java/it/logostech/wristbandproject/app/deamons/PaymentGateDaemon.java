package it.logostech.wristbandproject.app.deamons;

import android.nfc.tech.IsoDep;

import java.io.IOException;

import it.logostech.wristbandproject.app.model.TagModel;
import it.logostech.wristbandproject.app.model.payment.PaymentDetails;
import it.logostech.wristbandproject.app.model.payment.PaymentModelUtil;
import it.logostech.wristbandproject.app.model.payment.PaymentProtocolGate;
import it.logostech.wristbandproject.app.model.payment.protocol.IdentityMessage;
import it.logostech.wristbandproject.app.util.TypeUtil;

/**
 * This class should be used as a daemon
 * <p/>
 * Created by Michele Schimd on 22/09/2014.
 *
 * @version 1.1
 */
public class PaymentGateDaemon extends PaymentDaemonBase {

    private PaymentGateDaemon() {

    }

    // singleton instance
    public static final PaymentGateDaemon GATE_DAEOMN = new PaymentGateDaemon();

    // this is the model of the currently connected tag "device"
    private static TagModel currentTag = null;

    // the payment protocol associated with the current tag
    private static PaymentProtocolGate payProtocol = null;

    /**
     * This method is called when a tag is discovered (it can be either a new
     * one, or one already known). Since the communication on NFC channels are
     * usually <i>half duplex</i>, this method automatically ignores a tag when
     * there is one already in process. The method also indicated whether the
     * passed tag has been ignored (return value <code>false</code>) or it has
     * been set as the new current tag (return value <code>false</code>).
     *
     * @param tag    the discovered tag
     * @param isoDep
     * @return <code>true</code> if the passed tag is the new current tag and
     * <code>false</code> otherwise
     */
    public boolean tagDiscovered(TagModel tag, IsoDep isoDep) {
        // Check if the Id corresponds with the id that is actually in an active
        // NFC communication (if any).
        if (currentTag != null && currentTag.equals(tag)) {
            // communication continues...
            // it is hard to decide what to do here!!
        } else {
            // new tag...
            if (currentTag == null) {
                currentTag = tag;
                payProtocol = new PaymentProtocolGate(PaymentDaemonBase.deviceNfcId, null);
                simpleNfcCommunication(tag, isoDep);
                // ...mismatch
            } else {
                // there already exist a tag so we discard the current one but
                // before we close the Iso-Dep connection to the discarded tag
                try {
                    isoDep.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }
        return true;
    }

    /**
     * Resets the current tag if and only if the actual current tag has the same
     * id as the passed one.
     *
     * @param tag the tag to be reset
     * @return <code>true</code> if and only if the current tag has been reset
     */
    public boolean discardTag(TagModel tag) {
        if (currentTag.equals(tag)) {
            currentTag = null;
            return true;
        }
        return false;
    }

    /**
     * Performs a NFC communication payment protocol with the tag described by
     * the passed parameter.
     *
     * @param tagModel the tag identifying the NFC counterpart
     */
    private void simpleNfcCommunication(TagModel tagModel, IsoDep isoDep) {
        // We have here a connected Iso-Dep channel

        // 1. We send our identity on the channel
        IdentityMessage identityMessage = payProtocol.onNewConnection(tagModel);
        sendIdentity(identityMessage, isoDep);

        // prepare the transaction
        String transactionId = TypeUtil.byteArrayToHexString(
                PaymentModelUtil.newTransactionId(12345L));
        String wearId = TypeUtil.byteArrayToHexString(tagModel.getId());
        String gateId = PaymentGateDaemon.deviceNfcId;
        double amount = 99.99;
        int type = PaymentDetails.PURCHASE_TYPE_GENERIC;
        PaymentDetails details = PaymentDetails.fromProperties(
                transactionId, gateId, wearId, amount, type);

    }
}
