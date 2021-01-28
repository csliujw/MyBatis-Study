package org.example.core.base;

import java.io.Console;

public class Demo {
    public static void main(String[] args) {
        Console console = System.console();
        String user_name = console.readLine("User name");
        char[] passwords = console.readPassword("Password");
        System.out.println(user_name + ":" + String.valueOf(passwords));
    }
}
