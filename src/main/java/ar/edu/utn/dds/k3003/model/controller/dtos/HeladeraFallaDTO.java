package ar.edu.utn.dds.k3003.model.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HeladeraFallaDTO {
    private Integer id;
    private String nombre;
    private TipoAlerta tipoAlerta;
}

