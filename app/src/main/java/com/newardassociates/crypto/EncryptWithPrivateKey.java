package com.newardassociates.crypto;

import javax.crypto.Cipher;
import java.security.PrivateKey;

public class EncryptWithPrivateKey extends Command {
    public static final String ALGORITHM = GenerateAsymmetricKey.ALGORITHM;

    public String command() { return "encprv"; }
    public String args() { return "<PRVKEY> <SRC> <DEST>"; }
    public String description() { return "Encrypt SRC with private key PRVKEY.pub to DEST"; }
    public void execute(String... args) throws Exception {
        String privateKeyArg = args[0];
        String srcArg = args[1];
        String destArg = args[2];

        PrivateKey privateKey = GenerateAsymmetricKey.loadPrivateKey(privateKeyArg);
        byte[] cleartext = Crypto.inSource(srcArg);

        // {{## BEGIN encrypt-with-private-key ##}}
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] cipherdata = cipher.doFinal(cleartext);
        // {{## END encrypt-with-private-key ##}}

        Crypto.outDest(destArg, cipherdata);
    }
}
