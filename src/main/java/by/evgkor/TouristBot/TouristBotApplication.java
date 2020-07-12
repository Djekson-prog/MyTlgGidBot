package by.evgkor.TouristBot;

import by.evgkor.TouristBot.bot.ChatBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@SpringBootApplication
public class TouristBotApplication {

	public static void main(String[] args) throws TelegramApiRequestException {
		ApiContextInitializer.init();
		SpringApplication.run(TouristBotApplication.class, args);
		TelegramBotsApi telegramBotsApi=new TelegramBotsApi();
		telegramBotsApi.registerBot(new ChatBot());
	}

}
