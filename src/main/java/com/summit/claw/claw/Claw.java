package com.summit.claw.claw;

import org.bukkit.plugin.java.JavaPlugin;


public class Claw extends JavaPlugin {

    @Override
    public void onEnable() {
        // Register commands
        getCommand("claw").setExecutor(new ClawCommand(this));
        // Register subcommands

        getServer().getPluginManager().registerEvents(new ClawGUI(), this);
        OpenAIRequestHandler.initialize(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("C.L.A.W. has stopped");
    }
}