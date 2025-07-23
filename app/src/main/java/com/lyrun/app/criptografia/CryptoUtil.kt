package com.lyrun.app.util

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

object CryptoUtil {

    // Use uma chave de 16 bytes (128 bits)
    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"

    // Gera uma chave secreta a partir da String (16 chars)
    fun generateKey(keyString: String): SecretKey {
        val keyBytes = keyString.toByteArray(Charsets.UTF_8)
        return SecretKeySpec(keyBytes, ALGORITHM)
    }

    // Gera um IV aleatório de 16 bytes (usado no CBC)
    fun generateIv(): IvParameterSpec {
        val iv = ByteArray(16)
        Random.nextBytes(iv)
        return IvParameterSpec(iv)
    }

    // Criptografa um texto simples, retorna Base64 (iv + texto criptografado)
    fun encrypt(plainText: String, secretKey: SecretKey): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val iv = generateIv()
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv)
        val encryptedBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

        // Concatena IV + ciphertext para decodificar depois
        val combined = iv.iv + encryptedBytes
        return Base64.encodeToString(combined, Base64.DEFAULT)
    }

    // Descriptografa o texto Base64 (espera IV + ciphertext)
    fun decrypt(encryptedText: String, secretKey: SecretKey): String {
        val combined = Base64.decode(encryptedText, Base64.DEFAULT)

        // Separa IV e ciphertext
        val iv = combined.copyOfRange(0, 16)
        val encryptedBytes = combined.copyOfRange(16, combined.size)
        val ivSpec = IvParameterSpec(iv)

        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)

        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes, Charsets.UTF_8)
    }
}
