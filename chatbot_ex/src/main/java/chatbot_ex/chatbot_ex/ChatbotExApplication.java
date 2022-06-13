package chatbot_ex.chatbot_ex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.UUID;

import static chatbot_ex.chatbot_ex.dialogflow.DetectIntentText.detectIntentTexts;

@SpringBootApplication
public class ChatbotExApplication {

	public static void main(String[] args) {

		SpringApplication.run(ChatbotExApplication.class, args);

	}

}
