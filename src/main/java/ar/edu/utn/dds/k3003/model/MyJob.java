package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.model.controller.dtos.AlertaDTO;
import ar.edu.utn.dds.k3003.model.controller.dtos.TipoAlerta;

import java.util.TimerTask;

public class MyJob extends TimerTask {

    private static Fachada fachada;
    public MyJob(Fachada  fachada){
        this.fachada=fachada;
    }

    @Override
    public void run() {
        var heladeras=fachada.obtenerHeladeras();
        heladeras.forEach(
                heladeraDTO -> {
                   var heladera= fachada.obtenerHeladera(heladeraDTO.getId());
                   var alertaMinVianda = new AlertaDTO(heladera.getId(),TipoAlerta.MINIMOVIANDAS);
                   fachada.reportarAlerta(alertaMinVianda);
                   var alertaMaxVianda =new AlertaDTO(heladera.getId(),TipoAlerta.MAXIMOVIANDAS);
                   fachada.reportarAlerta(alertaMaxVianda);
                   var alertaSinConexion=new AlertaDTO(heladera.getId(),TipoAlerta.SIN_CONEXION);
                   if(heladera.getUltimaConexion())
                   fachada.reportarAlerta(alertaSinConexion);
                }
        );
    }


}
