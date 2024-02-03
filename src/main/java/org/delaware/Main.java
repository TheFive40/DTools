package org.delaware;
import org.bukkit.plugin.java.JavaPlugin;
import org.delaware.commands.CreateCharacterCommand;
import org.delaware.commands.SelectCharacterCommand;
import org.delaware.commands.TicketClaimCommand;
import org.delaware.commands.TicketCommand;
import org.delaware.tools.ClassesRegistration;
import org.delaware.tools.commands.CommandFramework;
import org.delaware.tools.model.Character;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main extends JavaPlugin {
    static {
        String ruta1 = System.getProperty("user.dir") + File.separator + "plugins";
        File file = new File(ruta1,"DTools");
        file.mkdir();
        String ruta2 = System.getProperty("user.dir") + File.separator + "plugins" + File.separator + "DTools";
        File file2 = new File(ruta2,"data");
        file2.mkdir();
    }

    private final CommandFramework commandFramework = new CommandFramework(this);
    private final ClassesRegistration classesRegistration = new ClassesRegistration();
    public static Main instance;
    private static final String PATH = System.getProperty("user.dir") + File.separator + "plugins" + File.separator + "DTools" + File.separator + "data" +
            File.separator + "characters.dat";
    private static final String PATH_ACCOUNT = System.getProperty("user.dir") + File.separator + "plugins" + File.separator + "DTools" + File.separator + "data" +
            File.separator + "accounts.dat";
    private static final String PATH_PLAYERDATA = System.getProperty("user.dir") + File.separator + "plugins" + File.separator + "DTools" + File.separator + "data" +
            File.separator + "playerdata.dat";
    private static final String PATH_TICKETS = System.getProperty("user.dir") + File.separator + "plugins" + File.separator + "DTools" + File.separator + "data" +
            File.separator + "tickets.dat";
    private static final String PATH_TICKETS_CLAIMED = System.getProperty("user.dir") + File.separator + "plugins" + File.separator + "DTools" + File.separator + "data" +
            File.separator + "ticketsclaim.dat";

    private static final String PATH_PLAYER_TICKETS = System.getProperty("user.dir") + File.separator + "plugins" + File.separator + "DTools" + File.separator + "data" +
            File.separator + "playertickets.dat";

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
        File file2 = new File(PATH_ACCOUNT);
        File file3 = new File(PATH_PLAYERDATA);
        File file4 = new File(PATH_TICKETS);
        File file5 = new File(PATH_PLAYER_TICKETS);
        File file6 = new File(PATH_TICKETS_CLAIMED);

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new FileOutputStream(file2));
            ObjectOutputStream objectOutputStream3 = new ObjectOutputStream(new FileOutputStream(file3));
            ObjectOutputStream objectOutputStream4 = new ObjectOutputStream(new FileOutputStream(file4));
            ObjectOutputStream objectOutputStream5 = new ObjectOutputStream(new FileOutputStream(file5));
            ObjectOutputStream objectOutputStream6 = new ObjectOutputStream(new FileOutputStream(file6));
            objectOutputStream.writeObject(new Character(SelectCharacterCommand.characterHashMap));
            objectOutputStream2.writeObject(CreateCharacterCommand.accounts);
            objectOutputStream3.writeObject(CreateCharacterCommand.playerData);
            objectOutputStream4.writeObject(TicketCommand.tickets);
            objectOutputStream5.writeObject(TicketCommand.playerOpenTicket);
            objectOutputStream6.writeObject(TicketClaimCommand.ticketClaimed);
            objectOutputStream.close();
            objectOutputStream2.close();
            objectOutputStream3.close();
            objectOutputStream4.close();
            objectOutputStream5.close();
            objectOutputStream6.close();
        } catch (IOException e) {
        }
    }

    public void rollBack() {
        File file = new File(PATH);
        File file2 = new File(PATH_ACCOUNT);
        File file3 = new File(PATH_PLAYERDATA);
        File file4 = new File(PATH_TICKETS);
        File file5 = new File(PATH_PLAYER_TICKETS);
        File file6 = new File(PATH_TICKETS_CLAIMED);
        try {
            if (file.canRead()) {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                ObjectInputStream objectInputStream2 = new ObjectInputStream(new FileInputStream(file2));
                ObjectInputStream objectInputStream3 = new ObjectInputStream(new FileInputStream(file3));
                ObjectInputStream objectOutputStream4 = new ObjectInputStream(new FileInputStream(file4));
                ObjectInputStream objectOutputStream5 = new ObjectInputStream(new FileInputStream(file5));
                ObjectInputStream objectOutputStream6 = new ObjectInputStream(new FileInputStream(file6));
                SelectCharacterCommand.characterHashMap = ((Character) objectInputStream.readObject()).getCharacters();
                CreateCharacterCommand.accounts = (ConcurrentHashMap<String, ConcurrentHashMap<String, Object>>) objectInputStream2.readObject();
                CreateCharacterCommand.playerData = (ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, Object>>>) objectInputStream3.readObject();
                TicketCommand.tickets = (ConcurrentHashMap<Integer, CopyOnWriteArrayList<String>>) objectOutputStream4.readObject();
                TicketCommand.playerOpenTicket = (ConcurrentHashMap<String, CopyOnWriteArrayList<Integer>>) objectOutputStream5.readObject();
                TicketClaimCommand.ticketClaimed = (ConcurrentHashMap<Integer, String>) objectOutputStream6.readObject();
                objectInputStream.close();
                objectInputStream2.close();
                objectInputStream3.close();
                objectOutputStream4.close();
                objectOutputStream5.close();
                objectOutputStream6.close();
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