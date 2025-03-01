package org.example.response;

import java.util.List;

public class ChatCompletionResponse {

    private List<Choice> choices;

    public ChatCompletionResponse() {
    }

    public ChatCompletionResponse(List<Choice> choices) {
        this.choices = choices;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    @Override
    public String toString() {
        return "ChatCompletionResponse{" +
                "choices=" + choices +
                '}';
    }
}
