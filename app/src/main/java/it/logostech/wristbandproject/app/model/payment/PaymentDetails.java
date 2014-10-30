package it.logostech.wristbandproject.app.model.payment;

import com.appspot.wristband_unipd.paymentremote.model.PaymentMessagesPaymentDetailMessage;

import java.util.Arrays;

import it.logostech.wristbandproject.app.util.TypeUtil;

/**
 *  This class represents the information (<em>i.e.</em> details) about a payment
 *  transaction.
 *
 * @version 1.1
 * Created by Michele Schimd on 02/09/2014.
 */
public class PaymentDetails {

    /** Constant for a generic purchase */
    public static final int PURCHASE_TYPE_GENERIC = 0;
    /** Constant for food and similar purchases */
    public static final int PURCHASE_TYPE_FOOD = 1;
    /** Constant for <em>services</em> expenses (<em>e.g.</em> car rend, hot tab) */
    public static final int PURCHASE_TYPE_SERVICE = 2;

    /**
     * Factory method to clone an instance of {@link it.logostech.wristbandproject.app.model.payment.PaymentDetails}.
     *
     * @param other the instance to be copied
     * @return a new {@link it.logostech.wristbandproject.app.model.payment.PaymentDetails} instance clone from the passed one.
     */
    public static PaymentDetails fromPaymentDetail(PaymentDetails other) {
        Builder builder = new Builder(other.transactionId, other.wearId, other.gateId);
        builder.amount(other.amount)
                .purchaseType(other.purchaseType);
        return builder.build();
    }

    /**
     * Constructs a {@link it.logostech.wristbandproject.app.model.payment.PaymentDetails}
     * objects from its byte representation as returned by the method {@link #toBytes()}.
     *
     * If the input array is not on the format prescribed, the behaviour of this method is
     * unpredictable (although it is likely to throw some exception indicating either bad access
     * to arrays or bad conversion).
     *
     * @param bytes the byte representation
     * @return a {@link it.logostech.wristbandproject.app.model.payment.PaymentDetails} instance
     * obtained by parsing the argument bytes array
     */
    public static PaymentDetails fromBytes(byte[] bytes) {
        // parsing
        int offset = 0;
        String tid = new String(Arrays.copyOfRange(bytes, offset + 1, (offset + 1) + bytes[offset]));
        offset += 1 + bytes[offset];
        String gid = new String(Arrays.copyOfRange(bytes, offset + 1, (offset + 1) + bytes[offset]));
        offset += 1 + bytes[offset];
        String wid = new String(Arrays.copyOfRange(bytes, offset + 1, (offset + 1) + bytes[offset]));
        offset += 1 + bytes[offset];
        double amount = TypeUtil.bytesToDouble(Arrays.copyOfRange(bytes, offset, offset + 8));
        offset += 8;
        int type = (int)TypeUtil.bytesToLong(Arrays.copyOfRange(bytes, offset, offset + 8));

        // build and return
        return PaymentDetails.fromProperties(tid, gid, wid, amount, type);
    }

    /**
     * Factory method to create a {@link it.logostech.wristbandproject.app.model.payment.PaymentDetails}
     * instance from a {@link com.appspot.wristband_unipd.paymentremote.model.PaymentMessagesPaymentDetailMessage}
     * (from the remote service auto-generated API).
     * <p>
     *     <b>Note.</b> It is not a good practice ti mix the remote service auto-generated
     *     classes with the model not only because this breaks the design pattern, but also
     *     because every time the remote API changes, there is the possibility for the
     *     corresponding class to change as well (which will, in turn, force changes all over
     *     the code). For prototyping reasons this <i>anti-pattern</i> is here accepted,
     *     but it shouldn't be used in actual release code.
     * </p>
     *
     * @param msg the web server message
     * @return a payment detail from the passed passed message
     */
    public static PaymentDetails fromPaymentDetailMessage(PaymentMessagesPaymentDetailMessage msg) {
        Builder builder = new Builder(msg.getTransactionId(), msg.getWearId(), msg.getGateId());
        builder.amount(msg.getAmount()).purchaseType(msg.getPurchaseType().intValue());
        return builder.build();
    }

