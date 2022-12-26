package kdu8.mozip.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.HashMap;

@RequiredArgsConstructor
public class StompSocketController {

    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/test")
    public void test(String uk) {

        System.out.println("connected");
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("name", "junseo");
        simpMessagingTemplate.convertAndSend("/qwer/a", payload);
    }
}
