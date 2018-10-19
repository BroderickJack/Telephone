package com.jackmike.telephone;

import java.io.IOException;

import com.jackmike.telephone.Client;
import com.jackmike.telephone.Server;

public class Main {

	public static void main(String[] args) {
		
		// random stuff v
		System.out.println("This is a test");
		int len = args.length;
		System.out.println("Number of args: " + len);
		// end of random stuff ^
		
		// format of args: <originator> <sourceIP> <destIP> <port>
		// 1 is a client, 0 is a server
		boolean validArgs = true;
		boolean originator = false;
		String sourceIP = "-";
		String destIP = "-";
		int port = 0;
		
		if(args.length == 4) {
			originator = ((args[0].equals("0"))) ? false : true;
			sourceIP = args[1];
			destIP = args[2];
			port = Integer.parseInt(args[3]);
		}
		else
			validArgs = false;
			
		
		Client c1;
		Server s1;
		if(originator)
			try {
				c1 = new Client(sourceIP, port);
			} catch (IOException e) {
				e.printStackTrace();
			}
		else
			try {
				s1 = new Server(port);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		
		
		System.out.println(originator + ":" + sourceIP + ":" + destIP + ":" + port);
	}

}
