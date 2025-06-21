package com.newardassociates.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class EncryptWithSecretKey extends Command {
    public static final String ALGORITHM = GenerateSecretKey.ALGORITHM;

    public String command() { return "encsec"; }
    public String args() { return "<KEY> <SRC> <DEST>"; }
    public String description() { return "Encrypt SRC using secret key KEY writing to DEST"; }
    public void execute(String... args) throws Exception {
        String keyfile = args[0];
        String clear = args[1];
        String dest = args.length > 2 ? args[2] : "";

        SecretKey secretKey = GenerateSecretKey.readSecretKeyFile(keyfile);
        byte[] cleartext = Crypto.inSource(clear);
        // {{## BEGIN encrypt-with-secret-key ##}}
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherdata = cipher.doFinal(cleartext);
        // {{## END encrypt-with-secret-key ##}}
        Crypto.outDest(dest, cipherdata);
    }
}