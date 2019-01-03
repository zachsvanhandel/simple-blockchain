package me.zachsvanhandel.blockchain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class TestCryptoUtils {

  static final String SHA256_ALGORITHM = "SHA-256";

  @Test
  public void testHashString() {
    String s = "hello world";

    String expectedHash = "b94d27b9934d3e08a52e52d7da7dabfac484efe37a5380ee9088f7ace2efcde9";
    String actualHash = CryptoUtils.hashString(s, SHA256_ALGORITHM);
    assertEquals(expectedHash, actualHash);
  }

  @Test(expected = RuntimeException.class)
  public void testHashStringInvalidAlgorithm() {
    String s = "hello world";

    String invalidAlgorithm = "ABC";
    CryptoUtils.hashString(s, invalidAlgorithm); // should throw RuntimeException
  }

}
