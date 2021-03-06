package com.jackmike.telephone;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class InternetChecksum {

  /**
   * Calculate the Internet Checksum of a buffer (RFC 1071 - http://www.faqs.org/rfcs/rfc1071.html)
   * Algorithm is
   * 1) apply a 16-bit 1's complement sum over all octets (adjacent 8-bit pairs [A,B], final odd length is [A,0])
   * 2) apply 1's complement to this final sum
   *
   * Notes:
   * 1's complement is bitwise NOT of positive value.
   * Ensure that any carry bits are added back to avoid off-by-one errors
   *
   *
   * @param buf The message
   * @return The checksum
   */
  public static String calculateChecksum(String msg) {
	byte[] buf = msg.getBytes(Charset.forName("UTF-8"));
    int length = buf.length;
    int i = 0;

    long sum = 0;
    long data;

    // Handle all pairs
    while (length > 1) {
      // Corrected to include @Andy's edits and various comments on Stack Overflow
      data = (((buf[i] << 8) & 0xFF00) | ((buf[i + 1]) & 0xFF));
      sum += data;
      // 1's complement carry bit correction in 16-bits (detecting sign extension)
      if ((sum & 0xFFFF0000) > 0) {
        sum = sum & 0xFFFF;
        sum += 1;
      }

      i += 2;
      length -= 2;
    }

    // Handle remaining byte in odd length buffers
    if (length > 0) {
      // Corrected to include @Andy's edits and various comments on Stack Overflow
      sum += (buf[i] << 8 & 0xFF00);
      // 1's complement carry bit correction in 16-bits (detecting sign extension)
      if ((sum & 0xFFFF0000) > 0) {
        sum = sum & 0xFFFF;
        sum += 1;
      }
    }

    // Final 1's complement value correction to 16-bits
    sum = ~sum;
    sum = sum & 0xFFFF;

    String cs = Long.toHexString(sum);
    // Add padding
	int pad = 4 - cs.length();
	for (int j = 0; j < pad; j++) {
		cs = "0" + cs;
	}
	return cs.toUpperCase();
    
  }


}
