package org.delaware;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import kamkeel.npcdbc.api.event.IDBCEvent;
import kamkeel.npcdbc.constants.DBCForm;
import kamkeel.npcdbc.constants.DBCRace;
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
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.CombatTag.CommandCancel;
import org.delaware.CombatTag.PlayerLeaveEvent;
import org.delaware.CombatTag.TagListener;
import org.delaware.DBCEvents.DBCDamageEvent;
import org.delaware.DBCEvents.DBCKnockoutEvent;
import org.delaware.DBCEvents.Listeners.DamageEvent;
import org.delaware.DBCEvents.Listeners.KnockoutEvent;
import org.delaware.commands.BlockItemCommand;
import org.delaware.commands.CommandAddGift;
import org.delaware.events.BlockedItemEvent;
import org.delaware.events.InteractWithGiftsEvent;
import org.delaware.events.PersonalBoosterEvent;
import org.delaware.tools.*;
import org.delaware.tools.BoosterHandler.BoosterDataHandler;
import org.delaware.tools.BoosterHandler.BoosterManager;
import org.delaware.tools.Boosters.BonusAttributes;
import org.delaware.tools.Boosters.ZenkaiExpansion;
import org.delaware.tools.CustomItems.CustomItems;
import org.delaware.tools.CustomItems.CustomItemsRunnable;
import org.delaware.tools.CustomItems.PlayerBonusesData;
import org.delaware.tools.CustomItems.Scythe.ScytheRunnable;
import org.delaware.tools.CustomItems.WriteRunnable;
import org.delaware.tools.Kits.KitClaimTracker;
import org.delaware.tools.Kits.KitStorage;
import org.delaware.tools.Permissions.PermissionsManager;
import org.delaware.tools.RegionTools.PlayerAccessManager;
import org.delaware.tools.RegionTools.Runnable.RegionCheckRunnable;
import org.delaware.tools.commands.CommandFramework;
import org.delaware.tools.model.entities.Gift;
import org.delaware.tools.model.entities.Localizaciones;
import org.delaware.tools.model.entities.TP;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import static kamkeel.npcdbc.constants.DBCRace.*;
import static org.delaware.commands.CommandTransform.playerStats;
import static org.delaware.events.BlockedItemEvent.startAsyncCheck;
import static org.delaware.tools.CustomItems.CustomItems.items;
import static org.delaware.tools.CustomItems.PlayerBonusesData.bonusData;
import static org.delaware.commands.TPSCommand.tps;
import static org.delaware.tools.RegionTools.PlayerAccessManager.allPlayers;
import static org.delaware.tools.RegionUtils.regionAccess;


public class Main extends JavaPlugin {
    DamageEvent dmgEventInstance;
    KnockoutEvent koEventInstance;
    TagListener tagEvent;
    CommandCancel commandEvent;
    PlayerLeaveEvent leaveEvent;
    public static HashMap<String, Integer> scytheConfig = new HashMap<> ( );
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
        luckPermsAPI = LuckPermsProvider.get ( );
        classesRegistration.loadCommands ( "org.delaware.commands" );
        classesRegistration.loadListeners ( "org.delaware.events" );
        System.out.println ( "Plugin successfully enabled" );
        System.out.println ( "Version: 1.1.5 " );
        System.out.println ( "By DelawareX | SpaceyDCO" );
        File rootDir = new File ( getDataFolder ( ), "DTools" );
        File dataDir = new File ( rootDir, "data" );

        try {
            //Five
            BoosterDataHandler.loadData ( );
            BoosterManager.startBoosterCheckTask ( );
            loadGifts ( );
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
        } catch (Exception e) {
        }
        BankManager.init ( this );

