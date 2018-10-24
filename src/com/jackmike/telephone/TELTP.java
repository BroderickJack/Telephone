package com.jackmike.telephone;
import java.util.Vector;

public class TELTP {
	private final String version = "1.7";
	private final int defaultPort = 12345;
	private final String currentAuthor = "MikeJack";
	private String program = "Java/8/Update/191";
	private String system = "Mac OS Mojave10.14";
	
	private int port;
	
	// These are the header data values
	private String fromHost, toHost, author;
	private String sendingTimestamp, messageCSum, headersCSum;
	private String transform;
	private int messageId, hop;
	private Vector<String> warnings; // This is a list of all of the warning that have been added
	
	
	public TELTP() {
		this.port = defaultPort;
	}
	
	public TELTP(int p) {
		this.port = p;
	}

	public String getVersion() { return version; }
	public int getDefaultPort() { return defaultPort; }
	public int getHop() { return this.hop; }
	public int getMesssageId() { return this.messageId; }
	public String getAuthor() { return this.author; }
	
	public void setHop(int currentHop) { this.hop = currentHop + 1; } // Update the hop by 1
	public void setMessageId(int messageId) { this.messageId = messageId; }
	public void setAuthor(String oldAuthors) { this.author = oldAuthors + "/" + this.currentAuthor; }
	public void addWarning(String newWarning) { this.warnings.addElement(newWarning); }
}
