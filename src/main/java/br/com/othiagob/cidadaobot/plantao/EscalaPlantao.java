package br.com.othiagob.cidadaobot.plantao;

import br.com.othiagob.cidadaobot.farmacia.Farmacia;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "escala_plantao")
public class EscalaPlantao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "farmacia_id", nullable = false)
  private Farmacia farmacia;

  @Column(name = "data_plantao", nullable = false)
  private LocalDate dataPlantao;

  @Column(name = "inicia_as", nullable = false)
  private LocalTime iniciaAs;

  @Column(name = "termina_as", nullable = false)
  private LocalTime terminaAs;

  @Column(name = "observacoes")
  private String observacoes;

  @Column(name = "criado_em", nullable = false, insertable = false, updatable = false)
  private LocalDateTime criadoEm;

  protected EscalaPlantao() {
  }

  public EscalaPlantao(
      Farmacia farmacia,
      LocalDate dataPlantao,
      LocalTime iniciaAs,
      LocalTime terminaAs,
      String observacoes
  ) {
    this.farmacia = farmacia;
    this.dataPlantao = dataPlantao;
    this.iniciaAs = iniciaAs;
    this.terminaAs = terminaAs;
    this.observacoes = observacoes;
  }

  public Long getId() {
    return id;
  }

  public Farmacia getFarmacia() {
    return farmacia;
  }

  public LocalDate getDataPlantao() {
    return dataPlantao;
  }

  public LocalTime getIniciaAs() {
    return iniciaAs;
  }

  public LocalTime getTerminaAs() {
    return terminaAs;
  }

  public String getObservacoes() {
    return observacoes;
  }

  public LocalDateTime getCriadoEm() {
    return criadoEm;
  }
}
