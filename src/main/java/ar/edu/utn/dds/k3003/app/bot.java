package ar.edu.utn.dds.k3003.app;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class bot extends TelegramLongPollingBot{
    MenuOpciones menu=new MenuOpciones();

    @Override
    public void onUpdateReceived(Update update) {
// Esta función se invocará cuando nuestro bot reciba un mensaje
// Se obtiene el mensaje escrito por el usuario
        final String messageTextReceived = update.getMessage().getText();
// Se obtiene el id de chat del usuario
        Long chatId = update.getMessage().getChatId();
// Se crea un objeto mensaje
       // SendMessage message = new SendMessage();
       // message.setChatId(chatId.toString());
        menu.setSubState(SubState.START);
        try {
            menu.execute(chatId,messageTextReceived,this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //   message.setText(messageTextReceived);
       // try {
// Se envía el mensaje
     //       execute(message);
     //   } catch (TelegramApiException e) {
    //        e.printStackTrace();
    //    }
    }

    @Override
    public String getBotUsername() {
        return System.getenv("NOMBRE_BOT");
    }
    @Override
    public String getBotToken() {
        return System.getenv("TOKEN_BOT");
    }

    public static void main(String[] args)
            throws TelegramApiException {
// Se crea un nuevo Bot API
        final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
// Se registra el bot
            telegramBotsApi.registerBot(new bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



}
