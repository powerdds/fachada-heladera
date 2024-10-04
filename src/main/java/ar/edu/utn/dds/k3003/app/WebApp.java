package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.clientes.ViandasProxy;
import ar.edu.utn.dds.k3003.model.controller.HeladeraController;
import ar.edu.utn.dds.k3003.model.controller.MetricsController;
import ar.edu.utn.dds.k3003.model.controller.TemperaturaController;
import ar.edu.utn.dds.k3003.facades.dtos.Constants;
import ar.edu.utn.dds.k3003.clientes.workers.MensajeListener;
import ar.edu.utn.dds.k3003.utils.MetricsRegistry;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.micrometer.MicrometerPlugin;
import io.micrometer.core.instrument.binder.jvm.*;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class WebApp {

    private static final Fachada fachada;
    private static final HeladeraController heladeraController;
    private static final TemperaturaController temperaturaController;
    private static final MetricsController metricsController;

    static {
        fachada = crearFachada(createObjectMapper());
        heladeraController = new HeladeraController(fachada);
        temperaturaController = new TemperaturaController(fachada);
        metricsController = new MetricsController();
    }

    public static void main(String[] args) {

        // Iniciar el worker en un hilo separado
        iniciarWorkerSensorTemperaturas();

        // Iniciar la API
        Javalin app = iniciarApiJavalin();

        // Definir las rutas
        definirRutas(app);
    }

    private static void definirRutas(Javalin app) {
        app.post("/heladeras", heladeraController::agregar);
        app.get("/heladeras/{id}", heladeraController::obtener);
        app.post("/temperaturas", temperaturaController::agregar);
        app.get("/heladeras/{id}/temperaturas", temperaturaController::obtener);
        app.post("/depositos", heladeraController::depositar);
        app.post("/retiros", heladeraController::retirar);
        app.get("/cleanup", heladeraController::cleanup);

        // Exponer las métricas en la ruta /metrics
        app.get("/metrics", metricsController::obtener);
    }

    // Iniciar el worker de RabbitMQ
    private static void iniciarWorkerSensorTemperaturas() {
        Thread workerThread = new Thread(() -> {
            try {
                MensajeListener.iniciar(); // Inicia el worker de RabbitMQ
            } catch (Exception e) {
                System.err.println("Error al iniciar el worker de RabbitMQ: " + e.getMessage());
            }
        });
        workerThread.start(); // Iniciar el hilo del worker
    }

    // Crear la fachada
    private static Fachada crearFachada(ObjectMapper objectMapper) {
        var fachada = new Fachada();
        fachada.setViandasProxy(new ViandasProxy(objectMapper)); // Usar ViandasProxy para pruebas locales
        return fachada;
    }

    // Inicializar la API con las métricas de Prometheus
    private static Javalin iniciarApiJavalin() {

        System.out.println("starting up the server");

        // agregar aquí cualquier tag que aplique a todas las métrivas de la app
        // (e.g. EC2 region, stack, instance id, server group)

        var registry = MetricsRegistry.getRegistry();

        registry.config().commonTags("app", "fachada_heladera");

        // agregamos a nuestro reigstro de métricas todo lo relacionado a infra/tech
        // de la instancia y JVM
        new ClassLoaderMetrics().bindTo(registry);
        new JvmMemoryMetrics().bindTo(registry);
        new JvmGcMetrics().bindTo(registry);
        new ProcessorMetrics().bindTo(registry);
        new JvmThreadMetrics().bindTo(registry);

        // seteamos el registro dentro de la config de Micrometer
        final var micrometerPlugin = new MicrometerPlugin(config -> config.registry = registry);

        // registramos el plugin de Micrometer dentro de la config de la app de
        // Javalin
        return Javalin.create(config -> config.registerPlugin(micrometerPlugin)).start(8080);
    }

    // Configurar el ObjectMapper
    public static ObjectMapper createObjectMapper() {
        var objectMapper = new ObjectMapper();
        configureObjectMapper(objectMapper);
        return objectMapper;
    }

    public static void configureObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var sdf = new SimpleDateFormat(Constants.DEFAULT_SERIALIZATION_FORMAT, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        objectMapper.setDateFormat(sdf);
    }

}