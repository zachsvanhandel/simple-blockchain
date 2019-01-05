package me.zachsvanhandel.blockchain;

import java.util.List;
import java.util.LinkedList;
import org.apache.commons.lang.StringUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class TestBlockchain {

  int difficulty;
  Blockchain blockchain;

  @Before
  public void init() {
    difficulty = 2; // low difficulty so tests remain fast
    blockchain = new Blockchain(difficulty);
  }

  @Test
  public void testAddBlock() throws IllegalAccessException, NoSuchFieldException {
    String data = "hello world";
    int numElementsAdded = 0;

    blockchain.addBlock(data);
    numElementsAdded++;
    List<Block> blocks = (List<Block>) TestUtils.getPrivateField(blockchain, "blocks");
    assertEquals(blocks.size(), numElementsAdded);

    Block firstBlock = blocks.get(numElementsAdded - 1);
    String expectedPreviousHash = StringUtils.repeat("0", Block.HASH_SIZE_CHARS);
    String actualPreviousHash = firstBlock.getPreviousHash();
    assertEquals(expectedPreviousHash, actualPreviousHash);

    blockchain.addBlock(data);
    numElementsAdded++;
    blocks = (List<Block>) TestUtils.getPrivateField(blockchain, "blocks");
    assertEquals(blocks.size(), numElementsAdded);

    Block secondBlock = blocks.get(numElementsAdded - 1);
    expectedPreviousHash = firstBlock.getHash();
    actualPreviousHash = secondBlock.getPreviousHash();
    assertEquals(expectedPreviousHash, actualPreviousHash);
  }

  @Test
  public void testIsValid() throws IllegalAccessException, NoSuchFieldException {
    LinkedList<Block> validBlocks = new LinkedList<>();
    String previousHash = StringUtils.repeat("0", Block.HASH_SIZE_CHARS);
    validBlocks.add(createBlock(difficulty, previousHash, true, true));
    TestUtils.setPrivateField(blockchain, "blocks", validBlocks);
    assertTrue(blockchain.isValid()); // valid genesis block counts as a valid chain

    previousHash = validBlocks.get(validBlocks.size() - 1).getHash();
    validBlocks.add(createBlock(difficulty, previousHash, true, true));
    TestUtils.setPrivateField(blockchain, "blocks", validBlocks);
    assertTrue(blockchain.isValid()); // multiple valid blocks count as a valid chain

    LinkedList<Block> invalidBlocks = new LinkedList<>();
    String invalidHash = "zzz";
    invalidBlocks.add(createBlock(difficulty, invalidHash, true, true));
    TestUtils.setPrivateField(blockchain, "blocks", invalidBlocks);
    assertFalse(blockchain.isValid()); // block has invalid previousHash so the chain is invalid

    invalidBlocks = new LinkedList<>();
    previousHash = StringUtils.repeat("0", Block.HASH_SIZE_CHARS);
    invalidBlocks.add(createBlock(difficulty, previousHash, false, true));
    TestUtils.setPrivateField(blockchain, "blocks", invalidBlocks);
    assertFalse(blockchain.isValid()); // block has incorrect hash so the chain is invalid

    invalidBlocks = new LinkedList<>();
    previousHash = StringUtils.repeat("0", Block.HASH_SIZE_CHARS);
    invalidBlocks.add(createBlock(difficulty, previousHash, true, false));
    TestUtils.setPrivateField(blockchain, "blocks", invalidBlocks);
    assertFalse(blockchain.isValid()); // block hash does not satisfy target so the chain is invalid
  }

  @Test
  public void testGetBlocks() throws IllegalAccessException, NoSuchFieldException {
    List<Block> expectedBlocks = (List<Block>) TestUtils.getPrivateField(blockchain, "blocks");
    List<Block> actualBlocks = blockchain.getBlocks();
    assertEquals(expectedBlocks, actualBlocks);
  }

  @Test
  public void testGetDifficulty() throws IllegalAccessException, NoSuchFieldException {
    int expectedDifficulty = (int) TestUtils.getPrivateField(blockchain, "difficulty");
    int actualDifficulty = blockchain.getDifficulty();
    assertEquals(expectedDifficulty, actualDifficulty);
  }

  private Block createBlock(int difficulty, String previousHash, boolean hashIsCorrect,
      boolean hashSatisfiesTarget) throws IllegalAccessException, NoSuchFieldException
  {
    String target = TestUtils.createHashTarget(difficulty);
    String data = "hello world";

    Block block = new Block(previousHash, target, data);
    block.mine();

    if (!hashIsCorrect) {
      String invalidHash = "zzz";
      TestUtils.setPrivateField(block, "hash", invalidHash);
    }

    if (!hashSatisfiesTarget) {
      String unsatisfiableTarget = StringUtils.repeat("0", Block.HASH_SIZE_CHARS);
      TestUtils.setPrivateField(block, "target", unsatisfiableTarget);
    }

    return block;
  }

}
