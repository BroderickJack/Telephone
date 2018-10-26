package com.jackmike.telephone;

import java.io.IOException;
import java.nio.charset.Charset;

import com.jackmike.telephone.Client;
import com.jackmike.telephone.Server;
import com.jackmike.telephone.TELTP;
import com.jackmike.telephone.InternetChecksum;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.Vector;

public class Main {

	public static void main(String[] args) {
		// random stuff v
//		System.out.println("This is a test");
		int len = args.length;
//		System.out.println("Number of args: " + len);
		// end of random stuff ^

		// format of args: <originator> <sourceIP> <destIP> <port>
		// 1 is a client, 0 is a server
		boolean validArgs = true;
		boolean originator = false;
		String sourceIP = "-";
		String sourceEndpoint, sourceHost;
		int sourcePort = -1;
		String destEndpoint;
		String destHost = "";
		String destIP = "-";
		int destPort = -1;
		int port = 0;

		if(args.length == 3) {
			originator = ((args[0].equals("0"))) ? false : true;
			sourceEndpoint = args[1];
			// Parse the sourceIP - Could be either just a host or IP and port
			// Host could be IPV4 address or a hostname
			if (sourceEndpoint.contains(":")) {
				// Then there is a port
				String[] sourceInfo = sourceEndpoint.split(":");
				sourceHost = sourceInfo[0];
				sourcePort = Integer.parseInt(sourceInfo[1]);
			}
			else {
				sourceHost = sourceEndpoint;
				sourcePort = -1; // This will force the client to use the default port
			}

//			// We must now parse the host
//			// * Do we want parsing the <endpoint> to be it's own method that returns either
//			// a Client or a Server object??
//			String regExp = "\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}";
//			boolean b = Pattern.matches(regExp,  sourceHost);
//
//			if (b) {
//				// It is an IPV4 address
//				System.out.println("Source Host: IPV4");
//			}
//			else
//				System.out.println("Source Host: Not IPV4");

			// Parse the DESTINATION end point
			destEndpoint = args[2];
			// Could be either just a host or IP and port
			// Host could be IPV4 address or a host name
			if ( destEndpoint.contains(":")) {
				// Then there is a port
				String[] destInfo = destEndpoint.split(":");
				destHost = destInfo[0];
				destPort = Integer.parseInt(destInfo[1]);
			}
			else {
				destHost = destEndpoint;
				destPort = -1; // This will force the client to use the default port
			}
			destIP = destHost;
//			port = Integer.parseInt(args[3]);
		}
		else
			validArgs = false;


		Client c1, c2;
		Server s1;
		// We can try to handle multiple instances running
		int messageId = 0;
		if(originator) {
			// Create the original message to send
			try {
				// Create the client
				c1 = new Client(destHost, destPort);

				// Create the message
				TELTP m = new TELTP(destPort);
				m.setMessageId(messageId);
				m.setBody("This is the message.\n From Telephone!");
				m.setToHost(c1.getEndpoint());

				// Send the message
				c1.startClient();
				c1.sendMessage(m);

				// Turn back into a server
				s1 = new Server(sourcePort);
				s1.startServer();

				analyzeMessages(s1.getMessages());
//				System.out.println("Finished recieving data");
				// Now we need to print out data
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		else {
//			while(true) {
				try {
					s1 = new Server(sourcePort);
					s1.startServer();
					// Now we need to get the protocol information about the old message/
					// Switch to a client and pass the message to the next person in the line
//					System.out.println("The server ended");
					Vector<TELTPMessage> messages = s1.getMessages();
					TELTP p = s1.getProtocol();
					// We need to create a client to send the message to the next server
					c2 = new Client(destHost, destPort);
					c2.startClient();
//					System.out.println("Started the client again");

//					System.out.println("Size: " + messages.size());
					for(int i = 0; i < messages.size(); i++) {
//						System.out.println("SENDING MESSAGE");
						c2.sendMessage(messages.get(i));
					}
					// Send the information for the current hop
//					System.out.println("Sending the protocol");

					p.setToHost(c2.getEndpoint());
					c2.sendMessage(p);
				} catch (IOException e) {
					e.printStackTrace();
				}
//			}
		}



//		System.out.println(originator + ":" + sourceIP + ":" + destIP + ":" + port);
	}

	public static void analyzeMessages(Vector<TELTPMessage> messages) {
		int numHops = 0;
		String lan, plat;
		Vector<String> languages = new Vector<String>();
		Vector<String> platforms = new Vector<String>();

		for(int i = 0; i < messages.size(); i++) {
			numHops++;
			TELTPMessage m = messages.get(i);
			lan = m.getProgram();
			plat = m.getSystem();

			// Make sure they aren't already in the vector
			if (!languages.contains(lan) )
				languages.add(lan);
			if (!platforms.contains(plat) )
				platforms.add(plat);
		}

		System.out.println("------ STATISTICS ------");
		System.out.println("# of Hops: " + numHops);

		System.out.print("Languages: ");
		for ( int i = 0; i < languages.size()-1; i++ ) {
			System.out.print(languages.get(i) + "; ");
		}
		System.out.println(languages.get(languages.size()-1));

		System.out.print("Platforms: ");
		for ( int i = 0; i < platforms.size()-1; i++ ) {
			System.out.print(platforms.get(i) + "; ");
		}
		System.out.println(platforms.get(platforms.size()-1));

	}

	public static boolean isIPV4( String host ) {
		// This method returns true if the argument "host" is an IPV4 address
		// It returns false if host is a <hostname>
		return false;
	}

}
