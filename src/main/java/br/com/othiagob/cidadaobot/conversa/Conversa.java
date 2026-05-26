package br.com.othiagob.cidadaobot.conversa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversas")
public class Conversa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "telefone_usuario", nullable = false, length = 20)
  private String telefoneUsuario;

  @Column(name = "mensagem_usuario", nullable = false, columnDefinition = "TEXT")
  private String mensagemUsuario;

  @Column(name = "resposta_enviada", nullable = false, columnDefinition = "TEXT")
  private String respostaEnviada;

  @Column(name = "intencao_detectada", length = 100)
  private String intencaoDetectada;

  @Column(name = "distrito_detectado", length = 100)
  private String distritoDetectado;

  @Column(name = "data_referencia")
  private LocalDate dataReferencia;

  @Column(nullable = false, length = 50)
  private String origem;

  @Column(name = "criada_em", nullable = false, insertable = false, updatable = false)
  private LocalDateTime criadaEm;

  protected Conversa() {}

  public Conversa(
      String telefoneUsuario,
      String mensagemUsuario,
      String respostaEnviada,
      String intencaoDetectada,
      String distritoDetectado,
      LocalDate dataReferencia,
      String origem) {
    this.telefoneUsuario = telefoneUsuario;
    this.mensagemUsuario = mensagemUsuario;
    this.respostaEnviada = respostaEnviada;
    this.intencaoDetectada = intencaoDetectada;
    this.distritoDetectado = distritoDetectado;
    this.dataReferencia = dataReferencia;
    this.origem = origem;
  }

  public Long getId() {
    return id;
  }

  public String getTelefoneUsuario() {
    return telefoneUsuario;
  }

  public String getMensagemUsuario() {
    return mensagemUsuario;
  }

  public String getRespostaEnviada() {
    return respostaEnviada;
  }

  public String getIntencaoDetectada() {
    return intencaoDetectada;
  }

  public String getDistritoDetectado() {
    return distritoDetectado;
  }

  public LocalDate getDataReferencia() {
    return dataReferencia;
  }

  public String getOrigem() {
    return origem;
  }

  public LocalDateTime getCriadaEm() {
    return criadaEm;
  }
}
