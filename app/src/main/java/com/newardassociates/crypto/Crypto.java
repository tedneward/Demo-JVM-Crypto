package com.newardassociates.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Crypto {

    static void outDest(String dest, byte[] data) throws Exception {
        if (!Objects.equals(dest, "")) {
            try (FileOutputStream fos = new FileOutputStream(dest.substring(1))) {
                fos.write(data);
            }
        }
        else {
            System.out.write(data);
        }
    }
    static byte[] inSource(String src) throws Exception {
        byte[] data;
        if (src.startsWith("@")) {
            File f = new File(src.substring(1));
            int length = (int)f.length();
            data = new byte[length];
            try (FileInputStream fis = new FileInputStream(src.substring(1))) {
                fis.read(data);
            }
        }
        else {
            data = src.getBytes(StandardCharsets.UTF_8);
        }
        return data;
    }

    static List<Command> commands = Arrays.asList(
        new GenerateDigest(),
        new GenerateSecretKey(),
        new EncryptWithSecretKey(),
        new DecryptWithSecretKey(),
        new GenerateAsymmetricKey(),
        new EncryptWithPublicKey(),
        new EncryptWithPrivateKey(),
        new DecryptWithPublicKey(),
        new DecryptWithPrivateKey(),
        new SignWithPrivateKey(),
        new VerifySignature()
    );

    public static void main(String... args) throws Exception {
        if (args.length < 1) {
            usage();
            return;
        }

        String command = args[0];
        String[] argList = Arrays.asList(args).subList(1, args.length).toArray(new String[] {});
        for (Command cmd : commands) {
            if (command.equals(cmd.command())) {
                cmd.execute(argList);
                return;
            }
        }
        System.out.println("Unrecognized command: " + command);
        usage();
    }
    public static void usage() {
        System.out.println("java Crypto [command] [args...]");
        System.out.println();
        System.out.println("... where [command] is one of:");
        System.out.println();
        for (Command c : commands) {
            System.out.println(c.command() + " " + c.args() + ": " + c.description());
        }
        System.out.println("... where SRC is either a quoted string or a filename prefixed with @");
        System.out.println("... and DEST is either stdout or a filename prefixed with @");
    }
}
