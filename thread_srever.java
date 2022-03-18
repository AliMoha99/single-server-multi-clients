/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.server.client_handler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Ali's PC
 */
public class thread_srever {
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(5051)){
            int counter=0;
            System.out.println("Server Started at Port 5051");
            int clientsNum = 1;
            List<Socket> clientsSockets = new ArrayList<>();
            while (true){
                counter++;
                Socket clientSocket = serverSocket.accept();
                clientsSockets.add(clientSocket);
                System.out.println("Serving "+clientsNum+" Client(s)");
                Runnable clientHandler = new client_handler(clientSocket,clientsSockets , counter);
                Thread clientHandlerThread = new Thread(clientHandler);
                clientHandlerThread.start();
                clientsNum++;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
