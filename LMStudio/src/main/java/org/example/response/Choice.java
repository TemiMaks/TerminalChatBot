package org.example.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.request.Message;

public class Choice {

    @JsonProperty("finish_reason")
    private String finishReason;
    private Message message;

    public Choice() {
    }

    public Choice(String finishReason, Message message) {
        this.finishReason = finishReason;
        this.message = message;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "finishReason='" + finishReason + '\'' +
                ", message=" + message +
                '}';
    }
}
