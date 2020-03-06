package guardador.teste;

import guardador.annotations.Atributo;
import guardador.annotations.Entidade;
import guardador.annotations.Formatos;
import guardador.annotations.Ignorar;

import java.text.DateFormat;
import java.util.Date;

@Entidade
public class Aluno {
  @Atributo(formato = Formatos.Inteiro)
  private Long id;
  private String matricula;
  private String nome;
  private String curso;
  @Atributo(nome = "valor_da_mensalidade", formato = Formatos.Moeda)
  private double valorMensalidade;
  @Atributo(nome = "data_de_ingresso", formato = Formatos.Data)
  private Date dataDeIngresso;
  private String login;
  @Ignorar
  private String senha;

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

  public double getValorMensalidade() {
    return valorMensalidade;
  }

  public void setValorMensalidade(double valorMensalidade) {
    this.valorMensalidade = valorMensalidade;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  @Override
  public String toString() {
    return "Aluno{" +
        "id=" + id +
        ", matricula='" + matricula + '\'' +
        ", nome='" + nome + '\'' +
        ", curso='" + curso + '\'' +
        ", valorMensalidade=" + valorMensalidade +
        ", dataDeIngresso=" + DateFormat.getDateInstance().format(dataDeIngresso) +
        ", login='" + login + '\'' +
        '}';
  }
}
