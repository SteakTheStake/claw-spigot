package com.summit.claw.claw;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static com.summit.claw.claw.ClawGUI.openInventory;
import static org.bukkit.Bukkit.getServer;
public class ClawCommand implements CommandExecutor {

    private final Claw plugin;


    // Assuming there's a constructor to pass the plugin instance, adjust as necessary.
    public ClawCommand(Claw plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            // Handle the default command usage
            sender.sendMessage("Usage: /claw generate or /claw open");
            return true;
        }

        if (args[0].equalsIgnoreCase("generate")) {
            // Handle the /claw generate command
            try {
                return generateChallenge(sender);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (args[0].equalsIgnoreCase("open")) {
            // Handle the /claw open command
            openInventory((Player) sender);
            return true;
        }

        return true;
    }

    public boolean generateChallenge(CommandSender sender) throws IOException, InterruptedException {
        // Generate a challenge using the OpenAI API
        String challenge = OpenAIRequestHandler.generateChallenge();

        // Send the challenge to the player
        sender.sendMessage("Challenge: " + challenge);

        return true;
    }
}