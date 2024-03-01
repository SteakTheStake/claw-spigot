package com.summit.claw.claw;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class OpenAIRequestHandler {

    private static final Logger logger = Bukkit.getLogger();

    private static final String API_KEY = "sk-KEY";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    private static void printHttpRequest(HttpRequest request) {
        logger.info("HTTP Request:");
        logger.info("URI: " + request.uri());
        logger.info("Method: " + request.method());
        logger.info("Headers:");
        request.headers().map().forEach((key, values) -> values.forEach(value -> logger.info("\t" + key + ": " + value)));
        logger.info("Body:");

        // Get the actual request body
        String requestBody = request.bodyPublisher().get().toString();

        logger.info(requestBody);

    }

    public static String generateChallenge() throws IOException, InterruptedException, JSONException {
        JSONObject requestBody = getRequestBody();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        // Print the HTTP request
        printHttpRequest(request);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonResponse = new JSONObject(response.body());
        return jsonResponse.getJSONArray("choices").getJSONObject(0).getString("text");
    }

    private static @NotNull JSONObject getRequestBody() throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("prompt", "1. Must be realistically achievable within a few hours. " +
                "2. Do not assume the player already has the necessary materials to complete the challenge. " +
                "3. Compatible with multiple players to do at the same time in a vanilla survival setting. " +
                "4. Able to be judged on how good of a job each player did. " +
                "5. State the challenge short and sweet, only brushing on key points. " +
                "6. When done stating the challenge, end with the word \"wins the challenge. for survival\"." );
        requestBody.put("temperature", 0.7);  // Add the temperature field
        requestBody.put("max_tokens", 120);  // Correct the field name
        return requestBody;
    }
}