        //Five
        RegionUtils.loadData ( );
        //Five
        if (Bukkit.getPluginManager ( ).getPlugin ( "PlaceholderAPI" ) != null) {
            new ZenkaiExpansion ( ).register ( );
            getLogger ( ).info ( "Expansion PlaceHolderZenkai registrada correctamente." );
        } else {
            getLogger ( ).warning ( "PlaceholderAPI no encontrada. No se registraron los placeholders de Zenkai." );
        }
        //Spacey
        //CustomNPCS events
        dmgEventInstance = new DamageEvent ( );
        Bukkit.getPluginManager ( ).registerEvents ( dmgEventInstance, this );
        koEventInstance = new KnockoutEvent ( );
        Bukkit.getPluginManager ( ).registerEvents ( koEventInstance, this );
        tagEvent = new TagListener ( );
        Bukkit.getPluginManager ( ).registerEvents ( tagEvent, this );
        commandEvent = new CommandCancel ( );
        Bukkit.getPluginManager ( ).registerEvents ( commandEvent, this );
        leaveEvent = new PlayerLeaveEvent ( );
        Bukkit.getPluginManager ( ).registerEvents ( leaveEvent, this );
        //CustomNPCS events
        loadCustomItems ( );
        loadCustomBonuses ( );
        loadScytheConfig ( );
        loadRegionData ( );
        RegionCheckRunnable.regionCheckRunnable.runTaskTimer ( this, 60, 60 );
        TagListener.clearExpireTagsTask.runTaskTimer ( this, 20L, 20L );
        //Spacey
        arcosianTask ( );
        startAsyncCheck ( );
        KitClaimTracker.load ( this );
        KitStorage.load ( this );
        PersonalBoosterEvent.boosterTask ( );
        BlockItemCommand.cargarLista ( this );
    }

    public void writeData () {
        File rootDir = new File ( getDataFolder ( ), "DTools" );
        File dataDir = new File ( rootDir, "data" );

        if (!dataDir.exists ( )) {
            dataDir.mkdirs ( );
        }

        Gson gson = new GsonBuilder ( ).setPrettyPrinting ( ).create ( );
        String jsonGift = gson.toJson ( new Gift (
                CommandAddGift.itemStackHashMap,
                InteractWithGiftsEvent.contadorRegalos,
                InteractWithGiftsEvent.regalosEncontrados,
                InteractWithGiftsEvent.misionCompletada
        ) );
        File file = new File ( dataDir, "gifts.json" );
        try (FileWriter writer = new FileWriter ( file )) {
            writer.write ( jsonGift );
            System.out.println ( "Datos guardados en: " + file.getAbsolutePath ( ) );
        } catch (IOException e) {
            throw new RuntimeException ( "Error al escribir el archivo JSON", e );
        }

    }
    public String getPlayerFactionName ( Player player ) {
        MPlayer mPlayer = MPlayer.get ( player );
        Faction faction = mPlayer.getFaction ( );
        if (faction == null) return null;
        return faction.getName ( );
    }

    public String getPlayerAtFactionLoc ( Player player ) {
        Faction faction2 = BoardColl.get ( ).getFactionAt ( PS.valueOf ( player.getLocation ( ) ) );
        if (faction2 == null) return null;
        return faction2.getName ( );
    }

    public boolean hasAccessFaction ( String name ) {
        Player player = Bukkit.getPlayer ( name );
        MPlayer mPlayer = MPlayer.get ( player );
        Faction faction = mPlayer.getFaction ( );
        if (faction == null) return false;
        Faction faction2 = BoardColl.get ( ).getFactionAt ( PS.valueOf ( player.getLocation ( ) ) );
        if (faction2 != null) {
            if (faction.getName ( ).equalsIgnoreCase ( faction2.getName ( ) )
                    && !faction.getName ( ).contains ( "Wilderness" )) {
                return true;
            } else if (faction.getRelationTo ( faction2 ).getValue ( ) == Rel.ALLY.getValue ( )) {
                return true;
            }
        }
        return false;
    }
    public String getPlayerRacialTop(String race) {
        Player topPlayer = null;
        int highestLevel = -1;
        for (Player player : Main.instance.getServer().getOnlinePlayers()) {
            IDBCPlayer idbcPlayer = NpcAPI.Instance().getPlayer(player.getName()).getDBCPlayer();
            if (race.equalsIgnoreCase("SAIYAN") && idbcPlayer.getRace() == DBCRace.SAIYAN ||
                    race.equalsIgnoreCase("HALFSAIYAN") && idbcPlayer.getRace() == DBCRace.HALFSAIYAN ||
                    race.equalsIgnoreCase("ARCOSIAN") && idbcPlayer.getRace() == DBCRace.ARCOSIAN ||
                    race.equalsIgnoreCase("NAMEKIAN") && idbcPlayer.getRace() == DBCRace.NAMEKIAN ||
                    race.equalsIgnoreCase("MAJIN") && idbcPlayer.getRace() == DBCRace.MAJIN ||
                    race.equalsIgnoreCase("HUMAN") && idbcPlayer.getRace() == DBCRace.HUMAN) {
                int lvl = General.getLVL(player);
                if (lvl > highestLevel) {
                    highestLevel = lvl;
                    topPlayer = player;
                }
            }
        }
        return topPlayer != null ? topPlayer.getName() : null;
    }
    public String getTopGlobalPlayer() {
        Player topPlayer = null;
        int highestLevel = -1;

        for (OfflinePlayer player : Main.instance.getServer().getOfflinePlayers ()) {
            int lvl = General.getLVL(player.getPlayer ());
            if (lvl > highestLevel) {
                highestLevel = lvl;
                topPlayer = player.getPlayer ();
            }
        }
        return topPlayer != null ? topPlayer.getName() : null;
    }

    public void loadGifts () {
        File rootDir = new File ( getDataFolder ( ), "DTools" );
        File dataDir = new File ( rootDir, "data" );
        File file = new File ( dataDir, "gifts.json" );
        if (!dataDir.exists ( )) {
            if (dataDir.mkdirs ( )) {
                System.out.println ( "Directorio creado exitosamente: " + dataDir.getAbsolutePath ( ) );
            } else {
                System.out.println ( "No se pudo crear el directorio." );
                return;
            }
        }
        if (!file.exists ( )) {
            System.out.println ( "No hay datos previos de regalos para cargar." );
            return;
        }

        try (FileReader reader = new FileReader ( file )) {
            Gson gson = new Gson ( );
            Gift gift = gson.fromJson ( reader, Gift.class );
            CommandAddGift.itemStackHashMap = gift.getItemStackHashMap ( );
            InteractWithGiftsEvent.contadorRegalos = gift.getContadorRegalos ( );
            InteractWithGiftsEvent.misionCompletada = gift.getMisionCompletada ( );
            InteractWithGiftsEvent.regalosEncontrados = gift.getRegalosEncontrados ( );
            for (Localizaciones localizaciones : CommandAddGift.itemStackHashMap) {
                ItemStack regalo = new ItemStack ( Material.SKULL_ITEM, 1, (short) 3 );
                SkullMeta skullMeta = (SkullMeta) regalo.getItemMeta ( );
                skullMeta.setOwner ( "defib" );
                CommandAddGift.setItemInMenu ( regalo, skullMeta, localizaciones );
            }

            System.out.println ( "Datos de regalos cargados correctamente." );
        } catch (IOException e) {
            throw new RuntimeException ( "Error al leer el archivo JSON", e );
        } catch (JsonSyntaxException e) {
            throw new RuntimeException ( "Error en el formato del archivo JSON", e );
        }
    }

    @Override
    public void onDisable () {
        System.out.println ( "Plugin successfully deactivated" );
        writeData ( );
        Gson gson = new GsonBuilder ( ).setPrettyPrinting ( ).create ( );
        String jsonTps = gson.toJson ( tps );
        File rootDir = new File ( getDataFolder ( ), "DTools" );
        File dataDir = new File ( rootDir, "data" );
        if (!dataDir.exists ( )) {
            dataDir.mkdirs ( );
        }
        //Five
        BoosterDataHandler.saveData ( );
        //Five
        try {
            FileWriter writerTps = new FileWriter ( new File ( dataDir, "tps.json" ) );
            writerTps.write ( jsonTps );
            writerTps.close ( );
        } catch (IOException e) {
            throw new RuntimeException ( "Error al guardar los archivos JSON", e );
        }
        BankManager.saveAll ( );
        //Five
        RegionUtils.saveData ( );
        //Five

        //Spacey
        disableCustomItems ( );
        //CustomNPCS events
        DBCDamageEvent.getHandlerList ( ).unregister ( dmgEventInstance );
        DBCKnockoutEvent.getHandlerList ( ).unregister ( koEventInstance );
        TagListener.clearExpireTagsTask.cancel ( );
        EntityDamageByEntityEvent.getHandlerList ( ).unregister ( tagEvent );
        PlayerCommandPreprocessEvent.getHandlerList ( ).unregister ( commandEvent );
        PlayerQuitEvent.getHandlerList ( ).unregister ( leaveEvent );
        //CustomNPCS events
        //Spacey
        for (Map.Entry<String, Set<String>> entry : regionAccess.entrySet ( )) {
            String regionName = entry.getKey ( );
            Set<String> players = entry.getValue ( );
            WorldGuardPlugin wg = WorldGuardPlugin.inst ( );

            ProtectedRegion region = wg.getRegionManager ( Bukkit.getWorld ( "trainingoculto" ) ).getRegion ( regionName );
            if (region != null) {
                for (String playerName : players) {
                    region.getMembers ( ).removePlayer ( playerName );
                }
            }
        }
        regionAccess.clear ( );
        playerStats.forEach ( ( k, v ) -> {
            IDBCPlayer idbcPlayer = NpcAPI.Instance ( ).getPlayer ( k ).getDBCPlayer ( );
            idbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.STR, v.getSTR ( ) );
            idbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.CON, v.getCON ( ) );
            idbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.WIL, v.getWIL ( ) );
            idbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.MND, v.getMND ( ) );
            idbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.SPI, v.getSPI ( ) );
        } );
        KitStorage.save ( this );
        KitClaimTracker.save ( this );
        BlockItemCommand.guardarLista ( this );
    }


    public CommandFramework getCommandFramework () {
        return commandFramework;
    }

    public ClassesRegistration getClassesRegistration () {
        return classesRegistration;
    }

    //Spacey
    private void loadCustomItems () {
        //CustomItemsRunnable.getBukkitRunnable ( ).runTaskTimer ( instance, (20 * 5), (20 * 5) );
        CustomItemsRunnable.getBukkitRunnable ( ).runTaskTimer ( instance, 20, 20 );
        WriteRunnable.getRunnable ( ).runTaskTimer ( instance, 100, (20 * 300) );
        try {
            File rootDir = new File ( getDataFolder ( ), "DTools" );
            File dataDir = new File ( rootDir, "data" );
            if (!dataDir.exists ( )) {
                dataDir.mkdirs ( );
            }
            FileReader readerCustomItems = new FileReader ( new File ( dataDir, "CustomItems.json" ) );
            Type typeItems = new TypeToken<HashMap<String, CustomItems>> ( ) {
            }.getType ( );
            Gson gson = new Gson ( );
            items = gson.fromJson ( readerCustomItems, typeItems );
            readerCustomItems.close ( );
        } catch (IOException | JsonSyntaxException e) {
            Bukkit.getLogger ( ).log ( Level.SEVERE, "CustomItems.json doesn't exist!", e );
        }
    }

    public void arcosianTask () {
        BukkitRunnable runnable = new BukkitRunnable ( ) {
            @Override
            public void run () {
                for (Player player : Main.instance.getServer ( ).getOnlinePlayers ( )) {
                    IDBCPlayer idbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
                    if (idbcPlayer.getRace ( ) == ARCOSIAN) {
                        if (DBCForm.ThirdForm == idbcPlayer.getForm ( ) && !idbcPlayer.hasFinishedQuest ( 327 )) {
                            idbcPlayer.setForm ( (byte) DBCForm.FirstForm );
                            player.sendMessage ( CC.translate ( "&cNecesitas desbloquear la tercera forma" ) );
                        }
                        if (DBCForm.FinalForm == idbcPlayer.getForm ( ) && !idbcPlayer.hasFinishedQuest ( 328 )) {
                            idbcPlayer.setForm ( (byte) DBCForm.FirstForm );
                            player.sendMessage ( CC.translate ( "&cNecesitas desbloquear la cuarta forma" ) );
                        }
                    }
                }
            }
        };
        runnable.runTaskTimer ( this, 20L, 20L );


    }

    private void loadCustomBonuses () {
        try {
            File rootDir = new File ( getDataFolder ( ), "DTools" );
            File dataDir = new File ( rootDir, "data" );
            if (!dataDir.exists ( )) {
                dataDir.mkdirs ( );
            }
            FileReader readerCustomBonuses = new FileReader ( new File ( dataDir, "PlayerBonusesData.json" ) );
            Type typeBonuses = new TypeToken<HashMap<String, PlayerBonusesData>> ( ) {
            }.getType ( );
            Gson gson = new Gson ( );
            bonusData = gson.fromJson ( readerCustomBonuses, typeBonuses );
            readerCustomBonuses.close ( );
        } catch (IOException | JsonSyntaxException e) {
            Bukkit.getLogger ( ).log ( Level.SEVERE, "PlayerBonusesData.json doesn't exist!", e );
        }
    }

    private void loadScytheConfig () {
        ScytheRunnable.getRun ( ).runTaskTimer ( instance, 20, (20 * 5) );
        try {
            File rootDir = new File ( getDataFolder ( ), "DTools" );
            File dataDir = new File ( rootDir, "data" );
            if (!dataDir.exists ( )) {
                dataDir.mkdirs ( );
            }
            FileReader readerScytheConfig = new FileReader ( new File ( dataDir, "ScytheConfig.json" ) );
            Type typeScythe = new TypeToken<HashMap<String, Integer>> ( ) {
            }.getType ( );
            Gson gson = new Gson ( );
            scytheConfig = gson.fromJson ( readerScytheConfig, typeScythe );
            readerScytheConfig.close ( );
        } catch (IOException | JsonSyntaxException e) {
            Bukkit.getLogger ( ).log ( Level.SEVERE, "ScytheConfig.json doesn't exist!", e );
        }
    }

    private void loadRegionData () {
        try {
            File rootDir = new File ( getDataFolder ( ), "DTools" );
            File dataDir = new File ( rootDir, "data" );
            if (!dataDir.exists ( )) {
                dataDir.mkdirs ( );
            }
            FileReader readerPlayerAccessData = new FileReader ( new File ( dataDir, "PlayerAccessData.json" ) );
            Type typeAccessData = new TypeToken<HashMap<String, PlayerAccessManager>> ( ) {
            }.getType ( );
            Gson gson = new Gson ( );
            allPlayers = gson.fromJson ( readerPlayerAccessData, typeAccessData );
            readerPlayerAccessData.close ( );
        } catch (IOException | JsonSyntaxException e) {
            Bukkit.getLogger ( ).log ( Level.SEVERE, "PlayerAccessData.json doesn't exist!", e );
        }
    }

    private void disableCustomItems () {
        CustomItemsRunnable.getBukkitRunnable ( ).cancel ( );
        WriteRunnable.getRunnable ( ).cancel ( );
        ScytheRunnable.getRun ( ).cancel ( );
        CustomItems.saveToConfig ( );
        PlayerBonusesData.saveToConfig ( );
        PlayerAccessManager.saveToConfig ( );
        //Region
        PlayerAccessManager.saveToConfig ( );
        RegionCheckRunnable.regionCheckRunnable.cancel ( );
    }

    public PermissionsManager getPermissionsManager () {
        return new PermissionsManager ( );
    }

    //DBC EVENTS
    public void damagedEvent ( IDBCEvent.DamagedEvent event ) {
        DBCDamageEvent dmgEvent = new DBCDamageEvent ( event );
        Bukkit.getPluginManager ( ).callEvent ( dmgEvent );
    }

    public void koEvent ( IDBCEvent.DBCKnockout event ) {
        DBCKnockoutEvent koEvent = new DBCKnockoutEvent ( event );
        Bukkit.getPluginManager ( ).callEvent ( koEvent );
    }
    //DBC EVENTS
    //Spacey
}