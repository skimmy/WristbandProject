package it.logostech.wristbandproject.app.model.payment;

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
