package com.student.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.SM4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Sm4Util {
    private static final Logger log = LoggerFactory.getLogger(Sm4Util.class);
    private static final int ENCRYPT = 1;
    private static final int DECRYPT = 0;
    private static final int ROUND = 32;
    private static final int BLOCK = 16;
    public static String ALGORITHM_OFFSET = null;
    @Value("${algorithm.offset:fHjJDEkOTq2kwVu7}")
    private String iv;
    private static byte[] Sbox = new byte[]{-42, -112, -23, -2, -52, -31, 61, -73, 22, -74, 20, -62, 40, -5, 44, 5, 43, 103, -102, 118, 42, -66, 4, -61, -86, 68, 19, 38, 73, -122, 6, -103, -100, 66, 80, -12, -111, -17, -104, 122, 51, 84, 11, 67, -19, -49, -84, 98, -28, -77, 28, -87, -55, 8, -24, -107, -128, -33, -108, -6, 117, -113, 63, -90, 71, 7, -89, -4, -13, 115, 23, -70, -125, 89, 60, 25, -26, -123, 79, -88, 104, 107, -127, -78, 113, 100, -38, -117, -8, -21, 15, 75, 112, 86, -99, 53, 30, 36, 14, 94, 99, 88, -47, -94, 37, 34, 124, 59, 1, 33, 120, -121, -44, 0, 70, 87, -97, -45, 39, 82, 76, 54, 2, -25, -96, -60, -56, -98, -22, -65, -118, -46, 64, -57, 56, -75, -93, -9, -14, -50, -7, 97, 21, -95, -32, -82, 93, -92, -101, 52, 26, 85, -83, -109, 50, 48, -11, -116, -79, -29, 29, -10, -30, 46, -126, 102, -54, 96, -64, 41, 35, -85, 13, 83, 78, 111, -43, -37, 55, 69, -34, -3, -114, 47, 3, -1, 106, 114, 109, 108, 91, 81, -115, 27, -81, -110, -69, -35, -68, 127, 17, -39, 92, 65, 31, 16, 90, -40, 10, -63, 49, -120, -91, -51, 123, -67, 45, 116, -48, 18, -72, -27, -76, -80, -119, 105, -105, 74, 12, -106, 119, 126, 101, -71, -15, 9, -59, 110, -58, -124, 24, -16, 125, -20, 58, -36, 77, 32, 121, -18, 95, 62, -41, -53, 57, 72};
    private static int[] CK = new int[]{462357, 472066609, 943670861, 1415275113, 1886879365, -1936483679, -1464879427, -993275175, -521670923, -66909679, 404694573, 876298825, 1347903077, 1819507329, -2003855715, -1532251463, -1060647211, -589042959, -117504499, 337322537, 808926789, 1280531041, 1752135293, -2071227751, -1599623499, -1128019247, -656414995, -184876535, 269950501, 741554753, 1213159005, 1684763257};
    private static String[] chats = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "l", "k", "j", "h", "g", "f", "d", "s", "a", "z", "x", "c", "v", "b", "n", "m"};

    public Sm4Util() {
    }

    @PostConstruct
    public void setAlgorithmOffset() {
        if (ObjectUtil.isNotEmpty(this.iv) && StrUtil.length(this.iv) == 16) {
            ALGORITHM_OFFSET = this.iv;
        }

    }

    private static int Rotl(int x, int y) {
        return x << y | x >>> 32 - y;
    }

    private static int ByteSub(int A) {
        return (Sbox[A >>> 24 & 255] & 255) << 24 | (Sbox[A >>> 16 & 255] & 255) << 16 | (Sbox[A >>> 8 & 255] & 255) << 8 | Sbox[A & 255] & 255;
    }

    private static int L1(int B) {
        return B ^ Rotl(B, 2) ^ Rotl(B, 10) ^ Rotl(B, 18) ^ Rotl(B, 24);
    }

    private static int L2(int B) {
        return B ^ Rotl(B, 13) ^ Rotl(B, 23);
    }

    void SMS4Crypt(byte[] Input, byte[] Output, int[] rk) {
        int[] x = new int[4];
        int[] tmp = new int[4];

        int j;
        for(j = 0; j < 4; ++j) {
            tmp[0] = Input[0 + 4 * j] & 255;
            tmp[1] = Input[1 + 4 * j] & 255;
            tmp[2] = Input[2 + 4 * j] & 255;
            tmp[3] = Input[3 + 4 * j] & 255;
            x[j] = tmp[0] << 24 | tmp[1] << 16 | tmp[2] << 8 | tmp[3];
        }

        for(int r = 0; r < 32; r += 4) {
            int mid = x[1] ^ x[2] ^ x[3] ^ rk[r + 0];
            mid = ByteSub(mid);
            x[0] ^= L1(mid);
            mid = x[2] ^ x[3] ^ x[0] ^ rk[r + 1];
            mid = ByteSub(mid);
            x[1] ^= L1(mid);
            mid = x[3] ^ x[0] ^ x[1] ^ rk[r + 2];
            mid = ByteSub(mid);
            x[2] ^= L1(mid);
            mid = x[0] ^ x[1] ^ x[2] ^ rk[r + 3];
            mid = ByteSub(mid);
            x[3] ^= L1(mid);
        }

        for(j = 0; j < 16; j += 4) {
            Output[j] = (byte)(x[3 - j / 4] >>> 24 & 255);
            Output[j + 1] = (byte)(x[3 - j / 4] >>> 16 & 255);
            Output[j + 2] = (byte)(x[3 - j / 4] >>> 8 & 255);
            Output[j + 3] = (byte)(x[3 - j / 4] & 255);
        }

    }

    private void SMS4KeyExt(byte[] Key, int[] rk, int CryptFlag) {
        int[] x = new int[4];
        int[] tmp = new int[4];

        for(int i = 0; i < 4; ++i) {
            tmp[0] = Key[0 + 4 * i] & 255;
            tmp[1] = Key[1 + 4 * i] & 255;
            tmp[2] = Key[2 + 4 * i] & 255;
            tmp[3] = Key[3 + 4 * i] & 255;
            x[i] = tmp[0] << 24 | tmp[1] << 16 | tmp[2] << 8 | tmp[3];
        }

        x[0] ^= -1548633402;
        x[1] ^= 1453994832;
        x[2] ^= 1736282519;
        x[3] ^= -1301273892;

        int r;
        int mid;
        for(r = 0; r < 32; r += 4) {
            mid = x[1] ^ x[2] ^ x[3] ^ CK[r + 0];
            mid = ByteSub(mid);
            rk[r + 0] = x[0] ^= L2(mid);
            mid = x[2] ^ x[3] ^ x[0] ^ CK[r + 1];
            mid = ByteSub(mid);
            rk[r + 1] = x[1] ^= L2(mid);
            mid = x[3] ^ x[0] ^ x[1] ^ CK[r + 2];
            mid = ByteSub(mid);
            rk[r + 2] = x[2] ^= L2(mid);
            mid = x[0] ^ x[1] ^ x[2] ^ CK[r + 3];
            mid = ByteSub(mid);
            rk[r + 3] = x[3] ^= L2(mid);
        }

        if (CryptFlag == 0) {
            for(r = 0; r < 16; ++r) {
                mid = rk[r];
                rk[r] = rk[31 - r];
                rk[31 - r] = mid;
            }
        }

    }

    public int sms4(byte[] in, int inLen, byte[] key, byte[] out, int CryptFlag) {
        int point = 0;
        int[] round_key = new int[32];
        this.SMS4KeyExt(key, round_key, CryptFlag);
        byte[] input = new byte[16];

        for(byte[] output = new byte[16]; inLen >= 16; point += 16) {
            input = Arrays.copyOfRange(in, point, point + 16);
            this.SMS4Crypt(input, output, round_key);
            System.arraycopy(output, 0, out, point, 16);
            inLen -= 16;
        }

        return 0;
    }

    public static byte[] filter(byte[] src) {
        byte[] res = new byte[src.length];

        for(int i = 2; i < src.length; ++i) {
            res[i - 2] = src[i];
        }

        return res;
    }

    public static byte[] encodeSMS4(String plaintext, byte[] key) throws UnsupportedEncodingException {
        if (plaintext != null && !plaintext.equals("")) {
            for(int i = plaintext.getBytes("unicode").length % 16; i < 16; ++i) {
                plaintext = plaintext + " ";
            }

            return encodeSMS4(plaintext.getBytes("unicode"), key);
        } else {
            return null;
        }
    }

    public static byte[] encodeSMS4(byte[] plaintext, byte[] key) {
        byte[] ciphertext = new byte[plaintext.length];
        int k = 0;

        for(int plainLen = plaintext.length; k + 16 <= plainLen; k += 16) {
            byte[] cellPlain = new byte[16];

            for(int i = 0; i < 16; ++i) {
                cellPlain[i] = plaintext[k + i];
            }

            byte[] cellCipher = encode16(cellPlain, key);

            for(int i = 0; i < cellCipher.length; ++i) {
                ciphertext[k + i] = cellCipher[i];
            }
        }

        return ciphertext;
    }

    public static byte[] decodeSMS4(byte[] ciphertext, byte[] key) {
        byte[] plaintext = new byte[ciphertext.length];
        int k = 0;

        for(int cipherLen = ciphertext.length; k + 16 <= cipherLen; k += 16) {
            byte[] cellCipher = new byte[16];

            for(int i = 0; i < 16; ++i) {
                cellCipher[i] = ciphertext[k + i];
            }

            byte[] cellPlain = decode16(cellCipher, key);

            for(int i = 0; i < cellPlain.length; ++i) {
                plaintext[k + i] = cellPlain[i];
            }
        }

        return plaintext;
    }

    public static String decodeSMS4toString(byte[] ciphertext, byte[] key) throws UnsupportedEncodingException {
        byte[] plaintext = new byte[ciphertext.length];
        plaintext = decodeSMS4(ciphertext, key);
        plaintext = filter(plaintext);
        return new String(plaintext, "unicode");
    }

    private static byte[] encode16(byte[] plaintext, byte[] key) {
        byte[] cipher = new byte[16];
        Sm4Util sm4 = new Sm4Util();
        sm4.sms4(plaintext, 16, key, cipher, 1);
        return cipher;
    }

    private static byte[] decode16(byte[] ciphertext, byte[] key) {
        byte[] plain = new byte[16];
        Sm4Util sm4 = new Sm4Util();
        sm4.sms4(ciphertext, 16, key, plain, 0);
        return plain;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src != null && src.length > 0) {
            for(int i = 0; i < src.length; ++i) {
                int v = src[i] & 255;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }

                stringBuilder.append(hv);
            }

            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString != null && !hexString.equals("")) {
            hexString = hexString.toUpperCase();
            int length = hexString.length() / 2;
            char[] hexChars = hexString.toCharArray();
            byte[] d = new byte[length];

            for(int i = 0; i < length; ++i) {
                int pos = i * 2;
                d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
            }

            return d;
        } else {
            return null;
        }
    }

    public static byte charToByte(char c) {
        return (byte)"0123456789ABCDEF".indexOf(c);
    }

    private static void SMS4DKCrypt(byte[] Input, byte[] Output, int[] rk) {
        int[] x = new int[4];
        int[] tmp = new int[4];

        int j;
        for(j = 0; j < 4; ++j) {
            tmp[0] = Input[0 + 4 * j] & 255;
            tmp[1] = Input[1 + 4 * j] & 255;
            tmp[2] = Input[2 + 4 * j] & 255;
            tmp[3] = Input[3 + 4 * j] & 255;
            x[j] = tmp[0] << 24 | tmp[1] << 16 | tmp[2] << 8 | tmp[3];
        }

        for(int r = 0; r < 32; r += 4) {
            int mid = x[1] ^ x[2] ^ x[3] ^ rk[r + 0];
            mid = ByteSub(mid);
            x[0] ^= L1(mid);
            mid = x[2] ^ x[3] ^ x[0] ^ rk[r + 1];
            mid = ByteSub(mid);
            x[1] ^= L1(mid);
            mid = x[3] ^ x[0] ^ x[1] ^ rk[r + 2];
            mid = ByteSub(mid);
            x[2] ^= L1(mid);
            mid = x[0] ^ x[1] ^ x[2] ^ rk[r + 3];
            mid = ByteSub(mid);
            x[3] ^= L1(mid);
        }

        for(j = 0; j < 16; j += 4) {
            Output[j] = (byte)(x[3 - j / 4] >>> 24 & 255);
            Output[j + 1] = (byte)(x[3 - j / 4] >>> 16 & 255);
            Output[j + 2] = (byte)(x[3 - j / 4] >>> 8 & 255);
            Output[j + 3] = (byte)(x[3 - j / 4] & 255);
        }

    }

    private static void SMS4DKKeyExt(byte[] Key, int[] rk, int CryptFlag) {
        int[] x = new int[4];
        int[] tmp = new int[4];

        for(int i = 0; i < 4; ++i) {
            tmp[0] = Key[0 + 4 * i] & 255;
            tmp[1] = Key[1 + 4 * i] & 255;
            tmp[2] = Key[2 + 4 * i] & 255;
            tmp[3] = Key[3 + 4 * i] & 255;
            x[i] = tmp[0] << 24 | tmp[1] << 16 | tmp[2] << 8 | tmp[3];
        }

        x[0] ^= -1548633402;
        x[1] ^= 1453994832;
        x[2] ^= 1736282519;
        x[3] ^= -1301273892;

        int r;
        int mid;
        for(r = 0; r < 32; r += 4) {
            mid = x[1] ^ x[2] ^ x[3] ^ CK[r + 0];
            mid = ByteSub(mid);
            rk[r + 0] = x[0] ^= L2(mid);
            mid = x[2] ^ x[3] ^ x[0] ^ CK[r + 1];
            mid = ByteSub(mid);
            rk[r + 1] = x[1] ^= L2(mid);
            mid = x[3] ^ x[0] ^ x[1] ^ CK[r + 2];
            mid = ByteSub(mid);
            rk[r + 2] = x[2] ^= L2(mid);
            mid = x[0] ^ x[1] ^ x[2] ^ CK[r + 3];
            mid = ByteSub(mid);
            rk[r + 3] = x[3] ^= L2(mid);
        }

        if (CryptFlag == 0) {
            for(r = 0; r < 16; ++r) {
                mid = rk[r];
                rk[r] = rk[31 - r];
                rk[31 - r] = mid;
            }
        }

    }

    private static int SMS4DK(byte[] in, int inLen, byte[] key, byte[] out, int CryptFlag) {
        int point = 0;
        int[] round_key = new int[32];
        SMS4DKKeyExt(key, round_key, CryptFlag);
        byte[] input = new byte[16];

        for(byte[] output = new byte[16]; inLen >= 16; point += 16) {
            input = Arrays.copyOfRange(in, point, point + 16);
            SMS4DKCrypt(input, output, round_key);
            System.arraycopy(output, 0, out, point, 16);
            inLen -= 16;
        }

        return 0;
    }

    private static String encodeByHexSMS4(String plaintext, byte[] key) throws UnsupportedEncodingException {
        if (StrUtil.isEmpty(plaintext)) {
            return "";
        } else {
            int len = plaintext.length() % 32;
            if (len > 0) {
                for(int i = len; i < 32; ++i) {
                    plaintext = plaintext + "0";
                }
            }

            byte[] mingwenByte = hexStringToBytes(plaintext);
            byte[] miwen = new byte[mingwenByte.length];
            SMS4DK(mingwenByte, mingwenByte.length, key, miwen, 1);
            String res = "";
            if (miwen != null) {
                res = bytesToHexString(miwen).toUpperCase();
            }

            return res.trim();
        }
    }

    private static String decodeByHexSMS4(String ent, byte[] key) throws UnsupportedEncodingException {
        if (StrUtil.isEmpty(ent)) {
            return "";
        } else {
            int len = ent.length() % 32;
            if (len > 0) {
                for(int i = len; i < 32; ++i) {
                    ent = ent + "0";
                }
            }

            byte[] miwenByte = hexStringToBytes(ent);
            byte[] mingwen = new byte[miwenByte.length];
            SMS4DK(miwenByte, miwenByte.length, key, mingwen, 0);
            String value_mingwen = "";
            if (mingwen != null) {
                value_mingwen = bytesToHexString(mingwen).toUpperCase();

                for(int i = 0; i < value_mingwen.length(); i += 2) {
                    if (value_mingwen.charAt(i) == '0' && value_mingwen.charAt(i + 1) == '0') {
                        value_mingwen = value_mingwen.substring(0, i);
                        break;
                    }
                }
            }

            return value_mingwen.trim();
        }
    }

    public static String encodeBySMS4(String plaintext, byte[] key) throws UnsupportedEncodingException {
        byte[] enOut = encodeSMS4(plaintext, key);
        if (enOut == null) {
            return "";
        } else {
            int[] enOutInt = new int[enOut.length];

            for(int i = 0; i < enOut.length; ++i) {
                enOutInt[i] = 255 & enOut[i];
            }

            String res = "";

            for(int i = 0; i < enOutInt.length; ++i) {
                if (i == 0) {
                    res = "" + enOutInt[i];
                } else {
                    res = res + "," + enOutInt[i];
                }
            }

            return res;
        }
    }

    public static String decodeBySMS4(String ent, byte[] key) throws UnsupportedEncodingException {
        if (ent != null && !"".equals(ent)) {
            String[] out = ent.split(",");
            byte[] entOut = new byte[out.length];

            for(int i = 0; i < out.length; ++i) {
                int j = Integer.parseInt(out[i]);
                entOut[i] = (byte)j;
            }

            String deOut = decodeSMS4toString(entOut, key);
            String res = deOut.substring(0, deOut.lastIndexOf(deOut.trim()) + deOut.trim().length());
            return res;
        } else {
            return "";
        }
    }

    public static String decodeBySMS4(String ent, byte[] key, String flag) throws UnsupportedEncodingException {
        if (StrUtil.isEmpty(ent)) {
            return "";
        } else {
            char[] tempChar = ent.toCharArray();
            int len = tempChar.length % 32;
            if (len > 0) {
                for(int i = len; i < 32; ++i) {
                    ent = ent + "0";
                }
            }

            byte[] miwenByte = hexStringToBytes(ent);
            byte[] mingwen = new byte[miwenByte.length];
            Sm4Util sm4 = new Sm4Util();
            sm4.sms4(miwenByte, miwenByte.length, key, mingwen, 0);
            String value_mingwen = "";
            if (mingwen != null) {
                value_mingwen = bytesToHexString(mingwen).toUpperCase();
            }

            return value_mingwen.trim();
        }
    }

    public static String encodeBySMS4(String plaintext, byte[] key, String flag) throws UnsupportedEncodingException {
        if (StrUtil.isEmpty(plaintext)) {
            return "";
        } else {
            char[] tempChar = plaintext.toCharArray();
            int len = tempChar.length % 32;
            if (len > 0) {
                for(int i = len; i < 32; ++i) {
                    plaintext = plaintext + "0";
                }
            }

            byte[] mingwenByte = hexStringToBytes(plaintext);
            byte[] miwen = new byte[mingwenByte.length];
            Sm4Util sm4 = new Sm4Util();
            sm4.sms4(mingwenByte, mingwenByte.length, key, miwen, 1);
            String res = "";
            if (miwen != null) {
                res = bytesToHexString(miwen).toUpperCase();
            }

            return res.trim();
        }
    }

    public static String encode(String plaintext, String sm4Key) throws UnsupportedEncodingException {
        byte[] sm4KeyArr = hexStringToBytes(sm4Key);
        return encodeBySMS4(plaintext, sm4KeyArr);
    }

    public static String decode(String ciphertext, String sm4Key) throws UnsupportedEncodingException {
        byte[] sm4KeyArr = hexStringToBytes(sm4Key);
        return decodeBySMS4(ciphertext, sm4KeyArr);
    }

    public static String encodeEcbByHex(String plaintext, String sm4Key) throws UnsupportedEncodingException {
        byte[] sm4KeyArr = hexStringToBytes(sm4Key);
        byte[] plaintextBytes = plaintext.getBytes();
        String plaintextHexStr = bytesToHexString(plaintextBytes);
        return encodeByHexSMS4(plaintextHexStr, sm4KeyArr);
    }

    public static String decodeEcbByHex(String ciphertext, String sm4Key) throws UnsupportedEncodingException {
        if (!StrUtil.isEmpty(ciphertext)) {
            byte[] sm4KeyArr = hexStringToBytes(sm4Key);
            String plaintextHexStr = decodeByHexSMS4(ciphertext, sm4KeyArr);
            byte[] plaintextBytes = hexStringToBytes(plaintextHexStr);
            String result = new String(plaintextBytes);
            return result;
        } else {
            return "";
        }
    }

    public static String encodeByHex(String plaintext, String sm4Key) {
        return encodeByHex(plaintext, sm4Key, sm4Key);
    }

    public static String decodeByHex(String ciphertext, String sm4Key) {
        return decodeByHex(ciphertext, sm4Key, sm4Key);
    }

    public static String encodeByHex(String plaintext, String sm4Key, String iv) {
        if (StrUtil.isNotEmpty(ALGORITHM_OFFSET)) {
            iv = ALGORITHM_OFFSET;
        }

        SM4 s4 = new SM4(Mode.CBC, Padding.ZeroPadding, sm4Key.getBytes(), iv.getBytes());
        return s4.encryptHex(plaintext, CharsetUtil.CHARSET_UTF_8);
    }

    public static String decodeByHex(String ciphertext, String sm4Key, String iv) {
        String result = "";
        if (!StrUtil.isEmpty(ciphertext)) {
            if (StrUtil.isNotEmpty(ALGORITHM_OFFSET)) {
                iv = ALGORITHM_OFFSET;
            }

            SM4 s4 = new SM4(Mode.CBC, Padding.ZeroPadding, sm4Key.getBytes(), iv.getBytes());
            result = s4.decryptStr(ciphertext, CharsetUtil.CHARSET_UTF_8);
            if (isMessyCode(result)) {
                s4 = new SM4(Mode.CBC, Padding.ZeroPadding, sm4Key.getBytes(), sm4Key.getBytes());
                result = s4.decryptStr(ciphertext, CharsetUtil.CHARSET_UTF_8);
            }
        }

        return result;
    }

    public static String generateSm4Key(int length) {
        String key = "";
        length = length == 0 ? 16 : length;

        for(int i = 0; i < length; ++i) {
            int num = randomNumber(0, chats.length - 1);
            key = key + chats[num];
        }

        return key;
    }

    private static int randomNumber(int min, int max) {
        return (int)Math.floor(Math.random() * (double)(max - min + 1) + (double)min);
    }

    public static Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
    public static boolean isMessyCode(String str) {
        boolean flag = false;
        Matcher m = p.matcher(str);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = (float)ch.length;
        float count = 0.0F;
        char[] var8 = ch;
        int var9 = ch.length;

        for(int var10 = 0; var10 < var9; ++var10) {
            char c = var8[var10];
            if (!Character.isLetterOrDigit(c) && !isChinese(c)) {
                ++count;
                flag = true;
                break;
            }
        }

        return flag;
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }
}
