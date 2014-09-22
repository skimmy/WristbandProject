package it.logostech.wristbandproject.app.util;

/**
 * Created by michele.schimd on 22/09/2014.
 */
public class TypeUtil {
    /**
     * Converts the input array into an hexadecimal string. This method is
     * useful for debugging purpose since it allows to have a visual
     * representation of a byte array.
     *
     * @param bytes input array
     * @return the hexadecimal encoding of the input array
     */
    public static String byteArrayToHexString(byte[] bytes) {
        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for ( int j = 0; j < bytes.length; j++ ) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
//        return new BigInteger(array).toString();
    }
}
