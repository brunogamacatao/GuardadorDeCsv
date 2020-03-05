package guardador.teste;

import guardador.GuardadorDeCsv;
import guardador.annotations.Formatos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
  private static int contador = 0;

  private static Aluno novoAluno(String matricula, String nome, String curso) {
    Aluno a = new Aluno();
    a.setId((long)contador++);
    a.setMatricula(matricula);
    a.setNome(nome);
    a.setCurso(curso);
    a.setDataDeIngresso(new Date());
    return a;
  }

  public static void main(String[] args) throws Exception {
    List<Aluno> alunos = new ArrayList<>();
    alunos.add(novoAluno("1111", "Hamurabi Medeiros", "SI"));
    alunos.add(novoAluno("2222", "Bruno de Brito", "SI"));
    alunos.add(novoAluno("3333", "Daniel Abella", "SI"));

    String homeDir = System.getProperty("user.home");
    OutputStream out = new FileOutputStream(new File(homeDir, "alunos.csv"));

    GuardadorDeCsv<Aluno> guardador = new GuardadorDeCsv<>();
    guardador.salvar(alunos, out);
  }
}
