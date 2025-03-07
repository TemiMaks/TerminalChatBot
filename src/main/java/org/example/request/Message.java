package org.example.request;

public class Message {

    private String role;
    private String content;

    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    public Message() {
    }

    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
