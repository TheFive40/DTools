package org.delaware;

import org.bukkit.plugin.java.JavaPlugin;
import org.delaware.commands.SelectCharacterCommand;
import org.delaware.tools.ClassesRegistration;
import org.delaware.tools.commands.CommandFramework;
import org.delaware.tools.model.Character;
import java.io.*;

public class Main extends JavaPlugin {
    private final CommandFramework commandFramework = new CommandFramework(this);
    private final ClassesRegistration classesRegistration = new ClassesRegistration();
    public static Main instance;
    private static final String PATH = System.getProperty("user.dir") + File.separator + "plugins" + File.separator + "DTools" + File.separator + "data" +
            File.separator + "characters.dat";

    @Override
    public void onEnable() {
        instance = this;
        classesRegistration.loadCommands("org.delaware.commands");
        classesRegistration.loadListeners("org.delaware.listeners");
        System.out.println("Plugin successfully enabled");
        System.out.println("Version: 1.0.0 ");
        System.out.println("By DelawareX");
        rollBack();
    }

    @Override
    public void onDisable() {
        save();
        System.out.println("Plugin successfully deactivated");
    }

    public void save() {
        File file = new File(PATH);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(new Character(SelectCharacterCommand.characterHashMap));
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void rollBack(){
        File file = new File(PATH);
        try {
            if(file.canRead()){
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                SelectCharacterCommand.characterHashMap = ((Character) objectInputStream.readObject()).getCharacters();
                objectInputStream.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public CommandFramework getCommandFramework() {
        return commandFramework;
    }

    public ClassesRegistration getClassesRegistration() {
        return classesRegistration;
    }
}