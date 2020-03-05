package guardador.util;

import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class DummyStringFormat extends Format {
  private static DummyStringFormat instance;

  public static DummyStringFormat getInstance() {
    if (instance == null) {
      instance = new DummyStringFormat();
    }

    return instance;
  }

  @Override
  public StringBuffer format(Object o, StringBuffer stringBuffer, FieldPosition fieldPosition) {
    return stringBuffer.append("" + o);
  }

  @Override
  public Object parseObject(String s, ParsePosition parsePosition) {
    parsePosition.setIndex(s.length());
    return s;
  }
}
