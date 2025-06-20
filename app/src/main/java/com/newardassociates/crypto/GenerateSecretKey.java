package com.newardassociates.crypto;

import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateSecretKey extends Command {
    public static final String ALGORITHM = "AES";
    
    public String command() { return "gensec"; }
    public String args() { return "<KEY>"; }
    public String description() { return "Generate a secret key into KEY.key"; }
    public void execute(String... args) throws Exception {
        // {{## BEGIN generate-secret-key ##}}
        // Generate secret key
        KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
        keygen.init(128);
        SecretKey aesKey = keygen.generateKey();
        
        // Store into args[0]
        try (FileOutputStream fos = new FileOutputStream(args[0] + ".key")) {
            byte[] rawKey = aesKey.getEncoded();
            fos.write(rawKey);
        }
        // {{## END generate-secret-key ##}}
    }

    public static SecretKey readSecretKeyFile(String file) throws Exception {
        // Load out of file
        try (FileInputStream fis = new FileInputStream(file + ".key")) {
            byte[] rawKey = new byte[fis.available()];
            fis.read(rawKey, 0, fis.available());
            SecretKeySpec keySpec = new SecretKeySpec(rawKey, ALGORITHM);
            return keySpec;
        }
    }
}