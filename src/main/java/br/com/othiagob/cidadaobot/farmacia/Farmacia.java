package br.com.othiagob.cidadaobot.farmacia;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "farmacias")
public class Farmacia {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 120)
  private String nome;

  @Column(nullable = false, length = 180)
  private String endereco;

  @Column(nullable = false, length = 100)
  private String bairro;

  @Column(nullable = false, length = 100)
  private String distrito;

  @Column(nullable = false, length = 20)
  private String telefone;

  @Column(name = "criada_em", nullable = false, insertable = false, updatable = false)
  private LocalDateTime criadaEm;

  protected Farmacia() {}

  public Farmacia(String nome, String endereco, String bairro, String distrito, String telefone) {
    this.nome = nome;
    this.endereco = endereco;
    this.bairro = bairro;
    this.distrito = distrito;
    this.telefone = telefone;
  }

  public Long getId() {
    return id;
  }

  public String getNome() {

    return nome;
  }

  public String getEndereco() {
    return endereco;
  }

  public String getBairro() {
    return bairro;
  }

  public String getDistrito() {
    return distrito;
  }

  public String getTelefone() {
    return telefone;
  }

  public LocalDateTime getCriadaEm() {
    return criadaEm;
  }
}
