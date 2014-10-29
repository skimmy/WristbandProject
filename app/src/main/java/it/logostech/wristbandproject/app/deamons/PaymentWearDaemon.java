package it.logostech.wristbandproject.app.deamons;

/**
 * Created by Michele Schimd on 10/27/14.
 *
 * @version 1.1
 */
public class PaymentWearDaemon extends PaymentDaemonBase {

    // Singleton enforced via private constructor and static final instance
    public static final PaymentWearDaemon WEAR_DAEOMN = new PaymentWearDaemon();

    private PaymentWearDaemon() {

    }
}
