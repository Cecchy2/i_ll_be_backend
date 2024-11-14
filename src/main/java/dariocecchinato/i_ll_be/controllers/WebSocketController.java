package dariocecchinato.i_ll_be.controllers;

import dariocecchinato.i_ll_be.entities.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

@Controller
public class WebSocketController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage message) {
        return message; // Ora il messaggio conterrà sia il contenuto che il sender
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public String addUser(String username, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", username);
        return username + " si è unito alla chat!";
    }
}