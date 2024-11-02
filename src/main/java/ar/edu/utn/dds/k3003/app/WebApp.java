package ar.edu.utn.dds.k3003.app;


import ar.edu.utn.dds.k3003.model.controller.dtos.AlertaDTO;
import ar.edu.utn.dds.k3003.model.controller.dtos.TipoAlerta;
import ar.edu.utn.dds.k3003.utils.FachadaHelper;
import io.javalin.Javalin;
import java.util.Timer;
import java.util.TimerTask;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class WebApp {//es el job////////////////////////

    private static Fachada fachada= FachadaHelper.crearFachada();

    public static void main(String[] args) {
        //Javalin app = iniciarApiJavalin();
        // Crear un Timer para programar la tarea
        Timer timer = new Timer();
        // Programar la tarea para que se ejecute cada hora (3600000 milisegundos)
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                // Aquí va la acción que quieres realizar cada hora
                System.out.println("Job ejecutandose a las " + LocalDateTime.now());
                var heladeras=fachada.obtenerHeladeras();
                heladeras.forEach(
                        heladeraDTO -> {
                            var heladera= fachada.obtenerHeladeraOrigin(heladeraDTO.getId());

                            var alertaMinVianda = new AlertaDTO(heladera.getId().intValue(),TipoAlerta.MINIMOVIANDAS);
                            fachada.reportarAlerta(alertaMinVianda);

                            var alertaMaxVianda =new AlertaDTO(heladera.getId().intValue(),TipoAlerta.MAXIMOVIANDAS);
                            fachada.reportarAlerta(alertaMaxVianda);

                            Duration d=Duration.between(heladera.getUltimaConexion(),LocalDateTime.now());
                            if(d.getSeconds()>=3600) {
                                var alertaSinConexion = new AlertaDTO(heladera.getId().intValue(), TipoAlerta.SIN_CONEXION);
                                fachada.reportarAlerta(alertaSinConexion);
                            }
                        }
                );
            }
        }, 0, 60000); // 0: inicio inmediato, 3600000 ms: intervalo de una hora
    }
    private static Javalin iniciarApiJavalin() {

        System.out.println("starting up the server");

        // Javalin
        return Javalin.create().start();
    }

}