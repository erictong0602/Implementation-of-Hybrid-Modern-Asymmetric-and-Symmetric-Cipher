package com.assignment.tsc711.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@NoArgsConstructor
@Getter
@Setter
public class SymmetricCipher {

    private SecretKey key;
    private String encryptAlgorithm;

    private byte[] initializationVector;

    public SymmetricCipher(String algorithm, String encryptAlgorithm) throws NoSuchAlgorithmException {
        this.key = createKey(algorithm);
        this.encryptAlgorithm = encryptAlgorithm;
        this.initializationVector = createInitializationVector();
    }

    public SymmetricCipher(SecretKey key, String encryptAlgorithm, byte[] initializationVector) {
        this.key = key;
        this.encryptAlgorithm = encryptAlgorithm;
        this.initializationVector = initializationVector;
    }

    public static SecretKey createKey(String algorithm) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        KeyGenerator keygenerator = KeyGenerator.getInstance(algorithm);
        keygenerator.init(256, secureRandom);
        SecretKey key = keygenerator.generateKey();
        return key;
    }

    public static byte[] createInitializationVector() {

        // Used with encryption
        byte[] initializationVector = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(initializationVector);
        return initializationVector;
    }

    public byte[] encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(this.encryptAlgorithm);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);

        cipher.init(Cipher.ENCRYPT_MODE, this.key, ivParameterSpec);

        return cipher.doFinal(plainText.getBytes());
    }

    public String decrypt(byte[] cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(this.encryptAlgorithm);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(this.initializationVector);

        cipher.init(Cipher.DECRYPT_MODE, this.key, ivParameterSpec);

        byte[] result = cipher.doFinal(cipherText);

        return new String(result);
    }
}
