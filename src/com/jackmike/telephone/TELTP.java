package com.jackmike.telephone;

public class TELTP {
	private final String version = "1.7";
	private final int defaultPort = 12345;
	
	private int port;
	
	public TELTP() {
		this.port = defaultPort;
	}
	
	public TELTP(int p) {
		this.port = p;
	}

	public String getVersion() { return version; }

}
