package org.example.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.RemoteEndpoint;
import org.example.entity.ChatMessage;
import org.example.entity.Constants;
import org.example.storage.MessageStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class MessageHandler implements jakarta.websocket.MessageHandler.Whole<String> {

    private final EndpointConfig endpointConfig;

    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    public MessageHandler(EndpointConfig endpointConfig) {
        this.endpointConfig = endpointConfig;
    }

    public void onMessage(String message) {
        Map<String, Object> userProperties = endpointConfig.getUserProperties();
        HttpSession httpSession = (HttpSession) userProperties.get(Constants.HTTP_SESSION);
        String currentSessionId = httpSession.getId();

        ChatMessage chatMessage = new ChatMessage(currentSessionId, message, true);

        try {
            MessageStorage.broadcastMessage(chatMessage);
            MessageStorage.saveMessages(currentSessionId, chatMessage);
        } catch (IOException e) {
            logger.error("Exception while on message logics", e);
        }
    }

}
