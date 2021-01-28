package org.example.core.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Scanner;

import static java.lang.System.out;
import static java.lang.System.setOut;

/**
 * 文件的输入与输出
 */
public class FileInputAndOutput {
    public static void ScannerRead() throws IOException {
        Enumeration<URL> resources = FileInputAndOutput.class.getClassLoader().getResources("./myfile.txt");
        // Paths.get 要绝对路径
        Scanner scanner = new Scanner(Paths.get("D:\\Code\\JavaEE\\JavaSE\\src\\main\\java\\org\\example\\core\\base\\myfile.txt"), "UTF-8");
        String s = scanner.nextLine();
        out.println(s);
        PrintWriter printWriter = new PrintWriter("myfile.txt");
        printWriter.write("!23");
        printWriter.flush();
        printWriter.close();
    }

    public static void main(String[] args) throws IOException {
        ScannerRead();
    }
}
