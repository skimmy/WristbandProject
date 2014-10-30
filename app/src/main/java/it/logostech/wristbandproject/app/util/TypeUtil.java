package it.logostech.wristbandproject.app.util;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by michele.schimd on 22/09/2014.
 */
public class TypeUtil {

    // Enforce non instantiability of this util class
    private TypeUtil() { }

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
    }

    public static byte[] hexStringToByteArray(String s) {
        // Cut-And-Paste from Android sample :D
        int len = s.length();
        if (len % 2 == 1) {
            throw new IllegalArgumentException("Hex string must have even number of characters");
        }
        byte[] data = new byte[len / 2]; // Allocate 1 byte per 2 hex characters
        for (int i = 0; i < len; i += 2) {
            // Convert each character into a integer (base-16), then bit-shift into place
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static byte[] concatArrays(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    public static byte[] longToBytes(long l) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putLong(l);
        return bytes;
    }

    public static long bytesToLong(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getLong();
    }

    public static byte[] doubleToBytes(double d) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(d);
        return bytes;
    }

    public static double bytesToDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }


    /**
     * Creates a byte array from the given string padded with zeros <b>on the
     * beginning of the array</b>. If the bytes representation of<code>str</code>
     * is no longer than <code>bytesCount</code> than the output array contains
     * such representation with trailing zeros on the lowest index position, if
     * the string representation is too long it is actually truncated.
     *
     * @param str the string to be encoded
     * @param bytesCount the size of the output array
     * @return
     */
    public static byte[] stringToPaddedBytes(String str, int bytesCount) {
        byte[] bytes = new byte[bytesCount];
        byte[] stringRepr = str.getBytes();
        int strLen = stringRepr.length;
        int begin = Math.max(0, (bytesCount - strLen + 1));
        int end = bytesCount - 1;
        System.arraycopy(stringRepr, 0, bytes, begin, (end - begin + 1));
        return bytes;
    }
}
