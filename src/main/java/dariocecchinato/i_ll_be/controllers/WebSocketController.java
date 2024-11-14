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
    public ChatMessage sendMessage(String message, SimpMessageHeaderAccessor headerAccessor) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(message);

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username == null) {
            username = "Anonimo";  // Imposta un valore predefinito se il nome utente non è presente
        }

        chatMessage.setSender(username);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public String addUser(String username, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", username);
        return username + " si è unito alla chat!";
    }
}