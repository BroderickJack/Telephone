package com.jackmike.telephone;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	
	public Client(String host, int portNum) throws IOException {


		System.out.println("Creating socket to '" + host + "' on port " + portNum);

		while (true) {
			Socket socket = new Socket(host, portNum);

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			// read from our client incoming br (what the server says on connect)
			System.out.println("Server says: " + br.readLine());

			BufferedReader userInputBR = new BufferedReader(new InputStreamReader(System.in));
			String userInput = userInputBR.readLine();

			// print our message on the client outgoing br
			out.println("Client says: " + userInput);
			
			System.out.println(br.readLine());

			// end process on keyword exit
			if ("exit".equalsIgnoreCase(userInput)) {
				socket.close();
				break;
			}
		}
	}
}
