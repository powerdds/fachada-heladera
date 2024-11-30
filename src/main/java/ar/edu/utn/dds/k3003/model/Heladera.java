package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.model.controller.dtos.TipoAlerta;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

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
    private LocalDateTime fechaInicioFuncionamiento;

    @Column
    private LocalDateTime ultimaConexion;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_id")
    private List<ColaboradorSuscrito> colaboradores;

    public Heladera(String nombre) {
        this.nombre = nombre;
        this.viandas = 0;
        this.capacidadMax = 20;
        this.fechaInicioFuncionamiento = LocalDateTime.now();
        this.activa = true;
        this.temperaturaMin = Float.parseFloat(System.getenv().getOrDefault("TEMPERATURA_MIN", "-18"));
        this.temperaturaMax = Float.parseFloat(System.getenv().getOrDefault("TEMPERATURA_MAX", "4"));
        this.cantidadAperturas = 0;
        this.colaboradores = new ArrayList<>();
        this.tipoIncidente = null;
        this.ultimaConexion=LocalDateTime.now();
    }

    public Heladera() {}

    public void depositarVianda() {

        if (this.viandas < this.capacidadMax) {
            this.cantidadAperturas++;
            this.viandas++;
        } else {
            throw new NoSuchElementException("Se ha llegado a su capacidad maxima");
        }
    }

    public void retirarVianda() {

        if (this.viandas > 0) {
            this.cantidadAperturas++;
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

    public void eliminarColaborador(Integer colaboradorId) {

        Optional<ColaboradorSuscrito> colaboradorAEliminar = this.getColaboradores().stream()
                .filter(colaborador -> Objects.equals(colaborador.getColaboradorId(), colaboradorId))
                .findFirst();

        if (colaboradorAEliminar.isPresent()) {
            this.getColaboradores().remove(colaboradorAEliminar.get());
        } else {
            throw new NoSuchElementException("No se encontró el colaborador con id: " + colaboradorId + " en la heladera " + this.getId());
        }
    }

    public String GetMensajeCapacidad() {

        String ret = "";

        if(this.getCantidadOcupacionActual() == 0){
            ret = "La heladera " + this.getId() +
                    " tiene una capacidad de almacenar " + this.capacidadMax +
                    " viandas y tiene almacenado " + this.viandas +
                    ". Ya no puede almacenar mas viandas";
        } else {
            ret = "La heladera " + this.getId() +
                    " tiene una capacidad de almacenar " + this.capacidadMax +
                    " viandas y tiene almacenado " + this.viandas +
                    ". Puede almacenar " + this.getCantidadOcupacionActual() + " viandas";
        }


        return ret;
    }

    private int getCantidadOcupacionActual() {
        return this.capacidadMax - this.viandas;
    }
}