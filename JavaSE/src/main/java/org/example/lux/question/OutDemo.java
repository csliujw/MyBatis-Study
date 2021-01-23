package org.example.lux.question;

interface Inter1 {
    void show();
}

class Student extends Object {
    public static void main(String[] args) {

    }

}

class Outer2 {
    public static Inter1 method() {
        Inter1 i = new Inter1() {
            public void show() {
                System.out.println("HelloWorld");
            }
        };
        return i;
    }
}

public class OutDemo {
    public static void main(String[] args) {
        int number = (int) (Math.random() * 1000);
        if (number % 2 == 0) {
            System.out.println("pdd");
            return;
        }
        System.out.println("淘宝");
    }
}