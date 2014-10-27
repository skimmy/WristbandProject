package it.logostech.wristbandproject.app.model.payment.protocol;

import it.logostech.wristbandproject.app.model.payment.PaymentModelUtil;
import it.logostech.wristbandproject.app.util.TypeUtil;

/**
 * This message is used to exchange devices' identity at the beginning of a NFC
 * communication.
 *
 * Created by Michele Schimd on 10/27/14.
 *
 * @version 1.0
 */
public class IdentityMessage extends PaymentMessageBase {

    public static final byte OP_CODE = 101;

    public IdentityMessage(String senderId) {
        super(senderId, null, null);
    }

    @Override
    public int getChannel() {
        return PaymentMessageBase.NFC_CHANNEL;
    }

    @Override
    public byte[] toByteArray() {
        // size: size(OP_CODE) + size(ID)
        int n = 1 + PaymentModelUtil.DEVICE_ID_MAXIMUM_SIZE;
        byte[] bytes = new byte[n];
        bytes[0] = OP_CODE;
        TypeUtil.stringToPaddedBytes(this.getSenderId(), PaymentModelUtil.DEVICE_ID_MAXIMUM_SIZE);
        return bytes;
    }


}
