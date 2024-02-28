package com.summit.claw.claw;

import org.bukkit.plugin.java.JavaPlugin;


public class Claw extends JavaPlugin {

    @Override
    public void onEnable() {
        // Register commands
        getCommand("claw").setExecutor(new ClawCommand(this));
        getCommand("open").setExecutor(new ClawCommand(this));
        getCommand("generate").setExecutor(new ClawCommand(this));
        // Register subcommands

        // Claw generate command
        if ((this.getCommand("generate") == null)) {
            getLogger().severe("Command 'generate' not found. Check plugin.yml");
        } else {
            getCommand("generate").setExecutor(new ClawCommand(this));
        }

        // Claw open command
        if (this.getCommand("open") == null) {
            getLogger().severe("Command 'open' not found. Check plugin.yml");
        } else {
            getCommand("open").setExecutor(new ClawCommand(this));
        }

        getServer().getPluginManager().registerEvents(new ClawGUI(), this);
        OpenAIRequestHandler.initialize(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("C.L.A.W. has stopped");
    }
}