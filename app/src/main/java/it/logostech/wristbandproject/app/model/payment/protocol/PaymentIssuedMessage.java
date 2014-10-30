package it.logostech.wristbandproject.app.model.payment.protocol;

import java.util.Arrays;

import it.logostech.wristbandproject.app.model.payment.PaymentDetails;

/**
 * This message is sent by the <b>gate</b> to <b>wear</b> throughout the NFC
 * channel to start a <i>payment transaction</i>, as of this point the two devices
 * have already exchanged their identities.
 *
 * <p />
 * Created by Michele Schimd on 02/09/2014.
 *
 * @version 1.0
 */
public class PaymentIssuedMessage extends PaymentMessageBase {

    /** Low level NFC operation code, 'P' is for Payment */
    public static final byte OP_CODE = (byte)'P';

    public static PaymentIssuedMessage fromPaymentDetails(PaymentDetails details) {
        return new PaymentIssuedMessage(details);
    }

    public static PaymentIssuedMessage fromBytes(byte[] bytes) {
        // TODO: insert check for the first byte
        return PaymentIssuedMessage.fromPaymentDetails(
                PaymentDetails.fromBytes(Arrays.copyOfRange(bytes, 1, bytes.length)));
    }

    private PaymentDetails paymentDetails;

    public PaymentIssuedMessage(PaymentDetails details) {
        super(details.getGateId(), details.getWearId(), details.getTransactionId());
        // TODO: clone to avoid reference clashes and unexpected behaviour
        this.paymentDetails = details;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    @Override
    public int getChannel() {
        return PaymentMessageBase.NFC_CHANNEL;
    }

    @Override
    public byte[] toByteArray() {
        byte[] rawDetails = this.paymentDetails.toBytes();
        int n = 1 + rawDetails.length;
        byte[] bytes = new byte[n];
        bytes[0] = PaymentIssuedMessage.OP_CODE;
        System.arraycopy(rawDetails, 0, bytes, 1, rawDetails.length);
        return bytes;
    }
}
