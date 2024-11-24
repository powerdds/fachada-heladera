package ar.edu.utn.dds.k3003.app;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramBot;

public class MenuOpciones extends botState {
    SubState subState = SubState.START;



    public void execute(Long userChat, String messageText, bot bot) throws Exception {
        switch (subState) {
            case START -> menuOpciones(userChat,bot);
            case WAITING_RESPONSE_OPTION -> waitingResponseExecute(userChat, messageText, bot);
        }
    }
    private void menuOpciones(Long user,bot bot) {
        SendMessage response = new SendMessage();
        response.setChatId(user.toString());
        response.setText("""
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
           this.subState=SubState.WAITING_RESPONSE_OPTION;
           //execute(user,null,bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void waitingResponseExecute(Long userChat, String messageText, bot bot) throws Exception {
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
                sendMessage.setText("seleccionaste una opcion incorrecta");
                bot.execute(sendMessage);
            }
        }
    }

}