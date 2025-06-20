package com.newardassociates.crypto;

import javax.crypto.Cipher;
import java.security.PublicKey;

public class EncryptWithPublicKey extends Command {
    public static final String ALGORITHM = GenerateAsymmetricKey.ALGORITHM;

    public String command() { return "encpub"; }
    public String args() { return "<PUBKEY> <SRC> <DEST>"; }
    public String description() { return "Encrypt SRC with public key PUBKEY.pub to DEST"; }
    public void execute(String... args) throws Exception {
        String publicKeyArg = args[0];
        String srcArg = args[1];
        String destArg = args[2];

        PublicKey publicKey = GenerateAsymmetricKey.loadPublicKey(publicKeyArg);
        byte[] cleartext = Crypto.inSource(srcArg);

        // {{## BEGIN encrypt-with-public-key ##}}
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherdata = cipher.doFinal(cleartext);
        // {{## END encrypt-with-public-key ##}}

        Crypto.outDest(destArg, cipherdata);
    }
}