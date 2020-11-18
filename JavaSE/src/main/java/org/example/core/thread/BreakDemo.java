package org.example.core.thread;

public class BreakDemo {
    public static void main(String[] args) {

        out:
        while (true) {
            for (int i = 0; i < 10; i++) {
                if (i == 3) {
                    break out;
                }
            }
        }
        System.out.println("I am out");
    }
}
