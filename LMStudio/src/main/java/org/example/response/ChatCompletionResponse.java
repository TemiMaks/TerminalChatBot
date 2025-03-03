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
        if (choices != null && !choices.isEmpty() && choices.get(0).getMessage() != null) {
            return choices.get(0).getMessage().getContent();
        }
        return "";
    }
}