    public static PaymentDetails fromProperties(String tId, String gId, String wId,
                                                double amnt, int type) {
        PaymentDetails.Builder builder = new Builder(tId,wId,gId);
        builder.amount(amnt).purchaseType(type);
        return builder.build();

    }

    /**
     * Builder class for {@link it.logostech.wristbandproject.app.model.payment.PaymentDetails},
     * following the <em>Effective Java</em> patterns (see Item 2).
     *
     * @version 1.0
     */
    public static class Builder {

        private final String trans;
        private final String wear;
        private final String gate;

        private double amnt = 0.0;
        private int purch = PURCHASE_TYPE_GENERIC;

        public Builder(String t, String w, String g) {
            this.trans = t;
            this.wear = w;
            this.gate = g;
        }

        public Builder amount(double a) { amnt = a; return this; }
        public Builder purchaseType(int p) { purch = p; return this; }

        public PaymentDetails build() {
            return new PaymentDetails(this);
        }
    }

    private String gateId;
    private String wearId;
    private String transactionId;

    private double amount;
    private int purchaseType;

    public PaymentDetails(Builder builder) {
        this.gateId = builder.gate;
        this.wearId = builder.wear;
        this.transactionId = builder.trans;
        this.amount = builder.amnt;
        this.purchaseType = builder.purch;
    }

    public String getGateId() {
        return gateId;
    }

    public String getWearId() {
        return wearId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public int getPurchaseType() {
        return purchaseType;
    }

    public void setGateId(String gateId) {
        this.gateId = gateId;
    }

    public void setWearId(String wearId) {
        this.wearId = wearId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s, %s] <-> %.2f (%d)",
                transactionId, gateId, wearId, amount, purchaseType);
    }

    /**
     * Returns a byte array describing the details. The format of the array is <br />
     * <code>[ TIL | TID | GIL | GID | WIL | WID | AMOUNT | TYPE]</code> <br />
     * where:
     * <ol>
     *     <li><b>xIL</b> is the length in bytes of the xID</li>
     *     <li><b>xID</b> is the ID for x (either T: transaction, G: gate or W: wear)/li>
     *     <li><b>AMOUNT</b> is the 8 bytes double value of the amount</li>
     *     <li><b>TYPE</b> is the 8 bytes long value of the purchase type</li>
     * </ol>
     *
     * @return a byte array representation of details
     */
    public byte[] toBytes() {
        int n = 0;
        byte[] rawTid = this.transactionId.getBytes();
        byte[] rawGid = this.gateId.getBytes();
        byte[] rawWid = this.wearId.getBytes();
        byte[] rawAmount = TypeUtil.doubleToBytes(this.amount);
        byte[] rawType = TypeUtil.longToBytes(this.purchaseType);

        // total length, string sizes are forced to be one byte (max id size is 64 bytes)
        n += 1 + rawTid.length;
        n += 1 + rawGid.length;
        n += 1 + rawWid.length;
        n += rawAmount.length;
        n += rawType.length;

        // create the array, fill and return it
        int offset = 0;
        byte tmp;
        byte[] bytes = new byte[n];
        // transaction ID
        tmp = (byte)rawTid.length;
        bytes[offset++] = tmp;
        System.arraycopy(rawTid, 0, bytes, offset, tmp);
        offset += tmp;
        // Gate ID
        bytes[offset++] = (byte)rawGid.length;
        tmp = (byte) rawGid.length;
        System.arraycopy(rawGid, 0, bytes, offset, tmp);
        offset += tmp;
        // Wear ID
        tmp = (byte)rawWid.length;
        bytes[offset++] = tmp;
        System.arraycopy(rawWid, 0, bytes, offset, tmp);
        offset += tmp;
        // Amount and type
        tmp = (byte)rawAmount.length;
        System.arraycopy(rawAmount, 0, bytes, offset, rawAmount.length);
        offset += tmp;
        tmp = (byte)rawType.length;
        System.arraycopy(rawType, 0, bytes, offset, tmp);
        return bytes;
    }
}
