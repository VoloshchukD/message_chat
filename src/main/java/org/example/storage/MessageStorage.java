package org.example.storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.Session;
import org.example.entity.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MessageStorage {

    private static final Map<String, List<Session>> activeSessions = new ConcurrentHashMap<>();
    private static final Map<String, Map<String, List<String>>> chatHistories = new ConcurrentHashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(MessageStorage.class);

    public static Map<String, List<Session>> getUserSession() {
        return activeSessions;
    }

    public static void saveMessageForSenderReceiver(ChatMessage message, String sender,
                                                    String recipient) throws JsonProcessingException {
        saveMessage(message, recipient, sender);
        saveMessage(message, sender, recipient);
    }

    private static void saveMessage(ChatMessage message, String sender,
                                    String recipient) throws JsonProcessingException {
        message.setCurrentSession(recipient.equals(message.getSender()));
        chatHistories.computeIfAbsent(recipient, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(sender, k -> new CopyOnWriteArrayList<>())
                .add(objectMapper.writeValueAsString(message));
    }

    public static void loadChatHistory(String reader, Session session) throws IOException {
        Map<String, List<String>> userChats = chatHistories.getOrDefault(reader, new ConcurrentHashMap<>());
        for (Map.Entry<String, List<String>> entry : userChats.entrySet()) {
            for (String message : entry.getValue()) {
                session.getBasicRemote().sendText(message);
            }
        }
    }

    public static void saveMessages(String currentSessionId, ChatMessage chatMessage) {
        getUserSession().keySet().stream()
                .filter(e -> !currentSessionId.equals(e))
                .forEach(e -> {
                    try {
                        saveMessageForSenderReceiver(chatMessage, currentSessionId, e);
                    } catch (JsonProcessingException ex) {
                        logger.error("Exception while saving message", ex);
                    }
                });
    }

    public static void broadcastMessage(ChatMessage chatMessage) throws IOException {
        for (Map.Entry<String, List<Session>> entry : activeSessions.entrySet()) {
            String sessionId = entry.getKey();
            List<Session> socketSessions = entry.getValue();

            chatMessage.setCurrentSession(sessionId.equals(chatMessage.getSender()));
            String stringChatMessage = objectMapper.writeValueAsString(chatMessage);

            for (Session session : socketSessions) {
                session.getBasicRemote().sendText(stringChatMessage);
            }
        }
    }

}
