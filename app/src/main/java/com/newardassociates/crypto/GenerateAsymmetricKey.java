package com.newardassociates.crypto;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.Signature;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.interfaces.RSAPrivateKey;

public class GenerateAsymmetricKey extends Command {
    public static final String ALGORITHM = "RSA";

    public String command() { return "genasy"; }
    public String args() { return "<KEYNAME>"; }
    public String description() { return "Generate a key pair; store to KEYNAME.pub and KEYNAME.prv"; }
    public void execute(String... args) throws Exception {
        // {{## BEGIN generate-keypair ##}}
        // Generate pub/priv key
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
        kpg.initialize(2048);
        KeyPair pair = kpg.generateKeyPair();
        // {{## END generate-keypair ##}}

        // Store keypair into args[0].keypair
        try (FileOutputStream pairOut = new FileOutputStream(args[0] + ".keypair");
             ObjectOutputStream oos = new ObjectOutputStream(pairOut)) {
            oos.writeObject(pair);
        }
        try (FileOutputStream pubOut = new FileOutputStream(args[0] + ".pub")) {
            pubOut.write(pair.getPublic().getEncoded());
        }
        try (FileOutputStream privOut = new FileOutputStream(args[0] + ".prv")) {
            privOut.write(pair.getPrivate().getEncoded());
        }
    }

    public static PublicKey loadPublicKey(String keyfile) throws Exception {
        try (FileInputStream fis = new FileInputStream(keyfile + ".pub")) {
            byte[] raw = new byte[fis.available()];
            fis.read(raw, 0, fis.available());
            
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            EncodedKeySpec keySpec = new X509EncodedKeySpec(raw);
            PublicKey key = keyFactory.generatePublic(keySpec);
            return key;
        }
    }
    public static PrivateKey loadPrivateKey(String keyfile) throws Exception {
        try (FileInputStream fis = new FileInputStream(keyfile + ".prv")) {
            byte[] raw = new byte[fis.available()];
            fis.read(raw, 0, fis.available());

            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(raw);
            PrivateKey key = keyFactory.generatePrivate(keySpec);
            return key;
        }
    }
}