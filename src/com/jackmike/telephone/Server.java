package com.jackmike.telephone;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import com.jackmike.telephone.TELTP;

public class Server {
	
	
	public Server(int portNum) throws IOException {

		TELTP protocol = new TELTP();
		
		System.out.println("Creating server socket on port " + portNum);
		ServerSocket serverSocket = new ServerSocket(portNum);
		
		Socket socket = serverSocket.accept();
		
		OutputStream os = socket.getOutputStream();
		PrintWriter pw = new PrintWriter(os, true);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		// send message to the client
//		pw.println("Connected to the server.");
		// Initiate the handshake
		pw.println("HELLO " + protocol.getVersion());
		System.out.println("Server: HELLO " + protocol.getVersion());
		
		// Check to make sure the client sent the correct handshake pack
		String clientResponse = br.readLine();
		String[] output = clientResponse.split(" ");
		
		boolean badResponse = false;
		// Check to see if the there are two parts to the message 
		if(output.length != 2) 
			badResponse = false;
		else if(!output[0].equals("HELLO")) 
			badResponse = false;
		else if(!output[1].equals(protocol.getVersion()))
			badResponse = false;
		
		if (badResponse) {
			System.out.println("Ending the connection");
			socket.close();
		}
		else 
//			System.out.println("Handshake Successful");
		
		// After successful handshake, continually wait for client to send data
		while (true) {
//			System.out.println("Entering loop");
			String str = br.readLine();
			
			System.out.println("Client: " + str);
			
			// Check to see what the input is
			switch (str) {
				case "QUIT": pw.println("GOODBYE");
							 System.out.println("Server: GOODBYE");
							 pw.close();
							 System.exit(1);
			}

//			BufferedReader userInputBR = new BufferedReader(new InputStreamReader(System.in));
//			String userInput = userInputBR.readLine();
//
//			// print our message on the client outgoing br
//			pw.println(userInput);
//			System.out.println("Server: " + userInput);
			
			// dont close anything for now....
			//pw.close();
			//socket.close();

		}
	}
}
