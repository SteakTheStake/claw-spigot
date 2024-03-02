package com.summit.claw.claw;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public class Claw extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("C.L.A.W. has started");
        // Register commands
        getCommand("claw").setExecutor(new ClawCommand(this));

        getServer().getPluginManager().registerEvents(new ClawGUI(), this);
        OpenAIRequestHandler.initialize(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("C.L.A.W. has stopped");
    }
}