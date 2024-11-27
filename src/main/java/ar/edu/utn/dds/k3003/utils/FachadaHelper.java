package ar.edu.utn.dds.k3003.utils;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.clientes.colaboradores.ColaboradoresProxy;
import ar.edu.utn.dds.k3003.clientes.viandas.ViandasProxy;
import ar.edu.utn.dds.k3003.facades.dtos.Constants;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class FachadaHelper {

    // Crear la fachada
    public static Fachada crearFachada() {
        var objectMapper = ObjectMapperHelper.createObjectMapper();
        var fachada = new Fachada();
        fachada.setViandasProxy(new ViandasProxy(objectMapper));
        fachada.setColaboradoresProxy(new ColaboradoresProxy(objectMapper));
        return fachada;
    }

}
