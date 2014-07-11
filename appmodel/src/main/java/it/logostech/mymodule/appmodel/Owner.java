package it.logostech.mymodule.appmodel;

import java.util.List;

import it.logostech.mymodule.appmodel.BaseAccount;
import it.logostech.mymodule.appmodel.ModelBase;

/**
 * Created by michele.schimd on 26/06/2014.
 */
public class Owner extends ModelBase {
    private String name;
    private int age;
    private BaseAccount account = null;
    private BaseProfile profile = null;

    private Object emergencyInfo = null;


    public Owner() {
        super();
    }

    public Owner(String name, int age, BaseProfile profile, BaseAccount account) {
        super();
        this.name = name;
        this.age = age;
        this.account = account;
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void issuePayemntRequest(PaymentGateway gw, double amount) {
        PaymentRequest request = new PaymentRequest(this, gw, PurchaseTypes.FOOD_PURCHASE, amount);

    }

}
