package ar.edu.utn.dds.k3003.app;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramBot;

public class MenuOpciones extends botState {
    SubState subState = SubState.START;



    public void execute(Long userChat, String messageText, bot bot) throws Exception {
        switch (subState) {
            case START -> elegirFormaDeColaborar(userChat,bot);
            case WAITING_RESPONSE_FORM_COLABORAR -> waitngResponseFormColaborar(userChat,messageText,bot);
            case DONADOR_VIANDA -> waitingResponseDonadorVianda(userChat,messageText,bot);
            case DONADOR_DINERO -> waitingResponseDonadorDinero(userChat,messageText,bot);
            case TRANSPORTADOR -> waitingResponseTransportador(userChat,messageText,bot);
            case TECNICO -> waitingResponseTecnico(userChat,messageText,bot);
        }
    }


    private void elegirFormaDeColaborar(Long user,bot bot) {
        SendMessage response = new SendMessage();
        response.setChatId(user.toString());
        response.setText("""
                A continuacion elija la forma de colaborar:

                1  ☞ Donador de vianda
                2  ☞ Transportador 
                3  ☞ Tecnico
                4  ☞ Donador de dinero
             
                """);
        try {
            bot.execute(response);
            this.subState=SubState.WAITING_RESPONSE_FORM_COLABORAR;
            //execute(user,null,bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void menuOpcionesDonadorDinero(Long user,bot bot) {
        SendMessage response = new SendMessage();
        response.setChatId(user.toString());
        response.setText("""
                Selecciono la forma de colaborar "Donador de dinero"
                
                Escriba el número de la opción deseada:
                1  ☞ Ver mis datos
                2  ☞ Cambiar forma de colaborar
                3  ☞ Donar
                4  ☞ Crear una incidencia (heladera rota)
                5  ☞ Ver incidencias de una heladera
                6  ☞ Ver heladeras de una zona
                7  ☞ Suscribirse a los eventos de una heladera
                8  ☞ Desuscribirse
                9  ☞ Recibir informacion de dichos eventos
                10 ☞ Cerrar una incidencia (activar heladera)
                """);
        try {
            bot.execute(response);
           // this.subState=SubState.WAITING_RESPONSE_OPTION;
            //execute(user,null,bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void menuOpcionesTecnico(Long user,bot bot) {
        SendMessage response = new SendMessage();
        response.setChatId(user.toString());
        response.setText("""
                Selecciono la forma de colaborar "Tecnico"
                
                Escriba el número de la opción deseada:
                1  ☞ Ver mis datos
                2  ☞ Cambiar forma de colaborar
                6  ☞ Crear una incidencia (heladera rota)
                7  ☞ Ver incidencias de una heladera
                8  ☞ Ver heladeras de una zona

                11 ☞ Suscribirse a los eventos de una heladera
                12 ☞ Desuscribirse
                13 ☞ Recibir informacion de dichos eventos
                14 ☞ Reparar heladera y cerrar una incidencia (activar heladera)
 
                """);
        try {
            bot.execute(response);
            //this.subState=SubState.WAITING_RESPONSE_OPTION;
            //execute(user,null,bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void menuOpcionesTransportador(Long user,bot bot) {
        SendMessage response = new SendMessage();
        response.setChatId(user.toString());
        response.setText("""
                Selecciono la forma de colaborar "Transportador"
                
                Escriba el número de la opción deseada:
                1  ☞ Ver mis datos
                2  ☞ Cambiar forma de colaborar
                3  ☞ Crear vianda
                4  ☞ Depositar vianda
                5  ☞ Retirar vianda
                6  ☞ Crear una incidencia (heladera rota)
                7  ☞ Ver incidencias de una heladera
                8  ☞ Ver heladeras de una zona
                9  ☞ Ver la ocupacion de las viandas en una heladera
                10 ☞ Ver los retiros del dia de una heladera
                11 ☞ Suscribirse a los eventos de una heladera
                12 ☞ Desuscribirse
                13 ☞ Recibir informacion de dichos eventos
                14 ☞ Cerrar una incidencia (activar heladera)
                15 ☞ Dar de alta una ruta
                16 ☞ Recibir mensaje que un traslado fue asignado al usuario
                17 ☞ Iniciar traslado de vianda
                18 ☞ Finalizar traslado de vianda
                """);
        try {
            bot.execute(response);
          //  this.subState=SubState.WAITING_RESPONSE_OPTION;
            //execute(user,null,bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void menuOpcionesDonadorVianda(Long user,bot bot) {
        SendMessage response = new SendMessage();
        response.setChatId(user.toString());
        response.setText("""
                Selecciono la forma de colaborar "Donador de vianda"
                
                Escriba el número de la opción deseada:
                1  ☞ Ver mis datos
                2  ☞ Cambiar forma de colaborar
                3  ☞ Crear vianda
                4  ☞ Depositar vianda
                5  ☞ Retirar vianda
                6  ☞ Crear una incidencia (heladera rota)
                7  ☞ Ver incidencias de una heladera
                8  ☞ Ver heladeras de una zona
                9  ☞ Ver la ocupacion de las viandas en una heladera
                10 ☞ Ver los retiros del dia de una heladera
                11 ☞ Suscribirse a los eventos de una heladera
                12 ☞ Desuscribirse
                13 ☞ Recibir informacion de dichos eventos
                14 ☞ Cerrar una incidencia (activar heladera)
                15 ☞ Dar de alta una ruta
                16 ☞ Recibir mensaje que un traslado fue asignado al usuario
                17 ☞ Iniciar traslado de vianda
                18 ☞ Finalizar traslado de vianda
                """);
        try {
            bot.execute(response);
         //   this.subState=SubState.WAITING_RESPONSE_OPTION;
            //execute(user,null,bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void waitngResponseFormColaborar(Long userChat, String messageText, bot bot) throws Exception{

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userChat.toString());

        switch (messageText) {
            case "1" -> {

                this.subState=SubState.DONADOR_VIANDA;
                menuOpcionesDonadorVianda(userChat,bot);
            }
            case "2" -> {

                this.subState=SubState.TRANSPORTADOR;
                menuOpcionesTransportador(userChat,bot);
            }
            case "3" -> {
                this.subState=SubState.TECNICO;
                menuOpcionesTecnico(userChat,bot);
            }
            case "4" -> {
                this.subState=SubState.DONADOR_DINERO;
                menuOpcionesDonadorDinero(userChat,bot);
            }
            default -> {
                sendMessage.setText("seleccionaste una opcion incorrecta, apreta una tecla para ver nuevamente las formas de colaborar");
                bot.execute(sendMessage);
            //    this.subState=SubState.WAITING_RESPONSE_FORM_COLABORAR;
            }
        }

    }


    private void waitingResponseTecnico(Long userChat, String messageText, bot bot)throws Exception {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userChat.toString());

        switch (messageText) {
            case "1" -> {
                sendMessage.setText("seleccionaste la opcion 1");
                bot.execute(sendMessage);
            }
            case "2" -> {
                sendMessage.setText("seleccionaste la opcion 2");
                bot.execute(sendMessage);
            }
            case "3" -> {
                sendMessage.setText("seleccionaste la opcion 3");
                bot.execute(sendMessage);
            }
            case "4" -> {
                sendMessage.setText("seleccionaste la opcion 4");
                bot.execute(sendMessage);
            }
            case "5" -> {
                sendMessage.setText("seleccionaste la opcion 5");
                bot.execute(sendMessage);
            }
            default -> {
                sendMessage.setText("seleccionaste una opcion incorrecta, apreta una tecla para ver nuevamente el menu");
                bot.execute(sendMessage);
                this.subState=SubState.START;
            }
        }
    }

    private void waitingResponseTransportador(Long userChat,String messageText, bot bot) throws Exception{
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userChat.toString());

        switch (messageText) {
            case "1" -> {
                sendMessage.setText("seleccionaste la opcion 1");
                bot.execute(sendMessage);
            }
            case "2" -> {
                sendMessage.setText("seleccionaste la opcion 2");
                bot.execute(sendMessage);
            }
            case "3" -> {
                sendMessage.setText("seleccionaste la opcion 3");
                bot.execute(sendMessage);
            }
            case "4" -> {
                sendMessage.setText("seleccionaste la opcion 4");
                bot.execute(sendMessage);
            }
            case "5" -> {
                sendMessage.setText("seleccionaste la opcion 5");
                bot.execute(sendMessage);
            }
            default -> {
                sendMessage.setText("seleccionaste una opcion incorrecta, apreta una tecla para ver nuevamente el menu");
                bot.execute(sendMessage);
                this.subState=SubState.START;
            }
        }
    }

    private void waitingResponseDonadorDinero(Long userChat,String messageText, bot bot)throws Exception {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userChat.toString());

        switch (messageText) {
            case "1" -> {
                sendMessage.setText("seleccionaste la opcion 1");
                bot.execute(sendMessage);
            }
            case "2" -> {
                sendMessage.setText("seleccionaste la opcion 2");
                bot.execute(sendMessage);
            }
            case "3" -> {
                sendMessage.setText("seleccionaste la opcion 3");
                bot.execute(sendMessage);
            }
            case "4" -> {
                sendMessage.setText("seleccionaste la opcion 4");
                bot.execute(sendMessage);
            }
            case "5" -> {
                sendMessage.setText("seleccionaste la opcion 5");
                bot.execute(sendMessage);
            }
            default -> {
                sendMessage.setText("seleccionaste una opcion incorrecta, apreta una tecla para ver nuevamente el menu");
                bot.execute(sendMessage);
                this.subState=SubState.START;
            }
        }
    }

    private void waitingResponseDonadorVianda(Long userChat,String messageText, bot bot) throws Exception{
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userChat.toString());

        switch (messageText) {
            case "1" -> {
                sendMessage.setText("seleccionaste la opcion 1");
                bot.execute(sendMessage);
            }
            case "2" -> {
                sendMessage.setText("seleccionaste la opcion 2");
                bot.execute(sendMessage);
            }
            case "3" -> {
                sendMessage.setText("seleccionaste la opcion 3");
                bot.execute(sendMessage);
            }
            case "4" -> {
                sendMessage.setText("seleccionaste la opcion 4");
                bot.execute(sendMessage);
            }
            case "5" -> {
                sendMessage.setText("seleccionaste la opcion 5");
                bot.execute(sendMessage);
            }
            default -> {
                sendMessage.setText("seleccionaste una opcion incorrecta, apreta una tecla para ver nuevamente el menu");
                bot.execute(sendMessage);
                this.subState=SubState.START;
            }
        }
    }

}