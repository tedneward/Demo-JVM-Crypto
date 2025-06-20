package com.newardassociates.crypto;

import javax.crypto.Cipher;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class VerifySignature extends Command {
    public String command() { return "verify"; }
    public String args() { return "<PUBKEY> <SRC> <SIG>"; }
    public String description() { return "Verify SRC against SIG using PUBKEY.pub"; }
    public void execute(String... args) throws Exception {
        String publicKeyArg = args[0];
        String srcArg = args[1];
        String sigArg = args[2];

        PublicKey publicKey = GenerateAsymmetricKey.loadPublicKey(publicKeyArg);
        byte[] cleartext = Crypto.inSource(srcArg);
        byte[] signatureBytes = Base64.getDecoder().decode(Crypto.inSource(sigArg));

        // {{## BEGIN verify ##}}
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(cleartext);
        if (publicSignature.verify(signatureBytes)) {
            System.out.println("Verified!");
        }
        else {
            System.out.println("NO MATCH");
        }
        // {{## END verify ##}}
    }
}