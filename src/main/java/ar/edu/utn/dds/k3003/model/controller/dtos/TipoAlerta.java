package ar.edu.utn.dds.k3003.model.controller.dtos;

public enum TipoAlerta {
    MOVIMIENTO, // Me llega de la cola de mensajeria
    TEMPERATURA_ALTA, // Me llega de la cola de mensajeria
    TEMPERATURA_BAJA, // Me llega de la cola de mensajeria
    SIN_CONEXION, // Se saca del job
    MAXIMOVIANDAS, // Se saca del job
    MINIMOVIANDAS, // Se saca del job
    FALLA_TECNICA, // Me llega por endPoint
    REPARADA
}
