import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import okhttp3.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Chatbot {
    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ChatBot: Hello! Welcome to the ChatBot! How can I help you? (Enter \"exit\" to quit)");

        while (true) {
            System.out.print("You: ");

            while (!scanner.hasNextLine()) {
            }

            String userInput = scanner.nextLine();

            if ("exit".equalsIgnoreCase(userInput)) {
                System.out.println("ChatBot: See you later!");
                break;
            }

            try {
                String response = askOllama("deepseek-r1", userInput);
                System.out.println("ChatBot: " + response);
            } catch (IOException e) {
                System.err.println("Failed to connect to Ollama: " + e.getMessage());
            }
        }


        scanner.close();
    }

    public static String askOllama(String model, String prompt) throws IOException {
        RequestBodyJson requestBody = new RequestBodyJson(model, prompt);
        String json = gson.toJson(requestBody);

        Request request = new Request.Builder()
                .url(OLLAMA_URL)
                .post(RequestBody.create(json, MediaType.get("application/json")))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        StringBuilder fullResponse = new StringBuilder();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return "Error: " + response.message();
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        ResponseJson chunk = gson.fromJson(line, ResponseJson.class);
                        if (chunk.response != null) {
                            fullResponse.append(chunk.response);
                        }
                        if (chunk.done) {
                            break;
                        }
                    } catch (JsonSyntaxException e) {
                        System.err.println("JSON error: " + e.getMessage());
                    }
                }
            }
        }
        return fullResponse.toString().trim();
    }

    static class RequestBodyJson {
        String model;
        String prompt;
        boolean stream;

        RequestBodyJson(String model, String prompt) {
            this.model = model;
            this.prompt = prompt;
            this.stream = true;
        }
    }

    static class ResponseJson {
        String response;
        boolean done;
    }
}
