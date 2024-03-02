package com.summit.claw.claw;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class GenerateChallengeCommand implements CommandExecutor {

    private final Claw plugin;

    public GenerateChallengeCommand(Claw plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        // Generate a challenge using the OpenAI API
        String challenge = OpenAIRequestHandler.generateChallenge();

        // Send the challenge to the player
        sender.sendMessage("Challenge: " + challenge);

        return true;
    }
}