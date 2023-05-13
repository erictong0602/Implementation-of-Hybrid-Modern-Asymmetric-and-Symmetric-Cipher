package com.assignment.tsc711;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;

import com.assignment.tsc711.constant.AppConstant;
import com.assignment.tsc711.models.AsymmetricCipher;
import com.assignment.tsc711.models.Person;
import com.assignment.tsc711.models.SymmetricCipher;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DemoApplication {
    private final static String defaultMessage = "Hello, Person B! This is a confidential message.";

    public static void main(String[] args) throws Exception {
        Person personA = new Person("Eric", AppConstant.ASYMMETRIC_CIPHER);
        Person personB = new Person("David", AppConstant.ASYMMETRIC_CIPHER);

        personB.addPublicKey(personA.getName(), personA.getAsymmetricCipher().getKeypair().getPublic());
        personA.addPublicKey(personB.getName(), personB.getAsymmetricCipher().getKeypair().getPublic());

        SymmetricCipher symmetricCipher = new SymmetricCipher(AppConstant.AES, AppConstant.NIST_CBC_AES_CIPHER_ALGORITHM);

        System.out.printf("Person A decided to send a message: {%s}\n", defaultMessage);
        byte[] encryptedSymmetricKey = personA.getAsymmetricCipher().encrypt(symmetricCipher.getKey().getEncoded(), personA.getPublicKey("David"));
        System.out.printf("SymmetricKey length after  encrypted by personB public key: {%s}\n", new String(encryptedSymmetricKey, StandardCharsets.UTF_8).length());
        byte[] encryptedMessage = symmetricCipher.encrypt(defaultMessage);
        System.out.printf("message length after encrypted by symmetric cipher: {%s}\n", new String(encryptedMessage, StandardCharsets.UTF_8).length());

        // simulate corrupt bit
        // corruptBits(encryptedMessage);

        goToPersonB(personB, encryptedSymmetricKey, encryptedMessage, symmetricCipher.getInitializationVector());

    }

    private static void goToPersonB(Person personB, byte[] encryptedSymmetricKey, byte[] encryptedMessage, byte[] initializationVector) throws Exception {
        // personB receive the encryptedMessage

        byte[] decryptedSymmetricKey = personB.getAsymmetricCipher().decrypt(encryptedSymmetricKey);
        System.out.printf("SymmetricKey length after decrypted by personB private key: {%s}\n", new String(decryptedSymmetricKey, StandardCharsets.UTF_8).length());

        SecretKey secretKey = new SecretKeySpec(decryptedSymmetricKey, AppConstant.AES);

        SymmetricCipher symmetricCipher = new SymmetricCipher(secretKey, AppConstant.NIST_CBC_AES_CIPHER_ALGORITHM, initializationVector);
        String message = symmetricCipher.decrypt(encryptedMessage);
        System.out.printf("message length after decrypted by symmetric cipher: {%s}\n", message.length());

        System.out.println("Decrypted Message: " + message);
    }

    private static void corruptBits(byte[] data) throws NoSuchAlgorithmException {
        // Introduce bit errors by flipping random bits
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        int numBitsToCorrupt = secureRandom.nextInt(data.length * 8);
        for (int i = 0; i < numBitsToCorrupt; i++) {
            int byteIndex = secureRandom.nextInt(data.length);
            int bitIndex = secureRandom.nextInt(8);
            data[byteIndex] ^= (1 << bitIndex);
        }
    }
}
