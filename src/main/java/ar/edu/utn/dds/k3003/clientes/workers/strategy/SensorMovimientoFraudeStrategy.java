package ar.edu.utn.dds.k3003.clientes.workers.strategy;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.clientes.colaboradores.ColaboradoresProxy;
import ar.edu.utn.dds.k3003.clientes.viandas.ViandasProxy;
import ar.edu.utn.dds.k3003.model.controller.dtos.AlertaDTO;
import ar.edu.utn.dds.k3003.utils.ObjectMapperHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class SensorMovimientoFraudeStrategy implements MensajeStrategy {

    private final ObjectMapper objectMapper;
    private final Fachada fachada;
    public SensorMovimientoFraudeStrategy() {
        this.objectMapper = ObjectMapperHelper.createObjectMapper();
        this.fachada = crearFachada(objectMapper);
    }

    @Override
    public void procesarMensage(byte[] body) throws IOException {
        AlertaDTO fraudeDTO = objectMapper.readValue(body, AlertaDTO.class);
        this.fachada.reportarAlerta(fraudeDTO);
    }

    // Crear la fachada
    private static Fachada crearFachada(ObjectMapper objectMapper) {
        var fachada = new Fachada();
        fachada.setViandasProxy(new ViandasProxy(objectMapper));
        fachada.setColaboradoresProxy(new ColaboradoresProxy(objectMapper));
        return fachada;
    }

}
