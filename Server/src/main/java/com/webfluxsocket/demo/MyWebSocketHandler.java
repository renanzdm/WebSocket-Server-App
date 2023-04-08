package com.webfluxsocket.demo;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MyWebSocketHandler extends TextWebSocketHandler {

    private Map<String, ArrayList<WebSocketSession>> sessions = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        sendMessageToUsers(message.getPayload(), session);
    }

    public void sendMessageToUsers(String message, WebSocketSession session) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Mensagem myObject = objectMapper.readValue(message, Mensagem.class);
        ArrayList<WebSocketSession> values = sessions.get(myObject.getDomain());
        if (values != null) {
            for (WebSocketSession item : values) {
                item.sendMessage(new TextMessage(message));
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String key = getKeyQuery(session);
        ArrayList<WebSocketSession> values = sessions.get(key);
        if (values != null) {
            values.add(session);
            sessions.put(key, values);
        } else {
            ArrayList<WebSocketSession> newValues = new ArrayList<WebSocketSession>();
            newValues.add(session);
            sessions.put(key, newValues);
        }
        super.afterConnectionEstablished(session);
    }

    public String getKeyQuery(WebSocketSession session) {
        try {
            URI url = session.getUri();
            if (url != null) {
                return url.getQuery().split("=")[1];
            }
            return "";
        } catch (Exception e) {
            return "";
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String key = getKeyQuery(session);
        ArrayList<WebSocketSession> values = sessions.get(key);
        if (values != null) {
            values.removeIf(item -> (item.getId() == session.getId()));
        }
        super.afterConnectionClosed(session, status);
    }

}
