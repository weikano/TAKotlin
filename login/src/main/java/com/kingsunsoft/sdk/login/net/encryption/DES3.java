package com.kingsunsoft.sdk.login.net.encryption;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DES3 implements IEncryption {

    // 定义加密算法，DESede即3DES
    private static final String Algorithm = "DESede/ECB/PKCS5Padding";
    // 加密密钥
    private static final String PASSWORD_CRYPT_KEY = "dajo308cvmx,ve@g0i54-3s";


    private SecretKeySpec key = null;

    @Override
    public byte[] encryption(byte[] bytes) {
        try {
            Cipher cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, getKey());
            return cipher.doFinal(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] decryption(byte[] bytes) {
        try {
            Cipher cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.DECRYPT_MODE, getKey());
            return cipher.doFinal(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private SecretKeySpec getKey() throws UnsupportedEncodingException {
        if (key == null) {
            key = new SecretKeySpec(build3DesKey(PASSWORD_CRYPT_KEY), Algorithm);
        }
        return key;
    }

    /**
     * 根据字符串生成密钥24位的字节数组
     *
     * @param keyStr
     * @return
     * @throws UnsupportedEncodingException
     */
    private static byte[] build3DesKey(String keyStr)
            throws UnsupportedEncodingException {
        byte[] key = new byte[24];
        byte[] temp = keyStr.getBytes("UTF-8");
        if (key.length > temp.length) {
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }

}
