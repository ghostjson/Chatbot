package com.chatbot.Chatbot.controllers;

import com.chatbot.Chatbot.chatbot.Chatbot;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class WebController {

    @GetMapping("/")
    public String home(){
        return "Chatbot API";
    }

    @GetMapping("/{asked}")
    public String response(@PathVariable("asked") String asked) throws IOException, ParseException {
        Chatbot chatbot = new Chatbot();

        return chatbot.getResponse(asked);
    }

}
