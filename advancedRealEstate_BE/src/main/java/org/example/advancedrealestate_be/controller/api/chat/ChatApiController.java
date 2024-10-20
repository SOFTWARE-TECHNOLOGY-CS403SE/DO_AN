package org.example.advancedrealestate_be.controller.api.chat;

import org.example.advancedrealestate_be.model.Chat;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;

@Controller
@CrossOrigin(origins = "https://localhost:3000")
public class ChatApiController {

//    @MessageMapping("/chat")
//    @SendTo("/topic/messages")
//    public Chat sendMessage(@Payload Chat chat){
//        chat.setTimeStamp(new Date());
//        return chat;
//    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public Chat sendMessage(Chat message) {

        System.out.print(message);
        return message;
    }

    @MessageMapping("/addUser")
    @SendTo("/topic/public")
    public Chat addUser(Chat message, SimpMessageHeaderAccessor headerAccessor) {
        System.out.print(message);

        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }
}
