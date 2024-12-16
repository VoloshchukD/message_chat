package org.example.socket;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import org.example.entity.Constants;
import org.example.storage.MessageStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Socket extends Endpoint {

    private static final Logger logger = LoggerFactory.getLogger(Socket.class);

    public void onOpen(Session session, EndpointConfig endpointConfig) {
        session.addMessageHandler(new MessageHandler(endpointConfig));

        Map<String, Object> map = session.getUserProperties();
        HttpSession httpSession = (HttpSession) map.get(Constants.HTTP_SESSION);

        String currentUser = httpSession.getId();
        MessageStorage.getUserSession().computeIfAbsent(currentUser, k -> new ArrayList<>())
                .add(session);
        logger.info("Open websocket for user session id={}", currentUser);

        try {
            MessageStorage.loadChatHistory(currentUser, session);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onError(Session session, Throwable throwable) {
        logger.error("Socket error", throwable);
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        Map<String, Object> map = session.getUserProperties();
        HttpSession httpSession = (HttpSession) map.get(Constants.HTTP_SESSION);
        String sessionId = httpSession.getId();
        MessageStorage.getUserSession().get(sessionId).remove(session);
        logger.error("Socket close");
    }
}
