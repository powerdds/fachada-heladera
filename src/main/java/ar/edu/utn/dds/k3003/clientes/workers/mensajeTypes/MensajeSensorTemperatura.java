package ar.edu.utn.dds.k3003.clientes.workers.mensajeTypes;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.dtos.TemperaturaDTO;
import ar.edu.utn.dds.k3003.utils.MetricsRegistry;
import ar.edu.utn.dds.k3003.utils.ObjectMapperHelper;
import io.micrometer.core.instrument.Gauge;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class MensajeSensorTemperatura implements MensajeStrategy {

    private final Fachada fachada;

    // Un mapa que contendrá las métricas de temperatura de cada heladera
    private static final ConcurrentHashMap<Long, AtomicReference<Double>> temperaturasPorHeladera = new ConcurrentHashMap<>();

    public MensajeSensorTemperatura(Fachada fachada) {
        this.fachada = fachada;
    }

    @Override
    public void procesarMensage(byte[] body) throws IOException {
        var objectMapper = ObjectMapperHelper.createObjectMapper();
        TemperaturaDTO temperaturaDTO = objectMapper.readValue(body, TemperaturaDTO.class);
        procesarTemperatura(temperaturaDTO);
    }

    // Procesar la temperatura recibida
    private void procesarTemperatura(TemperaturaDTO temperaturaDTO) {
        Long heladeraId = Long.valueOf(temperaturaDTO.getHeladeraId());

        fachada.temperatura(temperaturaDTO);

        //Actualizar o registrar la métrica para esta heladera
        actualizarMetricaHeladera(heladeraId, temperaturaDTO.getTemperatura());

        System.out.println("Se recibió la siguiente temperatura");
        System.out.println("Heladera ID: " + temperaturaDTO.getHeladeraId());
        System.out.println("Temperatura: " + temperaturaDTO.getTemperatura());
    }

    // Registrar o actualizar la métrica para una heladera específica
    private void actualizarMetricaHeladera(Long heladeraId, double nuevaTemperatura) {
        temperaturasPorHeladera.computeIfAbsent(heladeraId, id -> {
            // Crear y registrar una nueva métrica si no existe para esta heladera
            AtomicReference<Double> temperaturaRef = new AtomicReference<>(nuevaTemperatura);
            Gauge.builder("heladera.temperatura.actual", temperaturaRef, AtomicReference::get)
                    .description("Temperatura actual de la heladera " + heladeraId)
                    .tag("heladeraId", String.valueOf(heladeraId))
                    .register(MetricsRegistry.getRegistry());
            return temperaturaRef;
        }).set(nuevaTemperatura);  // Actualizamos la temperatura si ya existe
    }
}