package org.example.advancedrealestate_be.controller.api.chat;

import com.nimbusds.jose.shaded.gson.JsonObject;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Controller
@CrossOrigin(origins = "https://localhost:3000")
public class ChatApiController {

//    @MessageMapping("/chat")
//    @SendTo("/topic/messages")
//    public Chat sendMessage(@Payload Chat chat){
//        chat.setTimeStamp(new Date());
//        return chat;
//    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public static String generateRandomMessageId(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return "Mid"+sb;
    }

    @MessageMapping("/sendMessageToRoom/{room}")
    public void sendMessageToRoom(@DestinationVariable("room") String room, Chat message) {
        System.out.println("Message: " + message);
        System.out.println("Room: " + room);
        String messageId = generateRandomMessageId(9);
        JSONObject messageObject = new JSONObject();
        messageObject.put("id", messageId);
        messageObject.put("sender", message.getSender());
        messageObject.put("content", message.getContent());
        if(Objects.equals(message.getContent(), "a")){
            messageObject.put("bot", "hahaha");
        }
        messagingTemplate.convertAndSend("/topic/room/" + room, messageObject.toString());
    }

    @MessageMapping("/addUser/{room}")
    public void addUser(@DestinationVariable("room") String room, Chat message, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("User joined room: " + room);

        JSONObject messageObject = new JSONObject();
        messageObject.put("sender", message.getSender());
        messageObject.put("content", message.getContent() == null ? " Chào mừng bạn đã vào room " + room : message.getContent());
        headerAccessor.getSessionAttributes().put("username", message.getSender());

        messagingTemplate.convertAndSend("/topic/room/" + room, messageObject.toString());
    }
}
