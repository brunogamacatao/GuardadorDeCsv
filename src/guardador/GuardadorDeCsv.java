package guardador;

import guardador.annotations.Atributo;
import guardador.annotations.Entidade;
import guardador.annotations.Ignorar;
import guardador.exceptions.ClasseNaoEntidadeException;
import guardador.exceptions.DadosInvalidosException;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.*;

public class GuardadorDeCsv <T> {
  private List<Field> ordemDosCampos = new ArrayList<>();

  public void salvar(Collection<T> col, OutputStream outputStream) {
    boolean primeiraLinha = true;
    PrintWriter out = new PrintWriter(outputStream, true);

    String delimitador = ";";

    try {
      for (T obj : col) {
        if (primeiraLinha) {
          validaAnotacoes(obj);
          delimitador = obj.getClass().getAnnotation(Entidade.class).delimitador();
          escreveCabecalho(obj, delimitador, out);
          primeiraLinha = false;
        }

        try {
          gravarEntidade(obj, delimitador, out);
        } catch (IllegalAccessException e) {
          throw new DadosInvalidosException(e);
        }
      }
    } finally {
      out.close();
    }
  }

  private void gravarEntidade(T obj, String delimitador, PrintWriter out) throws IllegalAccessException {
    List<String> dadosLinha = new ArrayList<>();

    for (Field field : ordemDosCampos) {
      if (field.getAnnotation(Ignorar.class) != null) {
        continue;
      } else if (field.getAnnotation(Atributo.class) != null) {
        field.setAccessible(true);
        Object valorObj = field.get(obj);
        dadosLinha.add(field.getAnnotation(Atributo.class).formato().format(valorObj));
      } else {
        field.setAccessible(true);
        Object valorObj = field.get(obj);
        dadosLinha.add(valorObj != null ? valorObj.toString() : "");
      }
    }

    out.println(String.join(delimitador, dadosLinha));
  }

  private void escreveCabecalho(T obj, String delimitador, PrintWriter out) {
    List<String> nomesCampos = new ArrayList<>();

    for (Field field : obj.getClass().getDeclaredFields()) {
      if (field.getAnnotation(Ignorar.class) != null) {
        continue;
      } else if (field.getAnnotation(Atributo.class) != null) {
        String nome = field.getAnnotation(Atributo.class).nome();
        if (nome.trim().length() == 0) {
          nome = field.getName();
        }
        nomesCampos.add(nome);
      } else {
        nomesCampos.add(field.getName());
      }
      ordemDosCampos.add(field);
    }

    out.println(String.join(delimitador, nomesCampos));
  }

  private void validaAnotacoes(T obj) {
    if (obj.getClass().getAnnotation(Entidade.class) == null) {
      throw new ClasseNaoEntidadeException("A classe " + obj.getClass() + " não tem a anotação @Entidade");
    }
  }
}
