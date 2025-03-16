package org.delaware;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;
import net.minecraft.util.com.google.gson.JsonSyntaxException;
import net.minecraft.util.com.google.gson.reflect.TypeToken;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.commands.CommandAddGift;
import org.delaware.events.interactWithGift;
import org.delaware.tools.BoosterHandler.BoosterDataHandler;
import org.delaware.tools.BoosterHandler.BoosterManager;
import org.delaware.tools.ClassesRegistration;
import org.delaware.tools.CustomItems.CustomItems;
import org.delaware.tools.CustomItems.CustomItemsRunnable;
import org.delaware.tools.CustomItems.PlayerBonusesData;
import org.delaware.tools.CustomItems.Scythe.ScytheRunnable;
import org.delaware.tools.CustomItems.WriteRunnable;
import org.delaware.tools.General;
import org.delaware.tools.RegionUtils;
import org.delaware.tools.commands.CommandFramework;
import org.delaware.tools.model.entities.Gift;
import org.delaware.tools.model.entities.Localizaciones;
import org.delaware.tools.model.entities.TP;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import static org.delaware.commands.CommandTransform.playerStats;
import static org.delaware.tools.CustomItems.CustomItems.items;
import static org.delaware.tools.CustomItems.PlayerBonusesData.bonusData;
import static org.delaware.commands.TPSCommand.tps;


public class Main extends JavaPlugin {
    public static HashMap<String, Integer> scytheConfig = new HashMap<>();
    public static HashMap<String, Integer> playersTPS = new HashMap<> ( );
    public static LuckPerms luckPermsAPI;
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
        luckPermsAPI = LuckPermsProvider.get ();
        classesRegistration.loadCommands ( "org.delaware.commands" );
        classesRegistration.loadListeners ( "org.delaware.events" );
        System.out.println ( "Plugin successfully enabled" );
        System.out.println ( "Version: 1.1.0 " );
        System.out.println ( "By DelawareX" );
        File rootDir = new File ( getDataFolder ( ), "DTools" );
        File dataDir = new File ( rootDir, "data" );

        try{
            //Five
            BoosterDataHandler.loadData ();
            BoosterManager.startBoosterCheckTask ();
            loadGifts();
            //Five
            try {
                Type typeTps = new TypeToken<ConcurrentHashMap<Integer, TP>> ( ) {
                }.getType ( );
                FileReader readerTps = new FileReader ( new File ( dataDir, "tps.json" ) );
                Gson gson = new Gson ( );
                tps = gson.fromJson ( readerTps, typeTps );
                readerTps.close ( );
            } catch (FileNotFoundException e) {
                System.out.println ( "No se encontraron algunos archivos JSON. Se generarán automáticamente al desactivar el plugin." );
            } catch (IOException e) {
                throw new RuntimeException ( e );
            }
        }catch(Exception e){}

