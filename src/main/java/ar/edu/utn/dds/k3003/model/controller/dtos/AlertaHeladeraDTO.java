package ar.edu.utn.dds.k3003.model.controller.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class AlertaHeladeraDTO {
    private Integer heladeraId;
    private List<RegistroAlerta> alertas;

    public AlertaHeladeraDTO(Integer heladeraId, List<RegistroAlerta> alertas) {
        this.heladeraId = heladeraId;
        this.alertas = alertas;
    }
}

