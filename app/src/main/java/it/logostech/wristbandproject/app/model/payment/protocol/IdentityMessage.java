package it.logostech.wristbandproject.app.model.payment.protocol;

import java.util.Arrays;

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

    public static IdentityMessage fromBytes(byte[] bytes) {
        // TODO: insert check of first byte
        return new IdentityMessage(new String(Arrays.copyOfRange(bytes, 1, bytes.length)));
    }

    public static final byte OP_CODE = (byte)'I';

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
        byte[] idBytes = this.getSenderId().getBytes();
        int n = Math.min(PaymentModelUtil.DEVICE_ID_MAXIMUM_SIZE, idBytes.length) + 1;
        // create array with trailing op code and senderId
        byte[] bytes = new byte[n];
        bytes[0] = OP_CODE;
        System.arraycopy(idBytes, 0, bytes, 1, n - 1);
        //TypeUtil.stringToPaddedBytes(this.getSenderId(), PaymentModelUtil.DEVICE_ID_MAXIMUM_SIZE);
        return bytes;
    }

    public String getClaimedIdentity() {
        return this.getSenderId();
    }

}
