package ar.edu.utn.dds.k3003.model.mappers;

import ar.edu.utn.dds.k3003.facades.dtos.HeladeraDTO;
import ar.edu.utn.dds.k3003.model.Heladera;

import java.util.List;
import java.util.stream.Collectors;

public class HeladeraMapper {
    public HeladeraDTO map(Heladera heladera){
        HeladeraDTO heladeraDTO = new HeladeraDTO(heladera.getId().intValue(),heladera.getNombre(),heladera.getViandas());
        heladeraDTO.setId(heladera.getId().intValue());
        return heladeraDTO;
    }

    public List<HeladeraDTO> originToListDTO(List<Heladera> heladeras) {
        return heladeras.stream().map(this::map).collect(Collectors.toList());
    }
}
