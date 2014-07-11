package it.logostech.mymodule.appmodel;

/**
 * Created by michele.schimd on 26/06/2014.
 */
public class PaymentRequest {
    // unique identifier for the whole transaxtion
    private long transactionId;

    // owner that initiated the request
    private Owner requester = null;
    // describes the type of purchase and the gateway
    private PaymentGateway paymentGateway = null;
    private int type;
    // cash amount of the purchase
    private double amount;

    public PaymentRequest(Owner requester, PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
        this.requester = requester;
        this.transactionId = Util.getRandomId();
    }

    public PaymentRequest(Owner requester, PaymentGateway paymentGateway, int type, double amount) {
        this.transactionId = Util.getRandomId();
        this.type = type;
        this.amount = amount;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
