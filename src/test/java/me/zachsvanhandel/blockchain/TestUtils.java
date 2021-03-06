package me.zachsvanhandel.blockchain;

import java.lang.reflect.Field;
import org.apache.commons.lang.StringUtils;

public class TestUtils {

  public static Object getPrivateField(Object o, String fieldName)
      throws IllegalAccessException, NoSuchFieldException
  {
    Object fieldValue;

    Field field = o.getClass().getDeclaredField(fieldName);
    field.setAccessible(true);
    fieldValue = field.get(o);
    field.setAccessible(false);

    return fieldValue;
  }

  public static void setPrivateField(Object o, String fieldName, Object fieldValue)
      throws IllegalAccessException, NoSuchFieldException
  {
    Field field = o.getClass().getDeclaredField(fieldName);
    field.setAccessible(true);
    field.set(o, fieldValue);
    field.setAccessible(false);
  }

  public static String createHashTarget(int difficulty) {
    String leadingZeroes = StringUtils.repeat("0", difficulty);
    String remainingChars = StringUtils.repeat("f", Block.HASH_SIZE_CHARS - difficulty);

    return leadingZeroes + remainingChars;
  }

}
