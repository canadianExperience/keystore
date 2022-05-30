package com.me.keystore

import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.me.keystore.MyKeyStore.Companion.decryptData
import com.me.keystore.MyKeyStore.Companion.encryptData
import com.me.keystore.databinding.ActivityMainBinding
import javax.crypto.KeyGenerator

private const val TAG = "ENCRYPT_DECRYPT"
const val ANDROID_KEY_STORE = "AndroidKeyStore"
const val SAMPLE_ALIAS = "MYALIAS"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(SAMPLE_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()

        val pair = encryptData("Test this encryption")

        val decryptedData = decryptData(pair.first, pair.second)

        val encrypted = pair.second.toString(Charsets.UTF_8)

        Log.d(TAG, "Encrypted data: $encrypted")
        Log.d(TAG, "Decrypted data: $decryptedData")
    }
}