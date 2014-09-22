package it.logostech.wristbandproject.app.util;

import java.math.BigInteger;

/**
 * Created by michele.schimd on 22/09/2014.
 */
public class TypeUtil {
    /**
     * Converts the input array into an hexadecimal string. This method is
     * useful for debugging purpose since it allows to have a visual
     * representation of a byte array.
     *
     * @param array inpu array
     * @return the hexadecimal encoding of the input array
     */
    public static String byteArrayToHexString(byte[] array) {
        return new BigInteger(array).toString(16).toUpperCase();
    }
}
