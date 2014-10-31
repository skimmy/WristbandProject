package it.logostech.wristbandproject.app.deamons;

import android.nfc.tech.IsoDep;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;

import it.logostech.wristbandproject.app.model.TagModel;
import it.logostech.wristbandproject.app.model.payment.PaymentDetails;
import it.logostech.wristbandproject.app.model.payment.PaymentModelUtil;
import it.logostech.wristbandproject.app.model.payment.PaymentProtocolGate;
import it.logostech.wristbandproject.app.model.payment.protocol.IdentityMessage;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentIssuedMessage;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentRequestMerchant;
import it.logostech.wristbandproject.app.nfc.NfcSession;
import it.logostech.wristbandproject.app.util.TypeUtil;

/**
 * This is the <b>Gate</b> <i>daemon</i> used to perform payment when the device
 * is operating in <i>payment gate mode</i>.
 *
 * @author Michele Schimd
 * @version 1.1
 * @since 22/09/2014.
 *
 */
public class PaymentGateDaemon extends PaymentDaemonBase {

    public static final String TAG = PaymentGateDaemon.class.getSimpleName();

    // singleton instance
    public static final PaymentGateDaemon GATE_DAEMON = new PaymentGateDaemon();
    private PaymentGateDaemon() { }


    // TODO decide whether session and protocol should be merged
    private NfcSession currentSession;
    private PaymentProtocolGate payProtocol = null;

    public PaymentDetails getPayDetails() {
        return payDetails;
    }

    public void setPayDetails(PaymentDetails payDetails) {
        this.payDetails = payDetails;
    }

    private PaymentDetails payDetails = null;

    /**
     * This method is called when a tag is discovered (it can be either a new
     * one, or one already known). Since the communication on NFC channels are
     * usually <i>half duplex</i>, this method automatically ignores a tag when
     * there is one already in process. The method also indicates whether the
     * passed tag has been ignored (return value <code>false</code>) or it has
     * been set as the new current tag (return value <code>false</code>).
     *
     * @param tag    the discovered tag
     * @param isoDep
     * @return <code>true</code> if the passed tag is the new current tag and
     * <code>false</code> otherwise
     */
    public boolean tagDiscovered(TagModel tag, IsoDep isoDep) {
        // Check if the Id corresponds with the one on the current session
        if (currentSession != null && currentSession.getTagModel().equals(tag)) {
            // refresh the timestamp of the current session and continue communication
            currentSession.touch();
            // ...
        } else {
            // new tag...
            if (currentSession == null) {
                currentSession = NfcSession.createSessionFor(tag);
                payProtocol = new PaymentProtocolGate(PaymentDaemonBase.deviceNfcId, null);
                try {
                    simpleNfcCommunication(tag, isoDep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
     * Performs a NFC communication payment protocol with the tag described by
     * the passed parameter.
     *
     * @param tagModel the tag identifying the NFC counterpart
     */
    private void simpleNfcCommunication(TagModel tagModel, IsoDep isoDep) throws IOException {
        // We have here a connected Iso-Dep channel

        // Step 0 -  send our identity on the channel
        IdentityMessage identityMessage = payProtocol.onNewConnection(tagModel);
        byte[] identityReplyRaw = isoDep.transceive(identityMessage.toByteArray());
        if (identityReplyRaw[0] != IdentityMessage.OP_CODE) {
            Log.e(TAG, "Expected identity reply");
            // TODO something went wrong, act accordingly
        }
        else {
            Log.v(TAG, "Received remote identity: " + new String(
                    Arrays.copyOfRange(identityReplyRaw, 1, identityReplyRaw.length)));
            // construct IdentityMessage and call protocol onIdentity...
            this.payProtocol.onIdentityMessage(IdentityMessage.fromBytes(identityReplyRaw));
        }

        // STEP 1 - send paymentIssue to WEAR and send paymentRequest to AUTH
        // construct and send PaymentIssued message through the NFC channel
        if (this.payDetails == null) {
            // TODO details 'null' is an error either bring up a form or abort transaction
        } else {
            // send the PaymentIssued to WEAR
            byte[] rawMessage = PaymentIssuedMessage.
                    fromPaymentDetails(this.payDetails).toByteArray();
            isoDep.transceive(rawMessage);
            // send the PaymentRequestMerchant to AUTH
            PaymentAuthDaemon.AUTH_DAEMON.sendMessage(
                    PaymentRequestMerchant.fromPaymentDetailsAndIds(this.payDetails,
                            PaymentDaemonBase.deviceNfcId, PaymentAuthDaemon.AUTH_ID));
        }

    }

    public void reset() {
        // this is just a brut-force reset
        this.currentSession = null;
//        this.payDetails = null;
        this.payProtocol = null;
    }
}
