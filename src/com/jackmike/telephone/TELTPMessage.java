package com.jackmike.telephone;
import java.util.Vector;


public class TELTPMessage {
	private String fromHost, toHost, system, program, author, sendingTimestamp, messageChecksum, headersChecksum;
	private Vector<String> warnings, transform;
	private int messageID, hop;
	
	public TELTPMessage() {
		this.messageID = 0;
	}
	
	public TELTPMessage(int messageID) {
		this.messageID = messageID;
	}
	
	public void setHop(int hop) { this.hop = hop; }
	public void setFromHost(String fromHost) { this.fromHost = fromHost; }
	public void setToHost (String toHost) { this.toHost = toHost; }
	public void setSystem (String system) { this.system = system; }
	public void setProgram (String program) { this.program = program; }
	public void setAuthor(String author) { this.author = author; }
	public void setSendingTimestamp(String sendingTimestamp) { this.sendingTimestamp = sendingTimestamp; }
	public void setMessageID(int messageID) { this.messageID = messageID; }
	public void setMessageChecksum(String messageChecksum) { this.messageChecksum = messageChecksum; }
	public void setHeadersChecksum(String headersChecksum) { this.headersChecksum = headersChecksum; }
	public void addWarning(String warning) { this.warnings.addElement(warning); }
	public void addTransform(String transform) { this.transform.addElement(transform); }
	
}
