/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.server.client_handler;

/**
 *
 * @author Ali's PC
 */
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class client_handler implements Runnable {
    private final Socket currentClient;
    private final List<Socket> clientsList;
    private String line = "I Joined to This Server";
    private volatile boolean isSent;
   int clientNo;
    public client_handler(Socket currentClient, List<Socket> clientsList ,int counter) {
        clientNo=counter;
        this.currentClient = currentClient;
        this.clientsList = clientsList;
    }

    @Override
    public void run() {
        try (Scanner in = new Scanner(currentClient.getInputStream(), StandardCharsets.UTF_8)) {
            Thread receivingThread = new Thread(() -> {
                while (true) {
                    if (in.hasNextLine()) {
                        line = in.nextLine();
                        isSent = Boolean.FALSE;
                    }
                }
            });

            receivingThread.start();
            while (true) {
                if (!isSent) {
                    clientsList.stream().filter((client) -> client != currentClient).forEach(client -> {
                        PrintWriter out = null;
                        try {
                            out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(),
                                    StandardCharsets.UTF_8), true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        out.println("client "+clientNo+" : " + line);
                    });
                    isSent = Boolean.TRUE;
                }
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}