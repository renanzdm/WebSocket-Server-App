package com.webfluxsocket.demo;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class WebSocketController {

    void startWebSocketClient() {

    }

    @PostMapping("/send-message")
    ResponseEntity<Object> sendMessage(@RequestBody Mensagem mensagem) {
        System.out.println("Conectou ao servidor");
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketHandler webSocketHandler = new MyWebSocketHandler();
        WebSocketSession session = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            String url = "ws://localhost:8080/my-websocket-endpoint?id=server";
            session = webSocketClient.execute(webSocketHandler, url, new HashMap<>()).get();
            session.sendMessage(new TextMessage(mapper.writeValueAsString(mensagem)));
            return ResponseEntity.status(200).body("Mensagem Enviada");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body(e.getMessage());

        } finally {
            if (session != null && session.isOpen()) {
                try {
                    session.close();
                    System.out.println("Conexao encerrada com sucesso");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}