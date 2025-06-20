package com.newardassociates.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.crypto.KeyGenerator;

public class DecryptWithSecretKey extends Command {
    public static final String ALGORITHM = GenerateSecretKey.ALGORITHM;

    public String command() { return "decsec"; }
    public String args() { return "<KEY> <SRC> <DEST>"; }
    public String description() { return "Decrypt SRC using secret key KEY.key to DEST"; }
    public void execute(String... args) throws Exception {
        String keyfile = args[0];
        String cipherfile = args[1];
        String dest = args.length > 2 ? args[2] : "";

        byte[] ciphertext = Crypto.inSource(cipherfile);

        SecretKey secretKey = GenerateSecretKey.readSecretKeyFile(keyfile);
        // {{## BEGIN decrypt-with-secret-key ##}}
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] plaindata = cipher.doFinal(ciphertext);
        // {{## END decrypt-with-secret-key ##}}
        Crypto.outDest(dest, plaindata);
    }
}
