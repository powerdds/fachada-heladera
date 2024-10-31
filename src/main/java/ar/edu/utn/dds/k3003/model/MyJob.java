package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.model.controller.dtos.AlertaDTO;
import ar.edu.utn.dds.k3003.model.controller.dtos.TipoAlerta;

import java.time.Duration;
import java.time.LocalDate;
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
                   var heladera= fachada.obtenerHeladeraOrigin(heladeraDTO.getId());

                   var alertaMinVianda = new AlertaDTO(heladera.getId().intValue(),TipoAlerta.MINIMOVIANDAS);
                   fachada.reportarAlerta(alertaMinVianda);

                   var alertaMaxVianda =new AlertaDTO(heladera.getId().intValue(),TipoAlerta.MAXIMOVIANDAS);
                   fachada.reportarAlerta(alertaMaxVianda);

                   Duration d=Duration.between(heladera.getUltimaConexion(),LocalDate.now());
                   if(d.getSeconds()>=3600) {
                       var alertaSinConexion = new AlertaDTO(heladera.getId().intValue(), TipoAlerta.SIN_CONEXION);
                       fachada.reportarAlerta(alertaSinConexion);
                   }
                }
        );
    }


}
