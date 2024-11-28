package ar.edu.utn.dds.k3003.model.controller.dtos;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class RegistroAlerta {


    private final TipoAlerta tipoAlerta;
    private final LocalDateTime fechaMedicion;

    public RegistroAlerta(TipoAlerta tipoAlerta, LocalDateTime fechaMedicion) {
        this.tipoAlerta = tipoAlerta;
        this.fechaMedicion = fechaMedicion;
    }
}
