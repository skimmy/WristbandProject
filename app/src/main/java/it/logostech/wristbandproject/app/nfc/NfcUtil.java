package it.logostech.wristbandproject.app.nfc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.nfc.cardemulation.CardEmulation;

import it.logostech.wristbandproject.app.model.payment.protocol.IdentityMessage;
import it.logostech.wristbandproject.app.util.TypeUtil;

/**
 * Class containing several (static) helper methods to use Android NFC APIs
 *
 * Created by Michele Schimd on 25/09/2014.
 *
 * @version 1.1
 */
public class NfcUtil {
    // ISO-DEP command HEADER for selecting an AID.
    // Format: [Class | Instruction | Parameter 1 | Parameter 2]
    public static final String SELECT_APDU_HEADER = "00A40400";
    // "OK" status word sent in response to SELECT AID command (0x9000)
    public static final byte[] SELECT_OK_SW = TypeUtil.hexStringToByteArray("9000");
    // "UNKNOWN" status word sent in response to invalid APDU command (0x0000)
    public static final byte[] UNKNOWN_CMD_SW = TypeUtil.hexStringToByteArray("0000");

    /**
     * Checks if the current device supports Host Card Emulation (HCE).
     *
     * @param ctx the current context
     * @return <code>true</code>
     */
    public static boolean isHceAvailable(Context ctx) {
        return ctx.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_NFC_HOST_CARD_EMULATION);
    }




    /**
     * Checks whether current context is the system default for the passed ApplicationID (AID)
     *
     * @param aid the AID to test
     * @param ctx the current context
     * @return <code>true</code> if the current context (<i>e.g.</i> activity) is the
     * default for AID
     */
    public static boolean isDefaultServiceForAid(String aid, Context ctx) {
        return CardEmulation.getInstance(NfcAdapter.getDefaultAdapter(ctx)).
                isDefaultServiceForAid(new ComponentName(ctx, MyHostApduService.class), aid);
    }

    /**
     * Sets the passed context as system default for the given ApllicationID (AID)
     *
     * @param aid the AID for which set default responder
     * @param ctx the context to set as defualt responder
     */
    public static void setDefaultForAid(String aid, Context ctx) {
        Intent intent = new Intent(CardEmulation.ACTION_CHANGE_DEFAULT);
        ctx.startActivity(intent);
    }

    /**
     * Builds a SELECT AID message.
     *
     * @param aid target id
     * @return a SELECT AID message
     */
    public static byte[] buildSelectApdu(String aid) {
        // Format: [CLASS | INSTRUCTION | PARAMETER 1 | PARAMETER 2 | LENGTH | DATA]
        return TypeUtil.hexStringToByteArray(SELECT_APDU_HEADER +
                String.format("%02X", aid.length() / 2) + aid);
    }

}
