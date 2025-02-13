package org.delaware;

import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;
import net.minecraft.util.com.google.gson.reflect.TypeToken;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.tools.ClassesRegistration;
import org.delaware.tools.General;
import org.delaware.tools.commands.CommandFramework;
import org.delaware.tools.model.entities.DBItem;
import org.delaware.tools.model.entities.TP;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
;

import static org.delaware.commands.CommandTransform.playerStats;
import static org.delaware.commands.DBItemsCommand.*;
import static org.delaware.commands.TPSCommand.tps;
import static org.delaware.events.InventoryClick.hasFullArmorSet;
import static org.delaware.events.PlayerHeld.players;
import static org.delaware.events.PlayerHeld.restorePlayerData;
import static org.delaware.events.PlayerInteract.*;


public class Main extends JavaPlugin {
    public static HashMap<String, DBItem> armorsPlayers = new HashMap<> ( );
    public static HashMap<String, Integer> playersTPS = new HashMap<> ( );
    public static ArrayList<ArrayList<DBItem>> armors = new ArrayList<> ( );
    static {
        String ruta1 = System.getProperty ( "user.dir" ) + File.separator + "plugins";
        File file = new File ( ruta1, "DTools" );
        file.mkdir ( );
        String ruta2 = System.getProperty ( "user.dir" ) + File.separator + "plugins" + File.separator + "DTools";
        File file2 = new File ( ruta2, "data" );
        file2.mkdir ( );

    }

    private final CommandFramework commandFramework = new CommandFramework ( this );
    private final ClassesRegistration classesRegistration = new ClassesRegistration ( );
    public static Main instance;
    private static final String PATH_TICKETS = System.getProperty ( "user.dir" ) + File.separator + "plugins" + File.separator + "DTools" + File.separator + "data" +
            File.separator + "tickets.dat";
    private static final String PATH_TICKETS_CLAIMED = System.getProperty ( "user.dir" ) + File.separator + "plugins" + File.separator + "DTools" + File.separator + "data" +
            File.separator + "ticketsclaim.dat";

    private static final String PATH_PLAYER_TICKETS = System.getProperty ( "user.dir" ) + File.separator + "plugins" + File.separator + "DTools" + File.separator + "data" +
            File.separator + "playertickets.dat";

    @Override
    public void onEnable () {
        instance = this;
        classesRegistration.loadCommands ( "org.delaware.commands" );
        classesRegistration.loadListeners ( "org.delaware.events" );
        System.out.println ( "Plugin successfully enabled" );
        System.out.println ( "Version: 1.1.0 " );
        System.out.println ( "By DelawareX" );

        File rootDir = new File ( getDataFolder ( ), "DTools" );
        File dataDir = new File ( rootDir, "data" );



        try {
            Type typeTps = new TypeToken<ConcurrentHashMap<Integer, TP>> ( ) {
            }.getType ( );
            Type typePlayers = new TypeToken<ArrayList<String>> ( ) {
            }.getType ( );
            Type typeItems = new TypeToken<ConcurrentHashMap<String, DBItem>> ( ) {
            }.getType ( );
            Type typeArmorsPlayers = new TypeToken<HashMap<String, DBItem>> ( ) {
            }.getType ( );
            Type typeBonus = new TypeToken<HashMap<String, Float>> ( ) {
            }.getType ( );
            Type typeCost = new TypeToken<HashMap<String, Float>> ( ) {
            }.getType ( );
            Type typeArmors = new TypeToken<ArrayList<ArrayList<DBItem>>> ( ) {
            }.getType ( );

            FileReader readerTps = new FileReader ( new File ( dataDir, "tps.json" ) );
            FileReader readerPlayers = new FileReader ( new File ( dataDir, "players.json" ) );
            FileReader readerItems = new FileReader ( new File ( dataDir, "itemsRegistry.json" ) );
            FileReader readerArmorsPlayers = new FileReader ( new File ( dataDir, "armorsPlayer.json" ) );
            FileReader readerBonus = new FileReader ( new File ( dataDir, "bonus.json" ) );
            FileReader readerCost = new FileReader ( new File ( dataDir, "cost.json" ) );
            FileReader readerArmors = new FileReader ( new File ( dataDir, "armors.json" ) );

            Gson gson = new Gson ( );
            tps = gson.fromJson ( readerTps, typeTps );
            players = gson.fromJson ( readerPlayers, typePlayers );
            itemsRegistry = gson.fromJson ( readerItems, typeItems );
            armorsPlayers = gson.fromJson ( readerArmorsPlayers, typeArmorsPlayers );
            playerBonus = gson.fromJson ( readerBonus, typeBonus );
            playerCost = gson.fromJson ( readerCost, typeCost );
            armors = gson.fromJson ( readerArmors, typeArmors );

            readerTps.close ( );
            readerPlayers.close ( );
            readerItems.close ( );
            readerPlayers.close ( );
            readerBonus.close ( );
            readerCost.close ( );
            readerArmors.close ();
        } catch (FileNotFoundException e) {
            System.out.println ( "No se encontraron algunos archivos JSON. Se generarán automáticamente al desactivar el plugin." );
        } catch (IOException e) {
            throw new RuntimeException ( e );
        }
        armorTask ( );
    }

