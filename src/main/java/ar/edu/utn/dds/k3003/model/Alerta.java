package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.model.controller.dtos.TipoAlerta;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="alerta")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "heladera_id", nullable = false)
    private Heladera heladera;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_alerta", nullable = false)
    private TipoAlerta tipoAlerta;

    public Alerta() {
    }

    public Alerta(Heladera heladera, TipoAlerta tipoAlerta) {
        this.heladera = heladera;
        this.tipoAlerta = tipoAlerta;
    }
}
