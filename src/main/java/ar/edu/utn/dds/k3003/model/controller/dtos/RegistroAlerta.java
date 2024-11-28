package ar.edu.utn.dds.k3003.model.controller.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class RegistroAlerta {


    private final TipoAlerta tipoAlerta;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    )
    private final LocalDateTime fechaMedicion;

    public RegistroAlerta(TipoAlerta tipoAlerta, LocalDateTime fechaMedicion) {
        this.tipoAlerta = tipoAlerta;
        this.fechaMedicion = fechaMedicion;
    }
}
