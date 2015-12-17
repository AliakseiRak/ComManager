package com.solavento.client;

import android.os.AsyncTask;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSender {

    private String message;
    private String ipAddress;
    private int port;

    MessageSender(String ipAddress, int port, String message){
        this.ipAddress = ipAddress;
        this.port = port;
        this.message = message;
    }

    public void send() {
        SendMessage sendMessageTask = new SendMessage();
        sendMessageTask.execute();
    }

    private class SendMessage extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Socket socket = new Socket(ipAddress, port);
                PrintWriter printwriter = new PrintWriter(socket.getOutputStream(), true);
                printwriter.write(message);
                printwriter.flush();
                printwriter.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

