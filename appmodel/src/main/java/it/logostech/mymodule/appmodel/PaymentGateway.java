package it.logostech.mymodule.appmodel;

/**
 * Created by michele.schimd on 26/06/2014.
 */
public class PaymentGateway extends Gateway {
    private long id;


    public PaymentGateway(long id, int type, float[] position) {
        super(id, type, position);
    }
}
