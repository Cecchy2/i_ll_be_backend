package dariocecchinato.i_ll_be.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

@Controller
public class WebSocketController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public String sendMessage(String message, SimpMessageHeaderAccessor headerAccessor) {
        return message; // Invia il messaggio a tutti i client connessi al topic pubblico
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public String addUser(String username, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", username);
        return username + " si è unito alla chat!";
    }
}