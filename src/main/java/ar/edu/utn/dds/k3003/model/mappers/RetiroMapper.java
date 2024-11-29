package ar.edu.utn.dds.k3003.model.mappers;



import ar.edu.utn.dds.k3003.model.controller.dtos.RegistroRetiroDTO;
import ar.edu.utn.dds.k3003.model.RegistroRetiro;


public class RetiroMapper {
    public RegistroRetiroDTO map(RegistroRetiro retiro){
        return new RegistroRetiroDTO(retiro.getQrVianda(), retiro.getFechaMedicion());
    }
}
