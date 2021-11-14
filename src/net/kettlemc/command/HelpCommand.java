package net.kettlemc.command;

import net.kettlemc.configuration.Configuration;
import net.kettlemc.help.HelpPlugin;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandExecutor {

    private HelpPlugin plugin;
    private Configuration configuration;

    public HelpCommand(HelpPlugin plugin) {
        this.plugin = plugin;
        this.configuration = plugin.getConfiguration();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (String message : plugin.getConfiguration().getMessage()) {
            sendTextComponent(sender, getTextComponent(message));
        }
        return false;
    }

    private String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private TextComponent getTextComponent(String message) {

        // Checking for '%space%' placeholder
        if (message.equalsIgnoreCase("%space%"))
            return new TextComponent("");

        // Splits message at '||'
        String[] messages = color(message).split("\\|\\|");

        String command = messages[0];
        String description = (messages.length > 1 ? messages[1] : "");

        // Converts first part to a TextComponent
        TextComponent text = new TextComponent(plugin.getConfiguration().getPrefix() + command);

        // Removing all color codes from command
        command = ChatColor.stripColor(command);

        // Adds ClickEvent if enabled
        if (configuration.suggestCommand() && command.startsWith("/"))
            text.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));

        // Adds HoverEvent if a description is present
        if (!description.isEmpty())
            text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(description).create()));

        return text;
    }

    // Spigot doesn't have a method for sending TextComponents to the console
    private void sendTextComponent(CommandSender sender, TextComponent textComponent) {
        if (sender instanceof Player)
            ((Player) sender).spigot().sendMessage(textComponent);
        else
            sender.sendMessage(color(textComponent.getText().trim()));
    }
}
