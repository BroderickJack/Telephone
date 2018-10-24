package com.jackmike.telephone;

import java.io.IOException;

import com.jackmike.telephone.Client;
import com.jackmike.telephone.Server;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			
		
		Client c1;
		Server s1;
		if(originator)
			try {
				c1 = new Client(destHost, destPort);
				c1.startClient();
			} catch (IOException e) {
				e.printStackTrace();
			}
		else {
			while(true) {
				try {
					s1 = new Server(sourcePort);
					s1.startServer();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// Now we need to get the protocol information about the old message/
				// Switch to a client and pass the message to the next person in the line 
			}
		}
		
		
		
		System.out.println(originator + ":" + sourceIP + ":" + destIP + ":" + port);
	}
	
	public static boolean isIPV4( String host ) {
		// This method returns true if the argument "host" is an IPV4 address
		// It returns false if host is a <hostname>	
		return false;
	}

}
