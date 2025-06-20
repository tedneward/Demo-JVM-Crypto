package com.newardassociates.crypto;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

public class SignWithPrivateKey extends Command {
    public String command() { return "digsgn"; }
    public String args() { return "<PRVKEY> <SRC> <DEST>"; }
    public String description() { return "Digitally sign SRC using PRVKEY.prv to DEST"; }
    public void execute(String... args) throws Exception {
        String privateKeyArg = args[0];
        String srcArg = args[1];
        String destArg = args[2];

        byte[] cleartext = Crypto.inSource(srcArg);
        PrivateKey privateKey = GenerateAsymmetricKey.loadPrivateKey(privateKeyArg);

        // {{## BEGIN signature ##}}
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(cleartext);
        byte[] signature = privateSignature.sign();
        // {{## END signature ##}}

        Crypto.outDest(destArg, Base64.getEncoder().encode(signature));
    }
}