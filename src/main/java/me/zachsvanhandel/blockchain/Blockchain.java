package me.zachsvanhandel.blockchain;

import java.util.List;
import java.util.LinkedList;
import org.apache.commons.lang.StringUtils;

public class Blockchain {

  private List<Block> blocks;
  private final int difficulty; // number of leading 0s required for hash values

  public Blockchain(int difficulty) {
    this.blocks = new LinkedList<>();
    this.difficulty = difficulty;
  }

  public void addBlock(String data) {
    String previousHash;
    if (blocks.isEmpty()) {
      previousHash = StringUtils.repeat("0", Block.HASH_SIZE_CHARS); // all 0s for genesis block
    } else {
      previousHash = blocks.get(blocks.size() - 1).getHash();
    }

    String target = createTarget(difficulty);

    Block block = new Block(previousHash, target, data);
    block.mine();
    blocks.add(block); // make sure block is only added after it has been mined
  }

  public boolean isValid() {
    for (int i = 0; i < blocks.size(); i++) {
      String previousHash;
      if (i == 0) {
        previousHash = StringUtils.repeat("0", Block.HASH_SIZE_CHARS);
      } else {
        previousHash = blocks.get(i - 1).getHash();
      }

      Block current = blocks.get(i);
      boolean previousHashMatches = previousHash.equals(current.getPreviousHash());
      boolean currentHashIsCorrect = current.getHash().equals(current.calculateHash());
      boolean currentHashSatisfiesTarget = current.getHash().compareTo(current.getTarget()) <= 0;

      if (!(previousHashMatches && currentHashIsCorrect && currentHashSatisfiesTarget)) {
        return false;
      }
    }

    return true;
  }

  private String createTarget(int difficulty) {
    String leadingZeroes = StringUtils.repeat("0", difficulty);
    String remainingChars = StringUtils.repeat("f", Block.HASH_SIZE_CHARS - difficulty);

    return leadingZeroes + remainingChars;
  }

  public List<Block> getBlocks() {
    return blocks;
  }

  public int getDifficulty() {
    return difficulty;
  }

}
