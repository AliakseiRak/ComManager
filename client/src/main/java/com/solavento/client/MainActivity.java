package com.solavento.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textResponse;
    EditText editTextAddress, editTextPort;
    Button buttonStart, buttonImAlive, buttonStop;
    Button buttonFindSrv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        buttonStart = (Button) findViewById(R.id.start);
        buttonImAlive = (Button) findViewById(R.id.imAlive);
        buttonStop = (Button) findViewById(R.id.stop);
        buttonFindSrv = (Button) findViewById(R.id.findSrv);
        textResponse = (TextView) findViewById(R.id.response);

        OnClickListener commandButtonOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                String ip = editTextAddress.getText().toString();
                int port = Integer.parseInt(editTextPort.getText().toString());
                switch (v.getId()){
                    case R.id.start:
                        msg = "Started";
                        break;
                    case R.id.imAlive:
                        msg = "ImAlive";
                        break;
                    case R.id.stop:
                        msg = "Stopped";
                        break;
                }
                new MessageSender(ip, port, msg).send();
            }
        };

        buttonStart.setOnClickListener(commandButtonOnClickListener);
        buttonImAlive.setOnClickListener(commandButtonOnClickListener);
        buttonStop.setOnClickListener(commandButtonOnClickListener);

        buttonFindSrv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonFindSrv.setEnabled(false);
                (new Thread(new UDPReceiver())).start();
            }
        });
    }

    private class UDPReceiver extends Thread {
        static final int UDP_PORT = 12000;
        String response;

        @Override
        public void run() {

            byte[] message = new byte[1500];

            try {
                DatagramPacket packet = new DatagramPacket(message, message.length);
                DatagramSocket socket = new DatagramSocket(UDP_PORT);
                socket.receive(packet);
                response = new String(message, 0, packet.getLength());
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        editTextAddress.setText(response.substring(0, response.indexOf(':')));
                        editTextPort.setText(response.substring(response.indexOf(':') + 1));
                        buttonFindSrv.setEnabled(true);
                    }
                });
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}