package org.example.request;

import java.util.ArrayList;
import java.util.List;

public class ChatCompletionRequest {
    private String model;
    private List<Message> messages;
    private float temperature;

    public ChatCompletionRequest(String model, float temperature) {
        this.model = model;
        this.temperature = temperature;
        this.messages = new ArrayList<>();
        messages.add(new Message("system", "You're a helpful AI"));
    }

    public String getModel() {
        return model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public float getTemperature() {
        return temperature;
    }

    public void addMessage(String input) {
        messages.set(messages.size() - 1, new Message("user", input));
    }

}