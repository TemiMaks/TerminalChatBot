package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.example.exception.ChatBotClientErrorException;
import org.example.request.ChatCompletionRequest;
import org.example.response.ChatCompletionResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ChatBotClient {
    private final String API_URL = "http://localhost:1234/v1/chat/completions";
    private final URI API_URI = URI.create(API_URL);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ChatBotClient() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public ChatCompletionResponse postRequest(ChatCompletionRequest request) {
        try {
            String requestAsString = objectMapper.writeValueAsString(request);
            OkHttpClient client = new OkHttpClient();

            Request httpRequest = new Request.Builder()
                    .url(API_URL)
                    .addHeader("Content-Type", "application/json")
                    .post(RequestBody.create(requestAsString, MediaType.get("application/json; charset=utf-8")))
                    .build();
            try (Response response = client.newCall(httpRequest).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected status code: " + response);
                }
                if (response.body() == null) {
                    throw new IOException("Empty response");
                }
                return objectMapper.readValue(response.body().string(), ChatCompletionResponse.class);
            } catch (IOException e) {
                throw new ChatBotClientErrorException("ChatBotClient failed.", e);
            }
        } catch (JsonProcessingException e) {
            throw new ChatBotClientErrorException("Parsing request failed.", e);
        }
    }

    public ChatCompletionResponse postRequestNotWorking(ChatCompletionRequest request) {
        try {
            String requestAsString = objectMapper.writeValueAsString(request);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(API_URI)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestAsString))
                    .build();
            try (HttpClient client = HttpClient.newHttpClient()) {
                HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                return objectMapper.readValue(httpResponse.body(), ChatCompletionResponse.class);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
