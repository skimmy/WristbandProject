package it.logostech.mymodule.appmodel;

/**
 * Created by michele.schimd on 26/06/2014.
 */
public class BaseAccount {

    // if null this is a masterAccount
    private BaseAccount masterAccount = null;

    private double balance;
    private double maxExpenditure;

    public BaseAccount(double balance, double maxExpenditure) {
        this.balance = balance;
        this.maxExpenditure = maxExpenditure;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getMaxExpenditure() {
        return maxExpenditure;
    }

    public void setMaxExpenditure(double maxExpenditure) {
        this.maxExpenditure = maxExpenditure;
    }
}
