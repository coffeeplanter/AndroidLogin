package ru.coffeeplanter.androidlogin.data.settings

import android.util.Base64
import android.util.Log

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * Cryption class.
 */

internal class Crypter(key: ByteArray, algorithm: String) {

    @Suppress("PrivatePropertyName")
    private val TAG = "Crypter"

    @Suppress("MemberVisibilityCanPrivate")
    var algorithm: String? = null
        private set

    private var mEncipher: Cipher? = null
    private var mDecipher: Cipher? = null

    init {
        try {
            this.algorithm = algorithm
            mEncipher = Cipher.getInstance(algorithm)
            mDecipher = Cipher.getInstance(algorithm)
            val secretKeySpec = SecretKeySpec(key, algorithm)
            mEncipher!!.init(Cipher.ENCRYPT_MODE, secretKeySpec)
            mDecipher!!.init(Cipher.DECRYPT_MODE, secretKeySpec)
        } catch (e: Exception) {
            Log.e(TAG, "Error while initializing Crypter object: ", e)
        }
    }

    constructor(key: ByteArray) : this(key, "AES")

    fun encrypt(str: String): String {
        try {
            val encrypted = mEncipher!!.doFinal(str.toByteArray(charset("UTF8")))
            return Base64.encodeToString(encrypted, Base64.DEFAULT)
        } catch (e: Exception) {
            Log.e(TAG, "Encryption error: ", e)
        }

        return str
    }

    fun decrypt(str: String): String {
        try {
            val decrypted = Base64.decode(str, Base64.DEFAULT)
            return String(mDecipher!!.doFinal(decrypted), charset("UTF8"))
        } catch (e: Exception) {
            Log.e(TAG, "Decryption error: ", e)
        }
        return str
    }

}
