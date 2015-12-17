package com.netutils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPTransmitter extends Thread {
    static final int UDP_PORT = 12000;
    static final long PERIOD = 2000;

    @Override
    public void run() {
        //String data = getIp() + ":" + serverSocket.getLocalPort();
        while (true) {
            try {
                DatagramSocket socket = new DatagramSocket();
                socket.setBroadcast(true);
                //DatagramPacket packet = new DatagramPacket(data.getBytes(), data.length(),getBroadcastAddress(), UDP_PORT);
                //socket.send(packet);
                sleep(PERIOD);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}