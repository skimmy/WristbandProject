package it.logostech.wristbandproject.app.deamons;

import android.nfc.tech.IsoDep;
import android.util.Log;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import it.logostech.wristbandproject.app.model.payment.protocol.IdentityMessage;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentMessageBase;

/**
 * Created by Michele Schimd on 10/27/14.
 *
 * @version 1.1
 */
public abstract class PaymentDaemonBase implements Runnable {

    public static final String TAG = PaymentDaemonBase.class.getSimpleName();

    protected static final long DAEMON_SLEEP_TIME = 500;

    private Queue<PaymentMessageBase> messagesQueue = new ConcurrentLinkedQueue<PaymentMessageBase>();

    /**
     * Protected constructor, the {@link it.logostech.wristbandproject.app.deamons.PaymentDaemonBase}
     * class is not instantiable, moreover the class does not provide any public
     * instance so it is not usable unless derived classes are used.
     *
     */
    protected PaymentDaemonBase() {

    }

    // This is the ID used which should be initialized in the onResume method
    // of the CardEmulatorActivity or CardReaderActivity. Note that because of the Android NFC
    // implementation, this ID (which is supposed to be uniquely associated with
    // the current device) is different from the ID used by the low level NFC
    // hardware (which may change and is not accessible from the APIs).
    public static String deviceNfcId = null;

    public void onMessage(PaymentMessageBase message) {
        Log.v(TAG, "Message: " + message.toString());
        this.messagesQueue.add(message);
    }

    @Override
    public void run() {
        while(true) {
            try {
                while (!this.messagesQueue.isEmpty()) {
                    PaymentMessageBase nextMessage = this.messagesQueue.remove();
                    this.processMessage(nextMessage);
                    // Just so that we can receive interrupts also when lot of messages
                    // are in the queuesku
                    Thread.sleep(10);
                }
                Thread.sleep(DAEMON_SLEEP_TIME);
            } catch (InterruptedException e) {
                this.messagesQueue.clear();
                return;
            }
        }
    }

    protected abstract void processMessage(PaymentMessageBase message);
}
