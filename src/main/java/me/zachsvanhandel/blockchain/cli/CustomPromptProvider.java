package me.zachsvanhandel.blockchain.cli;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class CustomPromptProvider implements PromptProvider {

  @Override
  public AttributedString getPrompt() {
    final String PROMPT_TEXT = "■ simple-blockchain "; // note trailing space
    final AttributedStyle PROMPT_COLOR = AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW);

    return new AttributedString(PROMPT_TEXT, PROMPT_COLOR);
  }

}
