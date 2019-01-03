package me.zachsvanhandel.blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class CryptoUtils {

  public static String hashString(String str, String algorithm) {
    try {
      byte[] bytes = str.getBytes(StandardCharsets.UTF_8);

      MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
      byte[] encodedBytes = messageDigest.digest(bytes);

      return bytesToHexString(encodedBytes);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static String bytesToHexString(byte[] bytes) {
    StringBuilder hash = new StringBuilder();
    for (byte b : bytes) {
      String hex = Integer.toHexString(0xff & b);
      if (hex.length() == 1) {
        hash.append('0');
      }
      hash.append(hex);
    }

    return hash.toString();
  }

}
