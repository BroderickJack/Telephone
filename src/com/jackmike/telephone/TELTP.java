package com.jackmike.telephone;
import java.util.Vector;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.net.InetAddress; // Get the IP address 
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.jackmike.telephone.InternetChecksum;


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
	private String body;
	private Vector<String> warnings = new Vector<String>(); // This is a list of all of the warning that have been added
	
	
	public TELTP() {
		this.port = defaultPort;
	}
	
	public TELTP(int p) throws IOException{
		this.port = p;
		this.hop = 0;
        InetAddress inetAddress = InetAddress.getLocalHost();
//        System.out.println("IP Address:- " + inetAddress.getHostAddress());
		this.fromHost = inetAddress.getHostAddress() + ":" + port;
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
	public void setBody(String body) { this.body = body; }
	
	public void sendMessage( PrintWriter pw ) {
		System.out.println("Here");
		pw.println("Hop: " + hop);
		pw.println("MessageId: " + messageId);
		pw.println("FromHost: " + fromHost);
		pw.println("ToHost: " + toHost);
		pw.println("System: " + system);
		pw.println("Program: " + program);
		pw.println("Author: " + author);
		pw.println("SendingTimestamp: " + getCurrentTime());
		pw.println("MessageChecksum: " + getMessageChecksum());
		for(int i = 0; i < warnings.size(); i++) {
			pw.println("Warnging: " + warnings);
		}
	
		pw.println("Transform: " + transform);
		
	}
	
	public String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
        return sdf.format(cal.getTime());
	}
	
	public String getMessageChecksum() {
		byte[] b = body.getBytes(Charset.forName("UTF-8"));
		long sum = InternetChecksum.calculateChecksum(b);
		byte[] sumBytes = longToBytes(sum);
		return sumBytes.toString();
	}
	
	public byte[] longToBytes(long x) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.putLong(x);
	    return buffer.array();
	}
}
