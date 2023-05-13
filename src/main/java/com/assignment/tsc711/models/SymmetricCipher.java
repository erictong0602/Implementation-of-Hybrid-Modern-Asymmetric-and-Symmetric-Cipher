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

    // This is the constructor to generate the symmetric cipher based on given algorithm and defined
    // NIST operation
    public SymmetricCipher(String algorithm, String encryptAlgorithm)
            throws NoSuchAlgorithmException {
        this.key = createKey(algorithm);
        this.encryptAlgorithm = encryptAlgorithm;
        this.initializationVector = createInitializationVector();
    }

    // This is the constructor to generate the symmetric cipher based on given algorithm and defined
    // NIST operation and given IV
    public SymmetricCipher(SecretKey key, String encryptAlgorithm, byte[] initializationVector) {
        this.key = key;
        this.encryptAlgorithm = encryptAlgorithm;
        this.initializationVector = initializationVector;
    }

    // this function return a secret key using the speficis algorithm, we are using AES algorithm
    // the key size is 256
    public static SecretKey createKey(String algorithm) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        KeyGenerator keygenerator = KeyGenerator.getInstance(algorithm);
        keygenerator.init(256, secureRandom);
        SecretKey key = keygenerator.generateKey();
        return key;
    }

    // this function is to used for create IV that used for encryption and decryption
    // it generate a random bytes array of 16bits
    public static byte[] createInitializationVector() {
        // Used with encryption
        byte[] initializationVector = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(initializationVector);
        return initializationVector;
    }

    // This function perform the encryption for plaintext based on given algorithm and NIST operation
    // The Cipher is initialized with the encryption algo and IV for this symmetricCipher
    // Cipher wil be used in encrypt mode with secret key and IV
    public byte[] encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(this.encryptAlgorithm);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);

        cipher.init(Cipher.ENCRYPT_MODE, this.key, ivParameterSpec);

        return cipher.doFinal(plainText.getBytes());
    }

    // this function perform the decryption for the plaintext based on the given algorithm and NIST operation
    // the cipher is initialized with encrypt algoritnm
    // the cipher is used in decrypt mode and init with secret key and IV
    // after decryption will return a String result
    public String decrypt(byte[] cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(this.encryptAlgorithm);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(this.initializationVector);

        cipher.init(Cipher.DECRYPT_MODE, this.key, ivParameterSpec);

        byte[] result = cipher.doFinal(cipherText);

        return new String(result);
    }
}
