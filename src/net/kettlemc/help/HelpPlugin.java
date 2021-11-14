package net.kettlemc.help;

import net.kettlemc.command.HelpCommand;
import net.kettlemc.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public class HelpPlugin extends JavaPlugin {

    private Configuration configuration;

    public void onEnable() {
        this.saveDefaultConfig();
        this.configuration = new Configuration();
        this.registerCommands();
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    private void registerCommands() {
        this.getCommand("help").setExecutor(new HelpCommand(this));
    }


}