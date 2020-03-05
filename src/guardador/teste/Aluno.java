package guardador.teste;

import guardador.annotations.Atributo;
import guardador.annotations.Entidade;
import guardador.annotations.Formatos;
import guardador.annotations.Ignorar;

import java.util.Date;

@Entidade
public class Aluno {
  @Ignorar
  private Long id;
  private String matricula;
  private String nome;
  private String curso;
  @Atributo(nome = "data_de_ingresso", formato = Formatos.Data)
  private Date dataDeIngresso;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMatricula() {
    return matricula;
  }

  public void setMatricula(String matricula) {
    this.matricula = matricula;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCurso() {
    return curso;
  }

  public void setCurso(String curso) {
    this.curso = curso;
  }

  public Date getDataDeIngresso() {
    return dataDeIngresso;
  }

  public void setDataDeIngresso(Date dataDeIngresso) {
    this.dataDeIngresso = dataDeIngresso;
  }

  @Override
  public String toString() {
    return "Aluno{" +
        "id=" + id +
        ", matricula='" + matricula + '\'' +
        ", nome='" + nome + '\'' +
        '}';
  }
}
