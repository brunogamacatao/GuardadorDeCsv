package guardador.annotations;

import guardador.exceptions.FormatoInvalidoException;
import guardador.util.DummyStringFormat;

import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public enum Formatos {
  String(DummyStringFormat.getInstance()),
  Data(new SimpleDateFormat("dd/MM/yyyy")),
  DataHora(new SimpleDateFormat("dd/MM/yyyy HH:mm")),
  Inteiro(NumberFormat.getIntegerInstance()),
  Moeda(NumberFormat.getCurrencyInstance());

  private Format formatter;

  Formatos(Format formatter) {
    this.formatter = formatter;
  }

  public String format(Object value) {
    return this.formatter.format(value);
  }

  public Object parse(String str) {
    try {
      return this.formatter.parseObject(str);
    } catch (ParseException e) {
      throw new FormatoInvalidoException(e);
    }
  }
}
