package me.zachsvanhandel.blockchain;

import java.util.Date;
import org.apache.commons.lang.StringUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class TestBlock {

  Block block;

  @Before
  public void init() {
    String data = "hello world";
    String previousHash = StringUtils.repeat("0", Block.HASH_SIZE_CHARS); // all 0s

    int difficulty = 2; // low difficulty so tests remain fast
    String target = createHashTarget(difficulty);

    block = new Block(previousHash, target, data);
  }

  @Test
  public void testCalculateHash() throws IllegalAccessException, NoSuchFieldException {
    long testableTimeStamp = 100000;
    TestUtils.setPrivateField(block, "timeStamp", testableTimeStamp);

    String expectedHash = "f127bc494cc044aeeafe3f93cc6a9d3ef0efcc2d7888ba1a999466e5dd76f4d5";
    String calculatedHash = block.calculateHash();
    assertEquals(expectedHash, calculatedHash);
  }

  @Test
  public void testMine() throws IllegalAccessException, NoSuchFieldException {
    block.mine();

    String hash = (String) TestUtils.getPrivateField(block, "hash");
    String target = (String) TestUtils.getPrivateField(block, "target");
    assertTrue(hash.compareTo(target) <= 0); // verify hash is less than target after block is mined
  }

  @Test
  public void testGetHash() throws IllegalAccessException, NoSuchFieldException {
    String expectedHash = (String) TestUtils.getPrivateField(block, "hash");
    String actualHash = block.getHash();
    assertEquals(expectedHash, actualHash);

    String hexStringRegex = "[0-9a-f]{" + Block.HASH_SIZE_CHARS + "}"; // expected hash format
    assertTrue(actualHash.matches(hexStringRegex));
  }

  @Test
  public void testGetPreviousHash() throws IllegalAccessException, NoSuchFieldException {
    String expectedPreviousHash = (String) TestUtils.getPrivateField(block, "previousHash");
    String actualPreviousHash = block.getPreviousHash();
    assertEquals(expectedPreviousHash, actualPreviousHash);

    String hexStringRegex = "[0-9a-f]{" + Block.HASH_SIZE_CHARS + "}"; // expected hash format
    assertTrue(actualPreviousHash.matches(hexStringRegex));
  }

  @Test
  public void testGetTarget() throws IllegalAccessException, NoSuchFieldException {
    String expectedTarget = (String) TestUtils.getPrivateField(block, "target");
    String actualTarget = block.getTarget();
    assertEquals(expectedTarget, actualTarget);

    String hexStringRegex = "[0f]{" + Block.HASH_SIZE_CHARS + "}"; // expected target format
    assertTrue(actualTarget.matches(hexStringRegex));
  }

  @Test
  public void testGetData() throws IllegalAccessException, NoSuchFieldException {
    String expectedData = (String) TestUtils.getPrivateField(block, "data");
    String actualData = block.getData();
    assertEquals(expectedData, actualData);
  }

  @Test
  public void testGetTimeStamp() throws IllegalAccessException, NoSuchFieldException {
    long expectedTimeStamp = (long) TestUtils.getPrivateField(block, "timeStamp");
    long actualTimeStamp = block.getTimeStamp();
    assertEquals(expectedTimeStamp, actualTimeStamp);

    long approximateTime = new Date().getTime();
    long timeDifference = approximateTime - actualTimeStamp;
    long fiveMinutesMilliseconds = 5 * 60 * 1000;
    assertTrue(timeDifference < fiveMinutesMilliseconds); // make sure timeStamp is reasonable
  }

  @Test
  public void testGetNonce() throws IllegalAccessException, NoSuchFieldException {
    long expectedNonce = (long) TestUtils.getPrivateField(block, "nonce");
    long actualNonce = block.getNonce();
    assertEquals(expectedNonce, actualNonce);
  }

  private String createHashTarget(int difficulty) {
    String leadingZeroes = StringUtils.repeat("0", difficulty);
    String remainingChars = StringUtils.repeat("f", Block.HASH_SIZE_CHARS - difficulty);

    return leadingZeroes + remainingChars;
  }

}
