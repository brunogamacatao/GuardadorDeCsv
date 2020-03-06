package guardador;

import guardador.annotations.Atributo;
import guardador.annotations.Entidade;
import guardador.annotations.Ignorar;
import guardador.exceptions.ClasseNaoEntidadeException;
import guardador.exceptions.DadosInvalidosException;

import java.io.*;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.*;

public class GuardadorDeCsv <T> {
  private List<Field> ordemDosCampos = new ArrayList<>();

  public List<T> carregar(Class<T> classe, InputStream inputStream) throws Exception {
    List<T> retorno = new ArrayList<>();

    validaAnotacoes(classe);

    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    String cabecalho = reader.readLine();
    String[] colunas = cabecalho.split(classe.getAnnotation(Entidade.class).delimitador());
    Map<String, Field> colunaToField = criarMapaDasColunas(classe);

    String linha = null;
    while ((linha = reader.readLine()) != null) {
      T objeto = classe.newInstance();
      String[] valores = linha.split(classe.getAnnotation(Entidade.class).delimitador());

      for (int i = 0; i < valores.length; i++) {
        String coluna = colunas[i];
        Field f = colunaToField.get(coluna);
        f.setAccessible(true);
        if (temAnnotation(f, Atributo.class)) {
          f.set(objeto, f.getAnnotation(Atributo.class).formato().parse(valores[i]));
        } else {
          f.set(objeto, valores[i]);
        }
      }

      retorno.add(objeto);
    }

    reader.close();;

    return retorno;
  }

  private Map<String, Field> criarMapaDasColunas(Class<T> classe) {
    Map<String, Field> colunaToField = new HashMap<>();

    for (Field f : classe.getDeclaredFields()) {
      if (temAnnotation(f, Ignorar.class)) {
        continue;
      } else if (temAnnotation(f, Atributo.class)) {
        String nome = f.getAnnotation(Atributo.class).nome();
        if (nome.trim().length() == 0) {
          nome = f.getName();
        }
        colunaToField.put(nome, f);
      } else {
        colunaToField.put(f.getName(), f);
      }
    }

    return colunaToField;
  }

  public void salvar(Collection<T> col, OutputStream outputStream) {
    boolean primeiraLinha = true;
    PrintWriter out = new PrintWriter(outputStream, true);

    String delimitador = ";";

    try {
      for (T obj : col) {
        if (primeiraLinha) {
          validaAnotacoes(obj.getClass());
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
      } else if (temAnnotation(field, Atributo.class)) {
        Object valorObj = getValorDoCampo(obj, field);
        dadosLinha.add(field.getAnnotation(Atributo.class).formato().format(valorObj));
      } else {
        Object valorObj = getValorDoCampo(obj, field);
        dadosLinha.add(valorObj != null ? valorObj.toString() : "");
      }
    }

    out.println(String.join(delimitador, dadosLinha));
  }

  private Object getValorDoCampo(T obj, Field field) throws IllegalAccessException {
    field.setAccessible(true);
    return field.get(obj);
  }

  private boolean temAnnotation(AnnotatedElement element, Class annotation) {
    return element.getAnnotation(annotation) != null;
  }

  private void escreveCabecalho(T obj, String delimitador, PrintWriter out) {
    List<String> nomesCampos = new ArrayList<>();

    for (Field field : obj.getClass().getDeclaredFields()) {
      if (temAnnotation(field, Ignorar.class)) {
        continue;
      } else if (temAnnotation(field, Atributo.class)) {
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

  private void validaAnotacoes(Class classe) {
    if (!temAnnotation(classe, Entidade.class)) {
      throw new ClasseNaoEntidadeException("A classe " + classe + " não tem a anotação @Entidade");
    }
  }
}
