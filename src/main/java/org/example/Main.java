package org.example;

import org.example.request.ChatCompletionRequest;
import org.example.response.ChatCompletionResponse;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String input;
            ChatCompletionRequest request = prepareRequest();
            ChatBotClient chatBotClient = new ChatBotClient();
            System.out.println("ChatBot: Hello! Welcome to the ChatBot! How can I help you? (Enter \"exit\" to quit");
            do {
                System.out.print("You: ");
                input = scanner.nextLine();
                if (shouldExit(input)) {
                    System.out.println("ChatBot: See you later!");
                } else {
                    request.addMessage(input);
                    ChatCompletionResponse response = chatBotClient.postRequest(request);
                    System.out.println(response);
                }
            } while (!shouldExit(input));
        }
    }

    private static boolean shouldExit(String input) {
        return "exit".equalsIgnoreCase(input);
    }

    private static ChatCompletionRequest prepareRequest() {
        return new ChatCompletionRequest("qwen2.5-7b-instruct-1m", 0.7F);
    }
}
