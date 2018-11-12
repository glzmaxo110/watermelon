package com.xx.watermelon.study.socketdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @description: Socket客户端
 * @author: xiesx
 * @createTime: 2018-11-10 11:40
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public class SocketClientDemo {

    public static void main(String[] args) throws IOException {
        try {
            Socket socket = new Socket("localhost", 8888);
            //读取服务端信息
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 往服务端写数据
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello melon");
            // 持续读取服务端数据
            while (true) {
                String serverData = reader.readLine();
                if (serverData == null) {
                    break;
                }
                System.out.println("客户端收到数据：" + serverData);
            }
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
