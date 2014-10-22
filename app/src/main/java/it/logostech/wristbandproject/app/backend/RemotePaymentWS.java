package it.logostech.wristbandproject.app.backend;

import com.appspot.wristband_unipd.paymentremote.Paymentremote;
import com.appspot.wristband_unipd.paymentremote.model.PaymentMessagesPaymentAuthorizedMerchantMessage;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;

import it.logostech.wristbandproject.app.model.payment.PaymentDetails;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentAuthorizationMerchant;

/**
 * Created by Michele Schimd on 10/21/14.
 */
public class RemotePaymentWS {

    private static  Paymentremote service = null;

    public static void initService() {
        Paymentremote.Builder builder = new Paymentremote.Builder(
                AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
        service = builder.build();
    }

    public static void freeService() {
        service = null;
    }

    public static PaymentAuthorizationMerchant paymentRequestMerchant(PaymentDetails details) {
        PaymentMessagesPaymentAuthorizedMerchantMessage authMsg = null;
        try {
            service.paymentrequestmerchant(details.getTransactionId(), details.getWearId(),
                    details.getGateId(), details.getTransactionId(), details.getAmount(),
                    Long.valueOf(details.getPurchaseType()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
