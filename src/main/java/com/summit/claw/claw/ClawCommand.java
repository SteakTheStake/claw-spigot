package com.summit.claw.claw;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.summit.claw.claw.ClawGUI.openInventory;

public class ClawCommand implements CommandExecutor {

    private final Claw plugin;

    // Assuming there's a constructor to pass the plugin instance, adjust as necessary.
    public ClawCommand(Claw plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (label.equalsIgnoreCase("claw")) {
            if (args.length == 0) {
                // Handle the default command usage
                sender.sendMessage("Usage: /claw generate or /claw open");
                return true;
            } else if (args[0].equalsIgnoreCase("generate")) {
                // Handle the /claw generate command
                return generateChallenge(sender);
            } else if (args[0].equalsIgnoreCase("open")) {
                // Handle the /claw open command
                openInventory((Player) sender);
                return true;
            }
        }

        return false;
    }

    public boolean generateChallenge(CommandSender sender) {
        // Generate a challenge using the OpenAI API
        String challenge = OpenAIRequestHandler.generateChallenge();

        // Send the challenge to the player
        sender.sendMessage("Challenge: " + challenge);

        return true;
    }

    public Claw getPlugin() {
        return plugin;
    }
}