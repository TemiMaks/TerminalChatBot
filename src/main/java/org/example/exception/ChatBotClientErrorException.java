package org.example.exception;

public class ChatBotClientErrorException extends RuntimeException {

    public ChatBotClientErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
