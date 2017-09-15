package ru.coffeeplanter.androidlogin.domain;

import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Cryption class.
 */

public class Crypter {

    private final String TAG = "Crypter";

    private String mAlgorithm;

    private Cipher mEncipher;
    private Cipher mDecipher;

    public Crypter(byte[] key, String algorithm) {
        try {
            this.mAlgorithm = algorithm;
            mEncipher = Cipher.getInstance(getAlgorithm());
            mDecipher = Cipher.getInstance(getAlgorithm());
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, getAlgorithm());
            mEncipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            mDecipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        }
        catch (Exception e) {
            Log.e(TAG, "Error while initializing Crypter object: ", e);
        }
    }

    public Crypter(byte[] key) {
        this(key, "AES");
    }

    public String encrypt(String str) {
        try {
            byte[] encrypted = mEncipher.doFinal(str.getBytes("UTF8"));
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        }
        catch (Exception e) {
            Log.e(TAG, "Encryption error: ", e);
        }
        return str;
    }

    public String decrypt(String str) {
        try {
            byte[] decrypted = Base64.decode(str, Base64.DEFAULT);
            return new String(mDecipher.doFinal(decrypted), "UTF8");
        }
        catch (Exception e) {
            Log.e(TAG, "Decryption error: ", e);
        }
        return str;
    }

    public String getAlgorithm() {
        return mAlgorithm;
    }

}
