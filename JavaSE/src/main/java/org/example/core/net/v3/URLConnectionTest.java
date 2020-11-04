package org.example.core.net.v3;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * 获取Web数据
 */
public class URLConnectionTest {
    public static void main(String[] args) throws IOException {
        getHeaders();
    }

    public static void getStream() throws IOException {
        URL url = new URL("http://horstmann.com");
        // 获取资源内容  部分网站有安全措施，无法访问？？
        InputStream inputStream = url.openStream();
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }

    public static void getHeaders() throws IOException {
        URL url = new URL("http://horstmann.com");
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
        Set<String> strings = headerFields.keySet();
        for (String key : strings) {
            System.out.println("key = " + key + "-----------value = " + headerFields.get(key));
        }
    }
}
