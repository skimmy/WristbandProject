package it.logostech.wristbandproject.app.model.payment.protocol;

/**
 * @author Michele Schimd
 * @version 1.0
 * @since 02/09/2014.
 */
public class PaymentPerformed extends PaymentMessageBase {

    public static final byte OP_CODE = (byte)'C';

    public PaymentPerformed(String senderId, String receiverId, String transactionId) {
        super(senderId, receiverId, transactionId);
    }

    @Override
    public int getChannel() {
        return NFC_CHANNEL;
    }

    @Override
    public byte[] toByteArray() {
        return new byte[0];
    }
}
