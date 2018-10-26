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
	
	
	public TELTP() throws IOException{
		this.port = defaultPort;
        InetAddress inetAddress = InetAddress.getLocalHost();
//        System.out.println("IP Address:- " + inetAddress.getHostAddress());
		this.fromHost = inetAddress.getHostAddress() + ":" + port;
		this.author = currentAuthor;
	}
	
	public TELTP(int p) throws IOException{
		this.port = p;
		this.hop = 0;
        InetAddress inetAddress = InetAddress.getLocalHost();
//        System.out.println("IP Address:- " + inetAddress.getHostAddress());
		this.fromHost = inetAddress.getHostAddress() + ":" + port;
		this.author = currentAuthor;
	}

	public String getVersion() { return version; }
	public int getDefaultPort() { return defaultPort; }
	public int getHop() { return this.hop; }
	public int getMesssageId() { return this.messageId; }
	public String getAuthor() { return this.author; }
	
	public void setHop(int currentHop) { this.hop = currentHop + 1; } // Update the hop by 1
	public void setMessageId(int messageId) { this.messageId = messageId; }
	public void setAuthor(String author) { this.author = author; }
	public void addWarning(String newWarning) { this.warnings.addElement(newWarning); }
	public void setBody(String body) { this.body = body; }
	public void setToHost(String toHost) { this.toHost = toHost; }
	
	public void sendMessage( PrintWriter pw ) {
//		pw.println("DATA");
//		System.out.println("Client: DATA");
		pw.println("Hop: " + hop);
		System.out.println("Client: Hop: " + hop);
		System.out.println("Client: MessageId: " + messageId);
		pw.println("MessageId: " + messageId);
		System.out.println("Client: FromHost: " + fromHost);
		pw.println("FromHost: " + fromHost);
		System.out.println("Client: ToHost: " + toHost);
		pw.println("ToHost: " + toHost);
		System.out.println("Client: System: " + system);
		pw.println("System: " + system);
		System.out.println("Client: Program: " + program);
		pw.println("Program: " + program);
		System.out.println("Client: Author: " + author);
		pw.println("Author: " + author);
		String ts = getCurrentTime();
		System.out.println("Client: SendingTimestamp: " + ts);
		pw.println("SendingTimestamp: " + ts);
		String cs = InternetChecksum.calculateChecksum(body);
		System.out.println("Client: MessageChecksum: " + cs);
		pw.println("MessageChecksum: " + cs);
		for(int i = 0; i < warnings.size(); i++) {
			pw.println("Warning: " + warnings);
			System.out.println("Client: Warning: " + warnings);
		}
	
		if( transform != null) {
			pw.println("Transform: " + transform);
			System.out.println("Client: Transform: " + transform);
		}
		pw.println(this.body); // Send the body of the message
		System.out.println("Client: " + this.body); // Send the body of the message
		pw.println("\n.\n");
		System.out.println("Client: \nClient: . \nClient:");
		
		
		
	}
	
	public String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
        return sdf.format(cal.getTime());
	}
	
}
