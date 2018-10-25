package com.jackmike.telephone;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import com.jackmike.telephone.TELTP;
import java.util.Vector;

public class Server {
	private static boolean VERBOSE = true;
	private int portNum;
	private Vector<TELTPMessage> messages;
	private TELTPMessage currentMessage;
	private TELTP protocol;
		
	
	public TELTP getProtocol() { return this.protocol; }
	public Vector<TELTPMessage> getMessages() { return this.messages; }
	
	public Server(int portNum) throws IOException {
		this.portNum = portNum;
	}
	
	public void startServer() throws IOException {
		System.out.println("SERVER");
		this.protocol = new TELTP();
		
		String data1 = ""; 
		String data2 = ""; 
		String data3 = "";
		String body = ""; // The body of the message that we will continually build
		int parseRespone = -1; // The response of the parse method will be this (1-Header, 0-Part of the body)
		
		System.out.println("Creating server socket on port " + portNum);
		ServerSocket serverSocket = new ServerSocket(portNum);
		
		Socket socket = serverSocket.accept();
		
		// Print the info of the client
		// Do we need to check the source hostname?
		System.out.println("Accept connection from client: " + socket.getRemoteSocketAddress().toString());
	
		OutputStream os = socket.getOutputStream();
		PrintWriter pw = new PrintWriter(os, true);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		// send message to the client
//		pw.println("Connected to the server.");
		// Initiate the handshake
		pw.println("HELLO " + protocol.getVersion());
		System.out.println("Server: HELLO " + protocol.getVersion());
		
		// Check to make su re the client sent the correct handshake pack
		String clientResponse = br.readLine();
		System.out.println("Client: " + clientResponse);
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
				case "DATA": 
//					if (VERBOSE)
//						System.out.println("Here");
					boolean readingData = true;
					while ( readingData ) {
						data1 = br.readLine();
						System.out.println("Client: " + data1);
						if (VERBOSE) {
//							System.out.println("data1: " + data1);
//							System.out.println("data1 == '' " + data1.equals(""));
//							System.out.println("data1.length == " + data1.length());
						}
						if ( data1.equals("") ) {
							// Read the next line to see if it is a "."
							data2 = br.readLine();
							System.out.println("Client: " + data2);
							if ( data2.equals(".") ) { 
								data3 = br.readLine();
								System.out.println("Client: " + data3);
								if (data3.equals("") ) {
									// We are at the end of the DATA message
//									System.out.println("End of DATA message");
									// We send success to the client and break 
//									pw.println("SUCCESS");
//									System.out.println("Server: SUCCESS");
									readingData = false;
								}
								else
									if(parseHeader(data3, protocol) == 0)
										body += data3 + "\n"; // We must append the line to the body of the message
							}
							else
								if(parseHeader(data2, protocol) == 0)
									body += data2 + "\n";
						}
						// Parse all three of the strings read in order
						if( parseHeader(data1, protocol) == 0)
							body += data1 + "\n";
					}
					
					// Does the message body have to come after the headers??
					System.out.println("Message Body: " + body);
					this.protocol.setBody(body);
					
			}
		
			serverSocket.close();

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
	
	private int parseHeader(String header, TELTP protocol) {
		// This method takes the current line being read and returns updates the TELTP class to have 
		// the information corresponding to the headers in the message
		// 1. Split the string at : because the header is in the form
		// 		<Header>: <value>
		// RETURN
		// 	- 0: If the "header" is not a header but is a part of the body of the message
		//	- 1: If the "header" is actually a header
		String[] splitHeader = header.split(":");
		String headerName = splitHeader[0];
		
		switch (headerName) {
			case "Hop":
				int hop = Integer.parseInt(splitHeader[1].trim());
				System.out.println("The current hop is: " + hop);
				protocol.setHop(hop); // Trim removes leading and extra " "
				System.out.println("The hop is now: " + protocol.getHop());
				
				// Update the current message
				this.currentMessage.setHop(hop);
				return 1;
			case "MessageId":
				// If the current message is not void, append it to the messages vector
				this.messages.addElement(this.currentMessage);
				
				int messageID = Integer.parseInt(splitHeader[1].trim());
				
				// We will now create a new current message
				System.out.println("The mssaageId is: " + messageID);
				protocol.setMessageId(messageID);
				// Create a "current message"
				this.currentMessage = new TELTPMessage(messageID);
				return 1;
			case "Author":
				String oldAuthors = splitHeader[1];
				System.out.println("Old Authors: " + oldAuthors);
				protocol.setAuthor(oldAuthors);
				System.out.println("New Authors: " + protocol.getAuthor());
				
				// Update the current message
				this.currentMessage.setAuthor(oldAuthors);
				return 1;
			case "MessageChecksum":
				String checksum = splitHeader[1];
				// Need to make sure the checksum is correct
				return 1;
				
			case "FromHost":
				String fromHost = splitHeader[1];
				// ******** need to add to the protocol
				this.currentMessage.setFromHost(fromHost);
				return 1;
				
			case "System":
				String system = splitHeader[1];
				
				// Update the current message
				this.currentMessage.setSystem(system);
				return 1;
				
			case "Program":
				String program = splitHeader[1];
				this.currentMessage.setProgram(program);
				return 1;
				
			case "SendingTimestamp":
				String sendingTimestamp = splitHeader[1];
				this.currentMessage.setSendingTimestamp(sendingTimestamp);
				return 1;
				
			case "HeadersChecksum":
				String headersChecksum = splitHeader[1];
				this.currentMessage.setHeadersChecksum(headersChecksum);
				return 1;
				
			case "Warning":
				String warning = splitHeader[1];
				this.currentMessage.addWarning(warning);
				return 1;
				
			case "Transform":
				String transform = splitHeader[1];
				this.currentMessage.addTransform(transform);
				return 1;
				
			default:
				// This is a part of the body of the message
				return 0;
				// This is now the message 
		}
	}
}
