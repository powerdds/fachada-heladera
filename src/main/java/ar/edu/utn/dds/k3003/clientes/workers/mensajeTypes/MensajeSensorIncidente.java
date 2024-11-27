package ar.edu.utn.dds.k3003.clientes.workers.mensajeTypes;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.model.controller.dtos.AlertaDTO;
import ar.edu.utn.dds.k3003.utils.ObjectMapperHelper;

import java.io.IOException;

public class MensajeSensorIncidente implements MensajeStrategy {

    private final Fachada fachada;

    public MensajeSensorIncidente(Fachada fachada) {
        this.fachada = fachada;
    }

    @Override
    public void procesarMensage(byte[] body) throws IOException {
        var objectMapper = ObjectMapperHelper.createObjectMapper();
        AlertaDTO alertaDTO = objectMapper.readValue(body, AlertaDTO.class);
        this.fachada.reportarAlerta(alertaDTO);
        System.out.println("Se a notificado el incidente " + alertaDTO.getTipoAlerta() + " con exito");
    }

}
