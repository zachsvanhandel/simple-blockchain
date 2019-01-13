package me.zachsvanhandel.blockchain.cli;

import com.google.gson.GsonBuilder;
import me.zachsvanhandel.blockchain.Block;
import me.zachsvanhandel.blockchain.Blockchain;
import me.zachsvanhandel.blockchain.CryptoUtils;
import me.zachsvanhandel.blockchain.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class TestBlockchainCommands {

  BlockchainCommands blockchainCommands;

  Blockchain currentChain;

  @Before
  public void init() throws IllegalAccessException, NoSuchFieldException {
    blockchainCommands = new BlockchainCommands();

    int difficulty = 2; // low difficulty so tests remain fast
    currentChain = new Blockchain(difficulty);
    TestUtils.setPrivateField(blockchainCommands, "blockchain", currentChain);
  }

  @Test
  public void testAdd() throws IllegalAccessException, NoSuchFieldException {
    String data = "hello world";
    String expectedResult = "Block added with data:\n" + data;
    String actualResult = blockchainCommands.add(data);
    assertEquals(expectedResult, actualResult);

    currentChain = (Blockchain) TestUtils.getPrivateField(blockchainCommands, "blockchain");
    int expectedSize = 1;
    int actualSize = currentChain.getBlocks().size();
    assertEquals(expectedSize, actualSize);

    String expectedData = data;
    String actualData = currentChain.getBlocks().get(0).getData();
    assertEquals(expectedData, actualData);
  }

  @Test
  public void testDisplay() throws IllegalAccessException, NoSuchFieldException {
    String data = "hello world";
    currentChain.addBlock(data);
    TestUtils.setPrivateField(blockchainCommands, "blockchain", currentChain);

    Integer index = null;
    String expectedResult = "Current chain:\n" + toPrettyJson(currentChain);
    String actualResult = blockchainCommands.display(index);
    assertEquals(expectedResult, actualResult);

    index = 0;
    Block firstBlock = currentChain.getBlocks().get(index);
    expectedResult = "Block at index " + index + ":\n" + toPrettyJson(firstBlock);
    actualResult = blockchainCommands.display(index);
    assertEquals(expectedResult, actualResult);

    index = -1; // index < 0
    expectedResult = "Invalid index entered.";
    actualResult = blockchainCommands.display(index);
    assertEquals(expectedResult, actualResult);

    index = 1; // index >= blocks.size()
    expectedResult = "Invalid index entered.";
    actualResult = blockchainCommands.display(index);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testInit() throws IllegalAccessException, NoSuchFieldException {
    int newDifficulty = currentChain.getDifficulty() + 1;
    String expectedResult = "Chain created with difficulty:\n" + Integer.toString(newDifficulty);
    String actualResult = blockchainCommands.init(newDifficulty);
    assertEquals(expectedResult, actualResult);

    currentChain = (Blockchain) TestUtils.getPrivateField(blockchainCommands, "blockchain");
    int expectedSize = 0;
    int actualSize = currentChain.getBlocks().size();
    assertEquals(expectedSize, actualSize);

    int expectedDifficulty = newDifficulty;
    int actualDifficulty = currentChain.getDifficulty();
    assertEquals(expectedDifficulty, actualDifficulty);

    boolean chainCreated = (boolean) TestUtils.getPrivateField(blockchainCommands, "chainCreated");
    assertTrue(chainCreated);
  }

  @Test
  public void testValidate() throws IllegalAccessException, NoSuchFieldException {
    String data = "hello world";
    currentChain.addBlock(data);
    TestUtils.setPrivateField(blockchainCommands, "blockchain", currentChain);

    String expectedResult = "Chain is valid.";
    String actualResult = blockchainCommands.validate();
    assertEquals(expectedResult, actualResult);

    Block block = currentChain.getBlocks().get(0);
    TestUtils.setPrivateField(block, "data", "invalid data"); // make block invalid
    expectedResult = "Chain is not valid.";
    actualResult = blockchainCommands.validate();
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testIsChainCreated() throws IllegalAccessException, NoSuchFieldException {
    TestUtils.setPrivateField(blockchainCommands, "chainCreated", false);
    boolean result = blockchainCommands.isChainCreated().isAvailable();
    assertFalse(result);

    TestUtils.setPrivateField(blockchainCommands, "chainCreated", true);
    result = blockchainCommands.isChainCreated().isAvailable();
    assertTrue(result);
  }

  private static String toPrettyJson(Object o) {
    return new GsonBuilder().setPrettyPrinting().create().toJson(o);
  }

}
