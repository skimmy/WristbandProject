package it.logostech.wristbandproject.app.model.payment;

/**
 * Created by michele.schimd on 02/09/2014.
 */
public class PaymentDetails {
    private String gateId;
    private String wearId;
    private String transactionId;

    private double amount;
    private int purchaseType;

    public PaymentDetails(double amount, int purchaseType) {
        this.amount = amount;
        this.purchaseType = purchaseType;

        this.gateId = null;
        this.wearId = null;
        this.transactionId = null;
    }


    public PaymentDetails(String gateId, String wearId, String transactionId, double amount, int purchaseType) {
        this.gateId = gateId;
        this.wearId = wearId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.purchaseType = purchaseType;
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
