package ar.edu.utn.dds.k3003.clientes.workers.mensajeTypes;

import java.io.IOException;

public interface MensajeStrategy {
    void procesarMensage(byte[] body) throws IOException;
}
