package it.logostech.wristbandproject.app.backend;

import android.util.Log;

import com.appspot.wristband_unipd.paymentremote.Paymentremote;
import com.appspot.wristband_unipd.paymentremote.model.PaymentMessagesPaymentAuthorizedMerchantMessage;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;

import it.logostech.wristbandproject.app.model.payment.PaymentDetails;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentAuthorizationMerchant;

/**
 * @author Michele Schimd
 * @version 1.0
 * @since 10/21/14.
 */
public class RemotePaymentWS {

    public static final String TAG = RemotePaymentWS.class.getSimpleName();

    private static Paymentremote service = null;

    public static void initService() {
        if (service == null) {
            Paymentremote.Builder builder = new Paymentremote.Builder(
                    AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
            service = builder.build();
        }
    }

    public static void freeService() {
        service = null;
    }

    public static PaymentAuthorizationMerchant paymentRequestMerchant(PaymentDetails details) {
        PaymentMessagesPaymentAuthorizedMerchantMessage authMsg = null;
//        details = PaymentDetails.fromProperties("ABCDE", "gg", "www", 9876.54, PaymentDetails.PURCHASE_TYPE_SERVICE);
        try {
            authMsg  = service.paymentrequestmerchant(details.getTransactionId(), details.getWearId(),
                    details.getGateId(), details.getTransactionId(), details.getAmount(),
                    Long.valueOf(details.getPurchaseType())).execute();
            Log.v(TAG, "PaymentRequestMerchant (" + details.getTransactionId() + ") " +
                    "Authorized: " + authMsg.getAuthorized());
        } catch (IOException e) {
            Log.e(TAG, "PaymentRequestMerchant error: " + e.getMessage());
        }
        return PaymentAuthorizationMerchant.fromMerchantAuthMessage(authMsg);
    }
}
