package ar.edu.utn.dds.k3003.clientes.workers.mensajeTypes;

public class ErrorMensaje implements MensajeStrategy {

    @Override
    public void procesarMensage(byte[] body) throws IllegalArgumentException {
        throw new IllegalArgumentException();
    }
}
