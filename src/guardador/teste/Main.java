package guardador.teste;

import guardador.GuardadorDeCsv;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
  private static int contador = 0;

  private static Aluno novoAluno(String matricula, String nome, String curso, double valorMensalidade, String login, String senha) {
    Aluno a = new Aluno();

    a.setId((long)contador++);
    a.setMatricula(matricula);
    a.setNome(nome);
    a.setCurso(curso);
    a.setDataDeIngresso(new Date());
    a.setValorMensalidade(valorMensalidade);
    a.setLogin(login);
    a.setSenha(senha);

    return a;
  }

  public static void main(String[] args) throws Exception {
    List<Aluno> alunos = new ArrayList<>();
    alunos.add(novoAluno("1111", "Hamurabi Medeiros", "SI", 1200.50, "hamurabi.medeiros", "senha"));
    alunos.add(novoAluno("2222", "Bruno de Brito", "SI", 1200.50, "bruno.brito", "senha"));
    alunos.add(novoAluno("3333", "Daniel Abella", "SI", 1200.50, "daniel.abella", "senha"));

    String homeDir = System.getProperty("user.home");
    OutputStream out = new FileOutputStream(new File(homeDir, "alunos.csv"));

    GuardadorDeCsv<Aluno> guardador = new GuardadorDeCsv<>();

    // Gravando
    guardador.salvar(alunos, out);

    InputStream in = new FileInputStream(new File(homeDir, "alunos.csv"));
    // Carregando
    List<Aluno> carregadosDoArquivo = guardador.carregar(Aluno.class, in);
    for (Aluno a : carregadosDoArquivo) {
      System.out.println(a);
    }
  }
}
