package com.jackmike.telephone;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.jackmike.telephone.TELTP;

public class Client {
	
	public Client(String host, int portNum) throws IOException {

		// Create an instance of TELTP object that maintains all protocol information
		TELTP protocol = new TELTP(); 

		System.out.println("CLEINT");
		System.out.println("Creating socket to '" + host + "' on port " + portNum);

		// If port number was not specified
		if (portNum == -1 )
			portNum = protocol.getDefaultPort();
		
		Socket socket = new Socket(host, portNum);

		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);


		// --------------- HANDSHAKE -------------------------------------
		// read from our client incoming br (what the server says on connect)
		String serverResponse = br.readLine();
		System.out.println("Server: " + serverResponse);
		
		// Split the string at the space
		String[] output = serverResponse.split(" ");
		
		boolean badResponse = false;
		// Check to see if the there are two parts to the message 
		if(output.length != 2) 
			badResponse = false;
		else if(!output[0].equals("HELLO")) 
			badResponse = false;
		else if(!output[1].equals(protocol.getVersion()))
			badResponse = false;
		
		// If the version or the handshake is incorrect, then send QUIT
		if (badResponse) {
			out.println("QUIT");
			socket.close();
			System.out.println("Client: QUIT");
			System.exit(1); // Not sure how to handle this 
		}
		else {
			// The server has a matching protocol version
			out.println("HELLO " + protocol.getVersion());
			System.out.println("Client: HELLO " + protocol.getVersion());
		}
		
		// ------------- END OF HANDSHAKE -----------------------------
		
		// -------------- TERMINATION ------------------------
//		System.out.println("Ending the connection");
		out.println("QUIT");
		System.out.println("Client: QUIT");
		serverResponse = br.readLine();
		System.out.println("Server: " + serverResponse);
		
		// The server should send back "GOODBYE";
		if (serverResponse.equals("GOODBYE"))
			System.out.println("Sucessful Termination");
		else
			System.out.println("Error During Termination");
			
	}
}
