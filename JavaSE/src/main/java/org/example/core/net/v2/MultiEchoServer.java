package org.example.core.net.v2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 多线程 服务器，一个连接对应一个线程
 */
public class MultiEchoServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8189);
        while (true) {
            Socket accept = serverSocket.accept();
            ThreadEchoHandler threadEchoHandler = new ThreadEchoHandler(accept);
            Thread thread = new Thread(threadEchoHandler);
            thread.start();
        }
    }
}

class ThreadEchoHandler implements Runnable {

    private Socket accept;

    public ThreadEchoHandler() {
    }

    public ThreadEchoHandler(Socket accept) {
        this.accept = accept;
    }

    public Socket getAccept() {
        return accept;
    }

    public void setAccept(Socket accept) {
        this.accept = accept;
    }

    @Override
    public void run() {
        try (InputStream inputStream = accept.getInputStream();
             OutputStream outputStream = accept.getOutputStream();) {
            // 获取客户端的输入流
            Scanner in = new Scanner(inputStream, StandardCharsets.UTF_8);
            // 输出流 输出数据到客户端
            PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);
            boolean done = false;
            while (!done && in.hasNextLine()) {
                String ret = in.nextLine();
                out.println("Thread name is: " + Thread.currentThread().getName() + ": " + ret);
                if ("BYE".equals(ret.trim())) {
                    done = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
