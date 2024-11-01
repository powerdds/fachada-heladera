package ar.edu.utn.dds.k3003.clientes.workers.mensajeTypes;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.utils.FachadaHelper;

import java.util.HashMap;
import java.util.Map;

public class MensajeFactory {

    private final Map<String, MensajeStrategy> estrategias = new HashMap<String, MensajeStrategy>();
    private final Fachada fachada;

    public MensajeFactory() {
        this.fachada = FachadaHelper.crearFachada();
        this.estrategias.put("temperatura", new MensajeSensorTemperatura(fachada));
        this.estrategias.put("fraude", new MensajeSensorIncidente(fachada));
        this.estrategias.put("error", new ErrorMensaje());
    }

    public MensajeStrategy getStrategy(String strategy) {

        MensajeStrategy estrategia = estrategias.get(strategy);
        if (estrategia == null) {
            estrategia = estrategias.get("error");
        }
        return estrategia;
    }

}
