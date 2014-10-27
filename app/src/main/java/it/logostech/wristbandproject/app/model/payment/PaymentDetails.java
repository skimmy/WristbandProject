package it.logostech.wristbandproject.app.model.payment;

import com.appspot.wristband_unipd.paymentremote.model.PaymentMessagesPaymentDetailMessage;

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
}
