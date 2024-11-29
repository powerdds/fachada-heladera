package ar.edu.utn.dds.k3003.model.controller.dtos;

import ar.edu.utn.dds.k3003.model.ColaboradorSuscrito;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HeladeraIdDTO {
    private Integer id;
    private String nombre;
    private Integer cantidadDeViandas;
    private int capacidadMax;
    private boolean activa;
    private TipoAlerta tipoIncidente;
    private List<Integer> colaboradores;
}