        //Five
        RegionUtils.loadData ();
        //Five
        //Spacey
        loadCustomItems();
        loadCustomBonuses();
        loadScytheConfig();
        //Spacey


    }
    public void writeData(){
        File rootDir = new File(getDataFolder(), "DTools");
        File dataDir = new File(rootDir, "data");

        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonGift = gson.toJson(new Gift(
                CommandAddGift.itemStackHashMap,
                interactWithGift.contadorRegalos,
                interactWithGift.regalosEncontrados,
                interactWithGift.misionCompletada
        ));
        File file = new File(dataDir, "gifts.json");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonGift);
            System.out.println("Datos guardados en: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir el archivo JSON", e);
        }

    }
    public void loadGifts() {
        File rootDir = new File(getDataFolder(), "DTools");
        File dataDir = new File(rootDir, "data");
        File file = new File(dataDir, "gifts.json");
        if (!dataDir.exists()) {
            if (dataDir.mkdirs()) {
                System.out.println("Directorio creado exitosamente: " + dataDir.getAbsolutePath());
            } else {
                System.out.println("No se pudo crear el directorio.");
                return;
            }
        }

        if (!file.exists()) {
            System.out.println("No hay datos previos de regalos para cargar.");
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Gift gift = gson.fromJson(reader, Gift.class);
            CommandAddGift.itemStackHashMap = gift.getItemStackHashMap();
            interactWithGift.contadorRegalos = gift.getContadorRegalos();
            interactWithGift.misionCompletada = gift.getMisionCompletada();
            interactWithGift.regalosEncontrados = gift.getRegalosEncontrados();

            // Restaurar los regalos en el menú
            for (Localizaciones localizaciones : CommandAddGift.itemStackHashMap) {
                ItemStack regalo = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta skullMeta = (SkullMeta) regalo.getItemMeta();
                skullMeta.setOwner("defib");
                CommandAddGift.setItemInMenu(regalo, skullMeta, localizaciones);
            }

            System.out.println("Datos de regalos cargados correctamente.");
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo JSON", e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Error en el formato del archivo JSON", e);
        }
    }
    @Override
    public void onDisable () {
        System.out.println ( "Plugin successfully deactivated" );
        writeData ();
        Gson gson = new GsonBuilder ( ).setPrettyPrinting ( ).create ( );
        String jsonTps = gson.toJson ( tps );
        File rootDir = new File ( getDataFolder ( ), "DTools" );
        File dataDir = new File ( rootDir, "data" );
        if (!dataDir.exists ( )) {
            dataDir.mkdirs ( );
        }
        //Five
        BoosterDataHandler.saveData ();
        //Five
        try {
            FileWriter writerTps = new FileWriter ( new File ( dataDir, "tps.json" ) );
            writerTps.write ( jsonTps );
            writerTps.close ( );
        } catch (IOException e) {
            throw new RuntimeException ( "Error al guardar los archivos JSON", e );
        }
        //Five
        RegionUtils.saveData ();
        //Five

        //Spacey
        disableCustomItems();
        //Spacey

        playerStats.forEach ( ( k, v ) -> {
            IDBCPlayer idbcPlayer = NpcAPI.Instance ( ).getPlayer ( k ).getDBCPlayer ( );
            idbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.STR, v.getSTR ( ) );
            idbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.CON, v.getCON ( ) );
            idbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.WIL, v.getWIL ( ) );
            idbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.MND, v.getMND ( ) );
            idbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.SPI, v.getSPI ( ) );
        } );
    }


    public CommandFramework getCommandFramework () {
        return commandFramework;
    }

    public ClassesRegistration getClassesRegistration () {
        return classesRegistration;
    }
    //Spacey
    private void loadCustomItems() {
        CustomItemsRunnable.getBukkitRunnable().runTaskTimer(instance, 20, (20 * 5));
        WriteRunnable.getRunnable().runTaskTimer(instance, 100, (20 * 300));
        try {
            File rootDir = new File (getDataFolder(), "DTools");
            File dataDir = new File (rootDir, "data");
            if (!dataDir.exists ( )) {
                dataDir.mkdirs ( );
            }
            FileReader readerCustomItems = new FileReader ( new File ( dataDir, "CustomItems.json" ) );
            Type typeItems = new TypeToken<HashMap<String, CustomItems>>(){}.getType();
            Gson gson = new Gson();
            items = gson.fromJson(readerCustomItems, typeItems);
            readerCustomItems.close();
        }catch(IOException | JsonSyntaxException e) {
            Bukkit.getLogger().log(Level.SEVERE, "CustomItems.json doesn't exist!", e);
        }
    }
    private void loadCustomBonuses() {
        try {
            File rootDir = new File (getDataFolder(), "DTools");
            File dataDir = new File (rootDir, "data");
            if (!dataDir.exists ( )) {
                dataDir.mkdirs ( );
            }
            FileReader readerCustomBonuses = new FileReader(new File(dataDir, "PlayerBonusesData.json"));
            Type typeBonuses = new TypeToken<HashMap<String, PlayerBonusesData>>(){}.getType();
            Gson gson = new Gson();
            bonusData = gson.fromJson(readerCustomBonuses, typeBonuses);
            readerCustomBonuses.close();
        }catch(IOException | JsonSyntaxException e) {
            Bukkit.getLogger().log(Level.SEVERE, "PlayerBonusesData.json doesn't exist!", e);
        }
    }
    private void loadScytheConfig() {
        ScytheRunnable.getRun().runTaskTimer(instance, 20, (20 * 5));
        try {
            File rootDir = new File (getDataFolder(), "DTools");
            File dataDir = new File (rootDir, "data");
            if (!dataDir.exists ( )) {
                dataDir.mkdirs ( );
            }
            FileReader readerScytheConfig = new FileReader(new File(dataDir, "ScytheConfig.json"));
            Type typeScythe = new TypeToken<HashMap<String, Integer>>(){}.getType();
            Gson gson = new Gson();
            scytheConfig = gson.fromJson(readerScytheConfig, typeScythe);
            readerScytheConfig.close();
        }catch(IOException | JsonSyntaxException e) {
            Bukkit.getLogger().log(Level.SEVERE, "ScytheConfig.json doesn't exist!", e);
        }
    }
    private void loadRegionData() {

    }
    private void disableCustomItems() {
        CustomItemsRunnable.getBukkitRunnable().cancel();
        WriteRunnable.getRunnable().cancel();
        ScytheRunnable.getRun().cancel();
        CustomItems.saveToConfig();
        PlayerBonusesData.saveToConfig();
    }
    //Spacey
}