package by.evgkor.TouristBot.bot;


import by.evgkor.TouristBot.exception.NoSuchCityException;
import by.evgkor.TouristBot.model.City;
import by.evgkor.TouristBot.reader.DataReader;
import by.evgkor.TouristBot.service.CityService;
import by.evgkor.TouristBot.validator.CityValidation;
import org.apache.logging.log4j.LogManager;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.FileNotFoundException;

import static by.evgkor.TouristBot.bot.BotConfig.BOT_TOKEN;
import static by.evgkor.TouristBot.bot.BotConfig.BOT_USERNAME;
import static by.evgkor.TouristBot.bot.BotFiles.BEGIN_MESSAGE_FILEPATH;
import static by.evgkor.TouristBot.bot.BotFiles.HELP_MESSAGE_FILEPATH;
import static by.evgkor.TouristBot.commands.Commands.*;

@Component
public class ChatBot extends TelegramLongPollingBot {
    private final CityService cityService = new CityService();
    private static final Logger LOGGER = LogManager.getLogger();


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long userChatId = update.getMessage().getChatId();
            switch (messageText) {
                case START_COMMAND:
                case HELP_COMMAND:
                    sendMessageToUser(userChatId, HELP_MESSAGE_FILEPATH);
                    break;
                case CITIES_COMMAND:
                    sendMessageToUser(userChatId, BEGIN_MESSAGE_FILEPATH);
                    break;
                default:
                    searchCity(userChatId,messageText);
                    break;

            }
        }
    }

    private void sendMessageToUser(long chat_id, String messageFilepath) {
        SendMessage message = new SendMessage()
                .setChatId(chat_id).setParseMode("HTML");
        String messageText = null;
        try {
            messageText = DataReader.read(messageFilepath);
        } catch (FileNotFoundException e) {
            LOGGER.fatal("FILEPATH " + messageFilepath + " is wrong.", e);
        }
        message.setText(messageText);
        try {
            SendChatAction sendChatAction = new SendChatAction();
            sendChatAction.setChatId(chat_id);
            sendChatAction.setAction(ActionType.TYPING);
            Boolean wasSuccessful = execute(sendChatAction);
            if (!wasSuccessful) {
                LOGGER.warn("Message status " + chat_id + " was shown incorrectly. ");
            }
            execute(message);
        } catch (TelegramApiException e) {
            LOGGER.error("Error while sending message ", e);
            e.printStackTrace();
        }
    }
    private void searchCity( long userChatId, String messageText){

        SendMessage sendMessage = new SendMessage();
        if (!CityValidation.validate(messageText) && !messageText.equals(HELP_COMMAND)) {
            sendMessage.setChatId(userChatId).setText("Error in typing city name.");
        } else {
            City city = null;
            try {
                city = cityService.findCityByName(messageText);
                sendMessage
                        .setChatId(userChatId)
                        .setParseMode("Markdown")
                        .setText("*" + city.getName() + "* " + city.getInfo());
            } catch (NoSuchCityException e) {
                sendMessage
                        .setChatId(userChatId)
                        .setText("There is no such city.");
                LOGGER.error("There is no such city. ", messageText);
            }
            try {
                SendChatAction sendChatAction = new SendChatAction();
                sendChatAction.setChatId(userChatId);
                sendChatAction.setAction(ActionType.TYPING);
                Boolean wasSuccessfull = execute(sendChatAction);
                if (!wasSuccessfull) {
                    LOGGER.warn("Message status " + userChatId + " was shown incorrectly. ");
                }
                this.execute(sendMessage);
            } catch (TelegramApiException e) {
                LOGGER.error("Error while sending message ", e);
            }
        }

    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
