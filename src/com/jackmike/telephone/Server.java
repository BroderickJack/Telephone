package com.jackmike.telephone;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	
	public Server(int portNum) throws IOException {

		System.out.println("Creating server socket on port " + portNum);
		ServerSocket serverSocket = new ServerSocket(portNum);
		
		while (true) {
			Socket socket = serverSocket.accept();
			
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);
			
			// send message to the client
			pw.println("Connected to the server.");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String str = br.readLine();

			// print what was entered by the client
			pw.println(str);
			
			// dont close anything for now....
			//pw.close();
			//socket.close();

		}
	}
}
