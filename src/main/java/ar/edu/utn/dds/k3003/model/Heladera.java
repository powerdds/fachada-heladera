package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.model.controller.dtos.TipoAlerta;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Data
@Entity
@Table(name = "heladera")
public class Heladera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar")
    private String nombre;
    @Column
    private int viandas;
    @Column
    private int capacidadMax;
    @Column
    private LocalDate fechaInicioFuncionamiento;

    @Column
    private LocalDate ultimaConexion;
    @Column
    private boolean activa;
    @Column
    private float temperaturaMin;
    @Column
    private float temperaturaMax;
    @Column
    private int cantidadAperturas;
    @Column(name = "Tipo_falla")
    private TipoAlerta tipoIncidente;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "heladera_id")
    private List<ColaboradorSuscrito> colaboradores;

    public Heladera(String nombre) {
        this.nombre = nombre;
        this.viandas = 0;
        this.capacidadMax = 20;
        this.fechaInicioFuncionamiento = LocalDate.now();
        this.activa = true;
        this.temperaturaMin = Float.parseFloat(System.getenv().getOrDefault("TEMPERATURA_MIN", "-18"));
        this.temperaturaMax = Float.parseFloat(System.getenv().getOrDefault("TEMPERATURA_MAX", "4"));
        this.cantidadAperturas = 0;
        this.colaboradores = new ArrayList<>();
        this.tipoIncidente = null;
        this.ultimaConexion=LocalDate.now();
    }

    public Heladera() {}

    public void depositarVianda() {
        this.viandas++;
        this.cantidadAperturas++;
    }

    public void retirarVianda() {
        this.cantidadAperturas++;
        if (this.viandas > 0) {
            this.viandas--;
        } else {
            throw new NoSuchElementException("No hay viandas para remover");
        }
    }

    public void reparar() {
        this.activa = true;
        this.tipoIncidente = null;
    }

    public void falla(TipoAlerta tipoIncidente) {
        this.activa = false;
        this.tipoIncidente = tipoIncidente;
    }

    public void agregarSuscriptor(ColaboradorSuscrito colaboradorSuscrito) {

        var colaborador = this.colaboradores.stream()
                .filter(colaboradorSuscritoAux -> Objects.equals(colaboradorSuscritoAux.getColaboradorId(), colaboradorSuscrito.getColaboradorId()))
                .findFirst();

        if(colaborador.isEmpty()){
            this.colaboradores.add(colaboradorSuscrito);
        } else {
            colaborador.get().setMaximoViandas(colaboradorSuscrito.getMaximoViandas());
            colaborador.get().setMinimoViandas(colaboradorSuscrito.getMinimoViandas());
            colaborador.get().setReportarIncidente(colaboradorSuscrito.getReportarIncidente());
        }
    }
}