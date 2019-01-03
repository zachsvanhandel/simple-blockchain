package me.zachsvanhandel.blockchain;

import java.util.Date;

public class Block {

  public static final String HASH_ALGORITHM = "SHA-256";
  public static final int HASH_SIZE_BYTES = 32;
  public static final int HASH_SIZE_CHARS = HASH_SIZE_BYTES * 2;

  private String hash;
  private final String previousHash;
  private final String target;
  private final String data;
  private final long timeStamp; // milliseconds since Jan 1, 1970 GMT
  private long nonce;

  public Block(String previousHash, String target, String data) {
    this.previousHash = previousHash;
    this.target = target;
    this.data = data;
    this.timeStamp = new Date().getTime();
    this.nonce = 0;

    this.hash = calculateHash(); // hash should always be calculated last
  }

  public String calculateHash() {
    String blockData = previousHash + data + Long.toString(timeStamp) + Long.toString(nonce);
    return CryptoUtils.hashString(blockData, HASH_ALGORITHM);
  }

  public void mine() {
    while (!(hash.compareTo(target) <= 0)) {
      nonce++;
      hash = calculateHash(); // regenerate hash with new nonce value
    }
  }

  public String getHash() {
    return hash;
  }

  public String getPreviousHash() {
    return previousHash;
  }

  public String getTarget() {
    return target;
  }

  public String getData() {
    return data;
  }

  public long getTimeStamp() {
    return timeStamp;
  }

  public long getNonce() {
    return nonce;
  }

}
