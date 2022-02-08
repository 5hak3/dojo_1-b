package net.azisaba.dojo.task1b;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Task1B extends JavaPlugin {

    @Override
    public void onEnable() {
        Task1BCommands commands = new Task1BCommands();
        Objects.requireNonNull(getCommand("heal")).setExecutor(commands);
        Objects.requireNonNull(getCommand("broadcast")).setExecutor(commands);
        Objects.requireNonNull(getCommand("loc")).setExecutor(commands);
        getLogger().info("Loading Complete!");
    }
}
