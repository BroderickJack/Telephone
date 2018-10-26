package com.jackmike.telephone;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;


public class TELTPMessage {
	private String fromHost, toHost, system, program, author, sendingTimestamp, headersChecksum;
	private String messageChecksum;
	private Vector<String> warnings = new Vector<String>();
	private Vector<String> transform = new Vector<String>();
	private int messageId, hop;

	public TELTPMessage() {
		this.messageId = 0;
	}

	public TELTPMessage(int messageId) {
		this.messageId = messageId;
	}

	public void setHop(int hop) { this.hop = hop; }
	public void setFromHost(String fromHost) { this.fromHost = fromHost; }
	public void setToHost (String toHost) { this.toHost = toHost; }
	public void setSystem (String system) { this.system = system; }
	public void setProgram (String program) { this.program = program; }
	public void setAuthor(String author) { this.author = author; }
	public void setSendingTimestamp(String sendingTimestamp) { this.sendingTimestamp = sendingTimestamp; }
	public void setMessageID(int messageId) { this.messageId = messageId; }
	public void setMessageChecksum(String messageChecksum) { this.messageChecksum = messageChecksum; }
	public void setHeadersChecksum(String headersChecksum) { this.headersChecksum = headersChecksum; }
	public void addWarning(String warning) { this.warnings.add(warning); }
	public void addTransform(String transform) { this.transform.addElement(transform); }

	public int getHop() { return this.hop; };
	public String getMessageChecksum() { return this.messageChecksum; }
	public String getProgram() { return this.program; }
	public String getSystem() { return this.system; }

	public void sendMessage( PrintWriter pw ) {
//		pw.println("DATA");
//		System.out.println("Client: DATA");
		pw.println("Hop: " + hop);
		System.out.println("Client: Hop: " + hop);
		System.out.println("Client: MessageId: " + messageId);
		pw.println("MessageId: " + messageId);
		System.out.println("Client: FromHost: " + fromHost.trim());
//		System.out.println(fromHost);
//		System.out.println(fromHost.trim());
		pw.println("FromHost: " + fromHost);
		System.out.println("Client: ToHost: " + toHost);
		pw.println("ToHost: " + toHost);
		System.out.println("Client: System: " + system);
		pw.println("System: " + system);
		System.out.println("Client: Program: " + program);
		pw.println("Program: " + program);
		System.out.println("Client: Author: " + author);
		pw.println("Author: " + author);
		System.out.println("Client: SendingTimestamp: " + sendingTimestamp);
		pw.println("SendingTimestamp: " + sendingTimestamp);
		System.out.println("Client: MessageChecksum: " + messageChecksum);
		pw.println("MessageChecksum: " + messageChecksum);
		if(warnings != null) {
			for(int i = 0; i < warnings.size(); i++) {
				pw.println("Warning: " + warnings);
				System.out.println("Client: Warning: " + warnings);
			}
		}

		if( transform != null) {
			pw.println("Transform: " + transform);
			System.out.println("Client: Transform: " + transform);
		}
		pw.println("\n");
		System.out.println("Client: \n");
	}

}
