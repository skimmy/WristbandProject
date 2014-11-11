package it.logostech.wristbandproject.app.model;

import android.nfc.tech.IsoDep;
import android.util.Log;

import java.io.IOException;

import it.logostech.wristbandproject.app.deamons.PaymentDaemonBase;
import it.logostech.wristbandproject.app.nfc.NfcUtil;

/**
 * Defines the running code of the thread responsible to manage a NFC connection.
 *
 * There should be only one thread running this code and it should be spawn only
 * by <i>payment gate daemon</i> (on the wear sides NFC communication looks more
 * like an asynchronous request / reply communication). Moreover as soon as the
 * gate daemon thread is interrupted also the NFC channel possibly associated
 * must be interrupted.
 *
 * For simplicity the thread runs as long as the caller doesn't interrupt it.
 * Once an interruption is sent to the thread, it immediately returns without
 * freeing any resource and, even, without closing the NFC connection. This
 * design follows a responsiveness principle from the UI point of view, moreover
 * the semantic is guaranteed if we ensure that NFC connections are (in some
 * sense) <i>idempotent</i>.
 *
 * @author Michele Schimd
 * @since 11/11/14.
 * @version 1.0
 */
public class NfcConnection implements Runnable {

    public static final String TAG = NfcConnection.class.getSimpleName();

    private long NFC_CONNECTION_SLEEP_TIME = 200;

    private IsoDep channel;
    private byte[] command;
    private PaymentDaemonBase callbackDaemon;

    // enforce singleton instance
    public static final NfcConnection NFC_CONNECTION = new NfcConnection();
    private NfcConnection() {
        this.reset();
    }

    public void reset() {
        channel = null;
        command = null;
        this.callbackDaemon = null;
    }

    /**
     * Sets the current NFC connection.
     *
     * @param connection the current NFC connection
     */
    public void setIsoDepConnection(IsoDep connection) {
        this.channel = connection;
    }

    public void setCallbackDaemon(PaymentDaemonBase callback) {
        this.callbackDaemon = callback;
    }

    public synchronized void setCommand(byte[] cmd) {
        this.command = cmd;
    }

    public synchronized byte[] retrieveCommand() {
        byte[] tmp = this.command;
        this.command = null;
        return tmp;
    }

    public synchronized boolean hasCommand() {
        return (this.command != null);
    }

    @Override
    public void run() {
        while(true) {
            try {
                // there is a command to send and the connection is alive
                if (this.hasCommand() && this.channel.isConnected()) {
                    byte[] command = this.retrieveCommand();
                    this.setCommand(null);
                    Log.v(TAG, "Sending command code: " + (char) command[0]);
                    byte[] reply = this.channel.transceive(command);
                    this.callbackDaemon.onMessage(NfcUtil.parseNfcReply(reply));
                }
                Thread.sleep(NFC_CONNECTION_SLEEP_TIME);
            } catch (InterruptedException e) {
                // return immediately (without even closing the connection)
                return;
            } catch (IOException e) {
                Log.e(TAG, "Connection Error " + e.getCause().toString());
            }
        }
    }
}
