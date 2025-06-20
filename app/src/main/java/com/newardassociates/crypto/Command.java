package com.newardassociates.crypto;

public abstract class Command {
    abstract public String command();
    abstract public String args();
    abstract public String description();
    abstract public void execute(String... args) throws Exception;
}

/*
To create a new command, you can extend the Command class like this:

public class ... extends Command {
    public String command() { return "decryptsecret"; }
    public String args() { return ""; }
    public String description() { return ""; }
    public void execute(String... args) throws Exception {
        
    }
}
*/
// You can then add this command to the commands list in Crypto.
// Make sure to implement the execute method with the specific functionality you need.

