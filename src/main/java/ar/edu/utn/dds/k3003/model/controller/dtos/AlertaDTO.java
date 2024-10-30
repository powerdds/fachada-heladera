package ar.edu.utn.dds.k3003.model.controller.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class AlertaDTO {
    private Integer heladeraId;
    private TipoIncidente tipoFraude;
    private List<Integer> colaboradoresId;

    public AlertaDTO(Integer heladeraId, TipoIncidente tipoIncidente) {
        this.heladeraId = heladeraId;
        this.tipoFraude = tipoIncidente;
    }
}

