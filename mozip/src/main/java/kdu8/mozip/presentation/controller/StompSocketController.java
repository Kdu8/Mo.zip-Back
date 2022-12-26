package kdu8.mozip.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;

@Controller
public class StompSocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/test")
    public void test(String uk) {

        System.out.println("connected");
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("name", "junseo");
        simpMessagingTemplate.convertAndSend("/queue/a", payload);
    }
}
