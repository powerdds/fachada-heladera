package ar.edu.utn.dds.k3003.model.controller.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RegistroRetiroDTO {

    private String qrVianda;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    )
    private LocalDateTime fechaRetiro;

    public RegistroRetiroDTO(String qrVianda, LocalDateTime fechaRetiro) {
        this.qrVianda = qrVianda;
        this.fechaRetiro = fechaRetiro;
    }
}