    @Override
    public void onDisable () {
        System.out.println ( "Plugin successfully deactivated" );

        Gson gson = new GsonBuilder ( ).setPrettyPrinting ( ).create ( );
        String jsonTps = gson.toJson ( tps );
        String jsonPlayers = gson.toJson ( players );
        String jsonItems = gson.toJson ( itemsRegistry );
        String jsonArmorsPlayers = gson.toJson ( armorsPlayers );
        String jsonBonus = gson.toJson ( playerBonus );
        String jsonCost = gson.toJson ( playerCost );
        String jsonArmors = gson.toJson ( armors );

        File rootDir = new File ( getDataFolder ( ), "DTools" );
        File dataDir = new File ( rootDir, "data" );
        if (!dataDir.exists ( )) {
            dataDir.mkdirs ( );
        }

        try {
            FileWriter writerTps = new FileWriter ( new File ( dataDir, "tps.json" ) );
            FileWriter writerPlayers = new FileWriter ( new File ( dataDir, "players.json" ) );
            FileWriter writerItems = new FileWriter ( new File ( dataDir, "itemsRegistry.json" ) );
            FileWriter writerArmorsPlayers = new FileWriter ( new File ( dataDir, "armorsPlayer.json" ) );
            FileWriter writerBonus = new FileWriter ( new File ( dataDir, "bonus.json" ) );
            FileWriter writerCost = new FileWriter ( new File ( dataDir, "cost.json" ) );
            FileWriter writerArmors = new FileWriter ( new File ( dataDir, "armors.json" ) );

            writerTps.write ( jsonTps );
            writerPlayers.write ( jsonPlayers );
            writerItems.write ( jsonItems );
            writerArmorsPlayers.write ( jsonArmorsPlayers );
            writerBonus.write ( jsonBonus );
            writerCost.write ( jsonCost );
            writerArmors.write ( jsonArmors );

            writerTps.close ( );
            writerPlayers.close ( );
            writerItems.close ( );
            writerArmorsPlayers.close ( );
            writerBonus.close ( );
            writerCost.close ( );
            writerArmors.close ();
        } catch (IOException e) {
            throw new RuntimeException ( "Error al guardar los archivos JSON", e );
        }

        playerStats.forEach ( ( k, v ) -> {
            IDBCPlayer idbcPlayer = NpcAPI.Instance ( ).getPlayer ( k ).getDBCPlayer ( );
            idbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.STR, v.getSTR ( ) );
            idbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.CON, v.getCON ( ) );
            idbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.WIL, v.getWIL ( ) );
            idbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.MND, v.getMND ( ) );
            idbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.SPI, v.getSPI ( ) );
        } );
    }

    public static boolean hasSetArmor ( ArrayList<DBItem> armor ) {
        return armors.stream ( ).anyMatch ( e -> e.containsAll ( armor ) );
    }

    public void armorTask () {

        BukkitRunnable runnable = new BukkitRunnable ( ) {
            @Override
            public void run () {
                for (Player player : Main.instance.getServer ( ).getOnlinePlayers ( )) {
                    ItemStack chestplate = player.getInventory ( ).getChestplate ( );
                    ItemStack leggings = player.getInventory ( ).getLeggings ( );
                    ItemStack boots = player.getInventory ( ).getBoots ( );
                    if (hasFullArmorSet ( player ) && !armorsPlayers.containsKey ( player.getName ( ) )) {
                        if (isDBItem ( chestplate ) && isDBItem ( leggings ) && isDBItem ( boots )) {
                            ArrayList<DBItem> armaduras = new ArrayList<> ( Arrays.asList ( wrapDBItem ( chestplate ), wrapDBItem ( leggings ),
                                    wrapDBItem ( boots )) );
                            if (hasSetArmor ( armaduras )){
                                setPlayerData ( player, wrapDBItem ( chestplate ), false );
                                armorsPlayers.put ( player.getName ( ), wrapDBItem ( chestplate ) );
                                armorCompleted.put ( player.getName ( ), 3 );
                            }
                        }
                    } else if (!hasFullArmorSet ( player )) {
                        if (armorCompleted.containsKey ( player.getName ( ) )) {
                            IDBCPlayer idbcPlayer;
                            try {
                                idbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
                            } catch (NullPointerException exception) {
                                continue;
                            }
                            restorePlayerData ( player, armorsPlayers.get ( player.getName ( ) ), idbcPlayer );
                            armorCompleted.remove ( player.getName ( ) );
                            armorsPlayers.remove ( player.getName ( ) );
                        }
                    }else if(hasFullArmorSet ( player ) && armorCompleted.containsKey ( player.getName ( ) )){
                        if (isDBItem ( chestplate ) && isDBItem ( leggings ) && isDBItem ( boots )) {
                            ArrayList<DBItem> armaduras = new ArrayList<> ( Arrays.asList ( wrapDBItem ( chestplate ), wrapDBItem ( leggings ),
                                    wrapDBItem ( boots )) );
                            if (!hasSetArmor ( armaduras )){
                                IDBCPlayer idbcPlayer;
                                try {
                                    idbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
                                } catch (NullPointerException exception) {
                                    continue;
                                }
                                restorePlayerData ( player, armorsPlayers.get ( player.getName ( ) ), idbcPlayer );
                                armorCompleted.remove ( player.getName ( ) );
                                armorsPlayers.remove ( player.getName ( ) );
                            }
                        }
                    }
                }
            }
        };
        runnable.runTaskTimerAsynchronously ( Main.instance, 0L, 20L );
    }

    public CommandFramework getCommandFramework () {
        return commandFramework;
    }

    public ClassesRegistration getClassesRegistration () {
        return classesRegistration;
    }
}