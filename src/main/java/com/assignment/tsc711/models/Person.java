package com.assignment.tsc711.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Person {
    private String name;

    private AsymmetricCipher asymmetricCipher;

    private SymmetricCipher symmetricCipher;

    private Map<String, PublicKey> publicKeyMap = new HashMap<>();

    public Person(String name, String algorithm) throws NoSuchAlgorithmException {
        this.name = name;
        this.asymmetricCipher = new AsymmetricCipher(algorithm);
    }

    public void addPublicKey(String name, PublicKey key) {
        this.publicKeyMap.put(name, key);
    }

    public PublicKey getPublicKey(String name) throws Exception {
        if (this.publicKeyMap.containsKey(name)) {
            return this.publicKeyMap.get(name);
        }else{
            throw new Exception("Key not found");
        }
    }
}
