package org.delaware.commands;

import noppes.npcs.api.IWorld;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.api.entity.IEntity;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;
import org.delaware.tools.model.entities.PlayerData;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class CommandTransform extends BaseCommand {

    public static ConcurrentHashMap<String, IEntity<?>> entities = new ConcurrentHashMap<> ( );
    public static ConcurrentHashMap<String, Entity> entitiesBukkit = new ConcurrentHashMap<> ( );
    public static ConcurrentHashMap<String, PlayerData> playerStats = new ConcurrentHashMap<> ( );

    @Command(name = "transform", aliases = "transform")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        Player player = command.getPlayer ( );
        if (entities.containsKey ( player.getName ( ) )) {
            detransform ( player );
            return;
        }
        if (!playerStats.containsKey ( command.getPlayer ( ).getName ( ) )) {
            Entity entity = getEntityTarget ( command.getPlayer ( ), 3 );
            IWorld world = NpcAPI.Instance ( ).getIWorld ( player.getWorld ( ).getEnvironment ( ).getId ( ) );
            IEntity<?> iEntity = world.getEntityByID ( entity.getEntityId ( ) );
            if (iEntity instanceof ICustomNpc<?>) {
                IDBCPlayer dbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
                ICustomNpc<?> npc = (ICustomNpc<?>) iEntity;
                int statHealth = (int) ((npc.getMaxHealth ( ) / 22) + 1);
                int meleeStrength = (int) (npc.getMeleeStrength ( ) / 5.25);
                int STR = dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).getInteger ( General.STR );
                int DEX = dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).getInteger ( General.DEX );
                int SPI = dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).getInteger ( General.SPI );
                int WIL = dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).getInteger ( General.WIL );
                int CON = dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).getInteger ( General.CON );
                int MND = dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).getInteger ( General.MND );
                PlayerData playerData = new PlayerData ( );
                playerData.setName ( player.getName ( ) ).setDEX ( DEX ).setSTR ( STR ).setSPI ( SPI )
                        .setWIL ( WIL ).setCON ( CON ).setMND ( MND );
                playerStats.put ( player.getName ( ), playerData );
                dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.CON, statHealth );
                dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.DEX, (int) (statHealth * 0.8) );
                dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.STR, meleeStrength );
                dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.WIL, (int) (meleeStrength * 0.5) );
                dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.SPI, (int) (meleeStrength * 0.65) );
            }
            entities.put ( player.getName ( ), iEntity );
            entitiesBukkit.put ( player.getName ( ), entity );
            player.setGameMode ( GameMode.ADVENTURE );
            Bukkit.dispatchCommand ( Main.instance.getServer ( ).getConsoleSender ( ), "vanish " + player.getName ( ) );
            player.sendMessage ( CC.translate ( "&aYou have transformed into an NPC!" ) );

        }

    }

    public Entity getEntityTarget ( Player player, double range ) {
        Vector eyeLocation = player.getEyeLocation ( ).toVector ( );
        Vector direction = player.getEyeLocation ( ).getDirection ( );
        List<Entity> nearbyEntities = player.getNearbyEntities ( range, range, range );
        for (Entity entity : nearbyEntities) {
            Vector entityLocation = entity.getLocation ( ).toVector ( );
            Vector toEntity = entityLocation.subtract ( eyeLocation );
            double dotProduct = toEntity.normalize ( ).dot ( direction );
            if (dotProduct > 0.50) {
                return entity;
            }
        }
        return null;
    }

    public static void detransform ( Player player ) {
        player.sendMessage ( CC.translate ( "&aYou returned to your human form!" ) );
        entities.remove ( player.getName ( ) );
        entitiesBukkit.remove ( player.getName ( ) );
        player.setGameMode ( GameMode.SURVIVAL );
        PlayerData v = playerStats.get ( player.getName ( ) );
        try {
            IDBCPlayer dbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
            dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.STR, v.getSTR ( ) );
            dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.DEX, v.getDEX ( ) );
            dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.CON, v.getCON ( ) );
            dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.WIL, v.getWIL ( ) );
            dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.MND, v.getMND ( ) );
            dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( General.SPI, v.getSPI ( ) );
            playerStats.remove ( dbcPlayer.getName ( ) );
            Bukkit.dispatchCommand ( Main.instance.getServer ( ).getConsoleSender ( ), "vanish " + player.getName ( ) );
        } catch (NullPointerException exception) {
            return;
        }

    }

    public static Player findPlayerByEntity ( Entity entity ) {
        AtomicReference<Player> player = new AtomicReference<> ( );
        entitiesBukkit.forEach ( ( k, v ) -> {
            if (entity == v) {
                player.set ( Main.instance.getServer ( ).getPlayer ( k ) );
            }
        } );
        return player.get ( );
    }
}
