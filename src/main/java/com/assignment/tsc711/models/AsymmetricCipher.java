package com.assignment.tsc711.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

@Getter
@Setter
@NoArgsConstructor
public class AsymmetricCipher {
    private String algorithm = "RSA";
    private KeyPair keypair;

    // default algorithm is RSA and will automatically generate key pair when initialized
    public AsymmetricCipher(String algorithm) throws NoSuchAlgorithmException {
        this.algorithm = algorithm;
        this.keypair = generateKeyPair();
    }

    // This method will generate a new key pair for astymmetric encryption and decryption
    // using the specific algorithm, default is RSA
    // the keysize is defined as 2048 bits, which is the recommended key size for RSA
    // the SecureRandom class is for strong randomness of key generation
    public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(this.algorithm);
        keyPairGenerator.initialize(2048, secureRandom);
        return keyPairGenerator.generateKeyPair();
    }

    // This method initializes the cipher with RSA algorithm and encrypts the data
    // the cipher is init in encrypt mode with provided public key
    // the data is encrypted and returned as byte array
    public byte[] encrypt(byte[] data, PublicKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(this.algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    // This method initializes the cipher with RSA algorithm and decrypt the data
    // the cipher is init in decrypt mode with provided private key
    // the data is decrypted and returned as byte array
    public byte[] decrypt(byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(this.algorithm);
        cipher.init(Cipher.DECRYPT_MODE, this.keypair.getPrivate());
        return cipher.doFinal(data);
    }
}
