package it.logostech.wristbandproject.app.deamons;

import android.nfc.tech.IsoDep;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;

import it.logostech.wristbandproject.app.model.NfcConnection;
import it.logostech.wristbandproject.app.model.TagModel;
import it.logostech.wristbandproject.app.model.payment.PaymentDetails;
import it.logostech.wristbandproject.app.model.payment.PaymentModelUtil;
import it.logostech.wristbandproject.app.model.payment.PaymentProtocolGate;
import it.logostech.wristbandproject.app.model.payment.protocol.IdentityMessage;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentIssuedMessage;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentMessageBase;
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

    // Nfc channel thread
    Thread nfcChannelThread = null;

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
        // We cannot relies on the session mechanisms when using NFC connection because:
        // 1. The actual Tag ID may change even for the same device
        // 2. The higher level ID (i.e. the one used in the backend) can be forged by attacker
        // As a consequence each time a tag is discovered, a new authentication procedure
        // is needed.

        // Reset old data and create new session, protocol and channel
        this.reset();
        currentSession = NfcSession.createSessionFor(tag);
        payProtocol = new PaymentProtocolGate(PaymentDaemonBase.deviceNfcId, null);
        this.nfcChannelThread = new Thread(NfcConnection.NFC_CONNECTION, NfcConnection.TAG);
        this.nfcChannelThread.start();
        NfcConnection.NFC_CONNECTION.setIsoDepConnection(isoDep);
        NfcConnection.NFC_CONNECTION.setCallbackDaemon(this);
        // authentication
        IdentityMessage identityMessage = payProtocol.onNewConnection(tag);
        NfcConnection.NFC_CONNECTION.setCommand(identityMessage.toByteArray());

        return true;
    }

    /**
     * Performs a NFC communication payment protocol with the tag described by
     * the passed parameter.
     *
     * @param tagModel the tag identifying the NFC counterpart
     */
    private void simpleNfcCommunication(TagModel tagModel, IsoDep isoDep) throws IOException {

        // STEP 1 - send paymentIssue to WEAR and send paymentRequest to AUTH
        // construct and send PaymentIssued message through the NFC channel
        if (this.payDetails == null) {

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
        this.payProtocol = null;
        if (this.nfcChannelThread != null) {
            this.nfcChannelThread.interrupt();
            this.nfcChannelThread = null;
            NfcConnection.NFC_CONNECTION.reset();
        }
    }

    @Override
    protected void processMessage(PaymentMessageBase message) {
        // here the messages are processed according to the current state of the
        // payment protocol

        // Identity Message:
        if (message instanceof IdentityMessage) {
            if (payDetails == null) {
                // TODO details 'null' is an error either bring up a form or abort transaction
            } else {
                // send payment issued to wear
                byte[] paymentIssueCommand = PaymentIssuedMessage.
                        fromPaymentDetails(payDetails).toByteArray();
                NfcConnection.NFC_CONNECTION.setCommand(paymentIssueCommand);

            }
        }

        // Default case: do nothing

    }

}
