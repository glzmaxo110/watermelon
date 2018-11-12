package com.xx.watermelon.study.socketdemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * @description: Multicast客户端
 * @author: xiesx
 * @createTime: 2018-11-10 10:54
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public class MulticastClient2 {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress group = InetAddress.getByName("224.5.6.7");

        try {
            MulticastSocket socket = new MulticastSocket(8888);
            //加到指定的组里面
            socket.joinGroup(group);

            byte[] buf = new byte[256];
            while (true) {
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                socket.receive(msgPacket);

                String msg = new String(msgPacket.getData());
                System.out.println("接收到的数据：" + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
