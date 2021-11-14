package net.kettlemc.configuration;

import java.util.Arrays;
import java.util.List;

public class Configuration {

    private BasicConfigurationHandler config;

    private String prefix;
    private List<String> helpMessage;
    private boolean suggestCommand;

    public Configuration() {
        this.config = new BasicConfigurationHandler("plugins/SimpleHelp/config.yml");
        this.prefix = config.getColoredString("prefix", "&9Help &8» &7");
        this.suggestCommand = config.getBool("suggest-command", true);
        this.helpMessage = (List<String>) config.getValue("message", Arrays.asList(new String[] {"&9&lHelp", "&7Hover over the commands for more information.", "&3/help||Shows this message.", "&3/example||&7Another example command."}));
    }

    public String getPrefix() {
        return this.prefix;
    }

    public List<String> getMessage() {
        return this.helpMessage;
    }

    public boolean suggestCommand() {
        return this.suggestCommand;
    }
}
