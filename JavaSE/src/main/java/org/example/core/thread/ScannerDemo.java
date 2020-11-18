package org.example.core.thread;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

public class ScannerDemo {
    public static void main(String[] args) {
//        Console console = System.console();
        // 控制台对象才可以用console
//        System.err.println("Please input password:\n");
//        char[] chars = console.readPassword();

        ScannerDemo.pathDemo();
    }


    public static void PrintWriterDemo() {
        try {
            // 这边也可以包装为网络流，输出到网络上。
            PrintWriter printWriter = new PrintWriter("a.txt", StandardCharsets.UTF_8);
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            printWriter.println(s);
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void pathDemo(){
        Path of = Path.of("a.txt");
        // D:\Code\JavaEE 项目根目录？？
        String property = System.getProperty("user.dir");
        System.out.println(property);
        System.out.println(of);
    }

}
