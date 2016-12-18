package com.abit.han.pda.push.umeng;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 辅助类加密解密
 */

public class PushHelper {
    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_UPPER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};



    public static String md5Hex(final byte[] data) {
        return encodeHexString(md5(data));
    }
    public static byte[] md5(final byte[] data) {
        return getMd5Digest().digest(data);
    }
    public static MessageDigest getMd5Digest() {
        return getDigest("MD5");
    }
    public static MessageDigest getDigest(final String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }
    public static String encodeHexString(final byte[] data) {
        return new String(encodeHex(data));
    }
    public static char[] encodeHex(final byte[] data) {
        return encodeHex(data, true);
    }


    public static char[] encodeHex(final byte[] data, final boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static char[] encodeHex(final byte[] data, final char[] toDigits) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }

}
