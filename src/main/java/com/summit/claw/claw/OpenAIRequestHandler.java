package com.summit.claw.claw;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class OpenAIRequestHandler {

    private static final Logger logger = Bukkit.getLogger();
    private static String API_KEY;
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public static void initialize(JavaPlugin plugin) {
        File configFile = new File(plugin.getDataFolder(), "api_key.yml");
        if (!configFile.exists()) {
            plugin.getLogger().warning("API key configuration file does not exist.");
            return;
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        API_KEY = config.getString("openai.api_key", "defaultKey");
        if (API_KEY.equals("defaultKey")) {
            plugin.getLogger().warning("OpenAI API key is not set in api_key.yml.");
        }
    }

    public static String generateChallenge() {
        HttpClient client;
        try {
            JSONObject requestBody = getRequestBody();
            if (API_KEY == null || API_KEY.isEmpty() || API_KEY.equals("defaultKey")) {
                return "API Key not configured.";
            }

            client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            return responseFuture.thenApply(response -> {
                String candidates = response.body();
                System.out.println("OpenAI Response: " + candidates); // Inspect this output
                JSONObject responseObject = new JSONObject(candidates);
                if (responseObject.has("text")) {
                    return responseObject.getString("text");
                } else {
                    return "Error: Challenge not found in OpenAI response";
                }
            }).get();  // Wait for the future result and return

        } catch (InterruptedException | ExecutionException e) {
            logger.severe("Error generating challenge: " + e.getMessage());
            return "An error occurred while generating the challenge.";
        }
    }

    private static @NotNull JSONObject getRequestBody() {
        return new JSONObject()
                .put("model", "gpt-3.5-turbo")
                .put("prompt", "1. Must be realistically achievable within a few hours. " +
                        "2. Do not assume the player already has the necessary materials to complete the challenge. " +
                        "3. Compatible with multiple players to do at the same time in a vanilla survival setting. " +
                        "4. Able to be judged on how good of a job each player did. " +
                        "5. State the challenge short and sweet, only brushing on key points. " +
                        "6. When done stating the challenge, end with the word \"wins the challenge. for survival\"." )
                .put("temperature", 0.7)
                .put("max_tokens", 120);
    }
}
