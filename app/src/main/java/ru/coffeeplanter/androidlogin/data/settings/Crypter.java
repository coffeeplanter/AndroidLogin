package ru.coffeeplanter.androidlogin.data.settings;

import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Cryption class.
 */

@SuppressWarnings("SpellCheckingInspection")
class Crypter {

    private final String TAG = "Crypter";

    private String mAlgorithm;

    private Cipher mEncipher;
    private Cipher mDecipher;

    @SuppressWarnings("WeakerAccess")
    Crypter(byte[] key, String algorithm) {
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

    Crypter(byte[] key) {
        this(key, "AES");
    }

    String encrypt(String str) {
        try {
            byte[] encrypted = mEncipher.doFinal(str.getBytes("UTF8"));
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        }
        catch (Exception e) {
            Log.e(TAG, "Encryption error: ", e);
        }
        return str;
    }

    String decrypt(String str) {
        try {
            byte[] decrypted = Base64.decode(str, Base64.DEFAULT);
            return new String(mDecipher.doFinal(decrypted), "UTF8");
        }
        catch (Exception e) {
            Log.e(TAG, "Decryption error: ", e);
        }
        return str;
    }

    @SuppressWarnings("WeakerAccess")
    String getAlgorithm() {
        return mAlgorithm;
    }

}
