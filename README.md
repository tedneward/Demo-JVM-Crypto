# Demo-JVM-Crypto
A collection of demos around how to do cryptography in the JVM

The organization of the code here is that of a "crypto tool" that is built up out of a series of standalone "commands", each of which perform a single cryptographic action, such as "encrypting plaintext using a symmetric key". This felt more aligned with what people were asking when discussing cryptography, and manages to help segregate the actions just enough to make for clear demos, yet keep them close enough to one another to see the relationships.

## TODO

* Demonstrate KeyStore usage
* Demonstrate CipherInputStream/CipherOutputStream
* Demonstrate MessageDigest
* Some tests that demonstrate how SSL works (use public/private keys to verify identity, then exchange a secret key to pass data back and forth)


