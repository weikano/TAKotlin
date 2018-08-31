package com.kingsunsoft.sdk.login.net.encryption;

/**
 * Created by tom on 2014/12/19.
 */
public class EncryptionManager {

    private static IEncryption mInstance = new DES3();

    public static final String ENCYPTION_TYPE_DES3 = "DES3";
    public static final String ENCYPTION_TYPE_TEA = "TEA";

    public static IEncryption getEncyption() {
        return mInstance;
    }
}
