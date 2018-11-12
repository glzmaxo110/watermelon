package com.xx.watermelon.study.socketdemo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description: Socket服务端
 * @author: xiesx
 * @createTime: 2018-11-10 10:54
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public class SocketServerDemo {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            //启动一个服务
            serverSocket = new ServerSocket(8888);
            while (true) {
                //等待一个接收请求连接
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        //读取数据
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        //发送数据
                        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

                        while (true) {
                            //读取客户端发送过来的消息
                            String clientData = reader.readLine();
                            if (clientData == null) {
                                break;
                            }
                            System.out.println("服务端接收到的数据：" + clientData);
                            writer.println("Hello xx ");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }

}