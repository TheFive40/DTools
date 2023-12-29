package org.delaware;

import org.bukkit.plugin.java.JavaPlugin;
import org.delaware.tools.ClassesRegistration;
import org.delaware.tools.commands.CommandFramework;

public class Main extends JavaPlugin {
    private final CommandFramework commandFramework = new CommandFramework(this);
    private final ClassesRegistration classesRegistration = new ClassesRegistration();
    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        classesRegistration.loadCommands("org.delaware.commands");
        classesRegistration.loadListeners("org.delaware.listeners");
        System.out.println("\n" + "Plugin successfully enabled\n" +
                "Version: 1.0.0\n" +
                "By DelawareX");
    }

    @Override
    public void onDisable() {
        System.out.println("Apagando plugin");
    }

    public CommandFramework getCommandFramework() {
        return commandFramework;
    }

    public ClassesRegistration getClassesRegistration() {
        return classesRegistration;
    }
}