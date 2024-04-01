package com.student.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;

import java.util.Map;

public class Sm2Util {
    public Sm2Util() {
    }

    public static Map<String, String> generateKeyPair() {
        Map<String, String> result = MapUtil.newHashMap();
        SM2 sm2 = SmUtil.sm2();
        byte[] privateKey = BCUtil.encodeECPrivateKey(sm2.getPrivateKey());
        byte[] publicKey;
        if (privateKey.length > 0 && privateKey[0] == 0) {
            publicKey = new byte[privateKey.length - 1];
            System.arraycopy(privateKey, 1, publicKey, 0, publicKey.length);
            privateKey = publicKey;
        }

        publicKey = BCUtil.encodeECPublicKey(sm2.getPublicKey(), false);
        result.put("priKey", HexUtil.encodeHexStr(privateKey));
        result.put("pubKey", HexUtil.encodeHexStr(publicKey));
        return result;
    }

    public static String encodeByPubKey(String pubKey, String data) {
        if (StrUtil.isEmpty(data)) {
            return data;
        } else {
            SM2 sm2 = new SM2((String)null, pubKey);
            return sm2.encryptBcd(data, KeyType.PublicKey);
        }
    }

    public static String decodeByPriKey(String priKey, String data) {
        if (StrUtil.isEmpty(data)) {
            return data;
        } else {
            SM2 sm2 = new SM2(priKey, (String)null);
            String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(data, KeyType.PrivateKey));
            return decryptStr;
        }
    }

}
