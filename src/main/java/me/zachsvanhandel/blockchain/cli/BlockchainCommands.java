package me.zachsvanhandel.blockchain.cli;

import com.google.gson.GsonBuilder;
import java.util.List;
import me.zachsvanhandel.blockchain.Block;
import me.zachsvanhandel.blockchain.Blockchain;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class BlockchainCommands {

  private Blockchain blockchain;
  private boolean chainCreated = false;

  @ShellMethod(value = "Add new block to current chain.")
  public String add(@ShellOption(help = "Data to be inserted into new block.") String data) {
    blockchain.addBlock(data);

    return "Block added with data:\n" + data;
  }

  @ShellMethod(value = "Print JSON representation of current chain.")
  public String display(
      @ShellOption(help = "Print JSON representation of block at specified index.", defaultValue
          = ShellOption.NULL) Integer block)
  {
    if (block == null) {
      return "Current chain:\n" + toPrettyJson(blockchain);
    }

    List<Block> blocks = blockchain.getBlocks();
    if (block >= 0 && block < blocks.size()) {
      return "Block at index " + Integer.toString(block) + ":\n" + toPrettyJson(blocks.get(block));
    }

    return "Invalid index entered.";
  }

  @ShellMethod(value = "Create new chain.")
  public String init(
      @ShellOption(help = "Number of leading zeroes required for each block's hash.") Integer difficulty)
  {
    blockchain = new Blockchain(difficulty);
    chainCreated = true;

    return "Chain created with difficulty:\n" + Integer.toString(difficulty);
  }

  @ShellMethod(value = "Check if current chain is valid.")
  public String validate() {
    return "Chain is " + (blockchain.isValid() ? "valid." : "not valid.");
  }

  @ShellMethodAvailability({"add", "display", "validate"})
  public Availability isChainCreated() {
    final String UNAVAILABLE_REASON = "you must create a chain before you can use this command";

    return chainCreated ? Availability.available() : Availability.unavailable(UNAVAILABLE_REASON);
  }

  private static String toPrettyJson(Object o) {
    return new GsonBuilder().setPrettyPrinting().create().toJson(o);
  }

}
