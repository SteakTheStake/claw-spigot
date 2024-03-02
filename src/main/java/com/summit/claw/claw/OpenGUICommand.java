package com.summit.claw.claw;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class OpenGUICommand implements CommandExecutor {

    private final Claw plugin;

    public OpenGUICommand(Claw plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        // Open the C.L.A.W. GUI for the player
        ClawGUI.openInventory((Player) sender);

        return true;
    }
}