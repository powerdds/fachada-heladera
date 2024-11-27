package ar.edu.utn.dds.k3003.model.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public final class ColaboradorDTO {
    private Long id;
    private String nombre;
    private List<FormaDeColaborarEnum> formas;
    public Long pesosDonados;
    private Long heladerasReparadas;
    private Long minimoViandas;
    private Long maximoViandas;
    private boolean incidente;

    public ColaboradorDTO(String nombre, List<FormaDeColaborarEnum> formas, Long pesosDonados, Long heladerasReparadas) {
        this.nombre = nombre;
        this.formas = formas;
        this.pesosDonados = pesosDonados;
        this.minimoViandas = -1L;
        this.maximoViandas = -1L;
        this.incidente = false;
        this.heladerasReparadas = heladerasReparadas;
    }

    public void incrementPesosDonados(Long pesos) {
        pesosDonados = pesosDonados + pesos;
    }

    public void incrementHeladerasReparadas() {
        heladerasReparadas++;
    }
}

