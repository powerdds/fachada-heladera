package ar.edu.utn.dds.k3003.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="retiro")
public class RegistroRetiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String qrVianda;
    @ManyToOne
    @JoinColumn(name = "heladera_id", nullable = false)
    private Heladera heladera;
    @Column
    private LocalDateTime fechaMedicion;

    public RegistroRetiro(String qrVianda, Heladera heladera) {
        this.qrVianda = qrVianda;
        this.heladera = heladera;
        this.fechaMedicion = LocalDateTime.now();
    }

    public RegistroRetiro() {
    }
}
