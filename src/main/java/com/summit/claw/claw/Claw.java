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
        if ((this.getCommand("claw generate") == null)) {
            getLogger().severe("Command 'claw generate' not found. Check plugin.yml");
        } else {
            getCommand("claw generate").setExecutor(new ClawCommand(this));
        }

        // Claw open command
        if (this.getCommand("claw open") == null) {
            getLogger().severe("Command 'claw open' not found. Check plugin.yml");
        } else {
            getCommand("claw open").setExecutor(new ClawCommand(this));
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