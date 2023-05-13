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

    public AsymmetricCipher(String algorithm) throws NoSuchAlgorithmException {
        this.algorithm = algorithm;
        this.keypair = generateKeyPair();
    }
    public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(this.algorithm);
        keyPairGenerator.initialize(2048, secureRandom);
        return keyPairGenerator.generateKeyPair();
    }

    public byte[] encrypt(byte[] data, PublicKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(this.algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public byte[] decrypt(byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(this.algorithm);
        cipher.init(Cipher.DECRYPT_MODE, this.keypair.getPrivate());
        return cipher.doFinal(data);
    }
}
