package org.example.entity;

public class ChatMessage {

    private String sender;

    private String content;

    private boolean isCurrentSession;

    public ChatMessage() {
    }

    public ChatMessage(String sender, String content, boolean isCurrentSession) {
        this.sender = sender;
        this.content = content;
        this.isCurrentSession = isCurrentSession;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCurrentSession() {
        return isCurrentSession;
    }

    public void setCurrentSession(boolean currentSession) {
        isCurrentSession = currentSession;
    }
}
