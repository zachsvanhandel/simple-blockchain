package me.zachsvanhandel.blockchain.cli;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class TestCustomPromptProvider {

  CustomPromptProvider customPromptProvider;

  @Before
  public void init() {
    customPromptProvider = new CustomPromptProvider();
  }

  @Test
  public void testGetPrompt() {
    String promptText = "■ simple-blockchain "; // note trailing space
    AttributedStyle promptColor = AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW);
    AttributedString expectedResult = new AttributedString(promptText, promptColor);
    AttributedString actualResult = customPromptProvider.getPrompt();
    assertEquals(expectedResult, actualResult);
  }

}
