package ar.edu.utn.dds.k3003.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "colaborador_suscrito")
public class ColaboradorSuscrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Integer colaboradorId;
    @Column
    private Integer maximoViandas;
    @Column
    private Integer minimoViandas;
    @Column
    private Boolean reportarIncidente;

    public ColaboradorSuscrito() {}

    public ColaboradorSuscrito(Integer colaboradorId, Integer maximoViandas, Integer minimoViandas, Boolean reportarIncidentes) {
        this.colaboradorId = colaboradorId;
        this.maximoViandas = maximoViandas;
        this.minimoViandas = minimoViandas;
        this.reportarIncidente = reportarIncidentes;
    }
}
