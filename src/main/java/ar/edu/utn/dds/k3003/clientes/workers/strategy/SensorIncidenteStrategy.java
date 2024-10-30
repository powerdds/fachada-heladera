package ar.edu.utn.dds.k3003.clientes.workers.strategy;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.clientes.colaboradores.ColaboradoresProxy;
import ar.edu.utn.dds.k3003.clientes.viandas.ViandasProxy;
import ar.edu.utn.dds.k3003.model.controller.dtos.AlertaDTO;
import ar.edu.utn.dds.k3003.utils.ObjectMapperHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class SensorIncidenteStrategy implements MensajeStrategy {

    private final ObjectMapper objectMapper;
    private final Fachada fachada;
    public SensorIncidenteStrategy() {
        this.objectMapper = ObjectMapperHelper.createObjectMapper();
        this.fachada = crearFachada(objectMapper);
    }

    @Override
    public void procesarMensage(byte[] body) throws IOException {
        AlertaDTO alertaDTO = objectMapper.readValue(body, AlertaDTO.class);
        this.fachada.reportarAlerta(alertaDTO);
        System.out.println("Se a notificado el incidente " + alertaDTO.getTipoAlerta() + " con exito");
    }

    // Crear la fachada
    private static Fachada crearFachada(ObjectMapper objectMapper) {
        var fachada = new Fachada();
        fachada.setViandasProxy(new ViandasProxy(objectMapper));
        fachada.setColaboradoresProxy(new ColaboradoresProxy(objectMapper));
        return fachada;
    }

}
