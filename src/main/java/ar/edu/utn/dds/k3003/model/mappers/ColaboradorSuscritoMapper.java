package ar.edu.utn.dds.k3003.model.mappers;

import ar.edu.utn.dds.k3003.model.ColaboradorSuscrito;
import ar.edu.utn.dds.k3003.model.controller.dtos.SuscripcionDTO;

public class ColaboradorSuscritoMapper {

    public ColaboradorSuscrito toOrigin(SuscripcionDTO suscripcionDTO){
        return new ColaboradorSuscrito(suscripcionDTO.colaboradorId,
                suscripcionDTO.maximoViandas, suscripcionDTO.minimoViandas, suscripcionDTO.reportarIncidentes);
    }

}
