package com.newardassociates.crypto;

import javax.crypto.Cipher;
import java.security.PrivateKey;

public class DecryptWithPrivateKey extends Command {
    public static final String ALGORITHM = GenerateAsymmetricKey.ALGORITHM;

    public String command() { return "decprv"; }
    public String args() { return "<PRVKEY> <SRC> <DEST>"; }
    public String description() { return "Decrypt SRC with private key PRVKEY.prv to DEST"; }
    public void execute(String... args) throws Exception {
        String privateKeyArg = args[0];
        String srcArg = args[1];
        String destArg = args[2];

        PrivateKey privateKey = GenerateAsymmetricKey.loadPrivateKey(privateKeyArg);
        byte[] cleartext = Crypto.inSource(srcArg);

        // {{## BEGIN decrypt-with-private-key ##}}
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] cipherdata = cipher.doFinal(cleartext);
        // {{## END decrypt-with-private-key ##}}

        Crypto.outDest(destArg, cipherdata);
    }
}
