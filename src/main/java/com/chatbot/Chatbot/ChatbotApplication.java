package com.chatbot.Chatbot;

import com.chatbot.Chatbot.chatbot.Chatbot;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ChatbotApplication {

	public static void main(String[] args) throws IOException, ParseException {

		SpringApplication.run(ChatbotApplication.class, args);

	}

}
