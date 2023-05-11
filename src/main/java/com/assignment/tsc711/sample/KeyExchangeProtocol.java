package com.assignment.tsc711.sample;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class KeyExchangeProtocol {
    public static void main(String[] args) {
        try {
            // Step 1: Generate RSA key pairs for Alice and Bob
            KeyPair aliceKeyPair = generateKeyPair();
            KeyPair bobKeyPair = generateKeyPair();

            // Step 2: Alice sends her public key to Bob
            PublicKey alicePublicKey = aliceKeyPair.getPublic();

            // Step 3: Bob sends his public key to Alice
            PublicKey bobPublicKey = bobKeyPair.getPublic();

            // Step 4: Secret Key Generation by Alice
            SecretKey secretKey = generateSecretKey();

            // Step 5: Alice encrypts the secret key using Bob's public key
            byte[] encryptedSecretKey = encryptSecretKey(secretKey, bobPublicKey);

            // Step 6: Secret Key Decryption by Bob
            SecretKey decryptedSecretKey = decryptSecretKey(encryptedSecretKey, bobKeyPair.getPrivate());

            // Verify that the decrypted secret key matches the original secret key
            if (decryptedSecretKey.equals(secretKey)) {
                System.out.println("Secret Key Exchange Successful!");
            } else {
                System.out.println("Secret Key Exchange Failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Generate RSA key pair
    private static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    // Generate a secret key
    private static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    // Encrypt the secret key using the recipient's public key
    private static byte[] encryptSecretKey(SecretKey secretKey, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(secretKey.getEncoded());
    }

    // Decrypt the encrypted secret key using the recipient's private key
    private static SecretKey decryptSecretKey(byte[] encryptedSecretKey, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedSecretKeyBytes = cipher.doFinal(encryptedSecretKey);
        return new javax.crypto.spec.SecretKeySpec(decryptedSecretKeyBytes, "AES");
    }
}
