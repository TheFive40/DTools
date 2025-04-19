package org.delaware.events;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.delaware.Main;
import org.delaware.commands.CommandAddGift;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.model.entities.Localizaciones;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class InteractWithGiftsEvent implements Listener {
    public static HashMap<String, Integer> contadorRegalos = new HashMap<>();
    public static HashMap<String, ArrayList<Localizaciones>> regalosEncontrados = new HashMap<>();
    public static int reward = 3000000;
    public static int MULTIPLIER = 1800;
    public static ArrayList<String> misionCompletada = new ArrayList<>();
    private String message = "---------------------------------------------";

    @EventHandler
    public void onBlockInteractEvent(PlayerInteractEvent event) {
        int messageWidth = message.length();
        int spacesToAdd = (int) ((60 - messageWidth) / 2);
        String centeredMessage01 = new String(new char[spacesToAdd]).replace("\0", " ");
        Player player = event.getPlayer();
        try{
            Location location = event.getClickedBlock().getLocation();
            boolean encontroRegalo = false;
            for(Localizaciones localizaciones : CommandAddGift.itemStackHashMap){
                if(localizaciones.getBloqueX() == location.getBlockX() && localizaciones.getBloqueY() == location.getBlockY()
                && localizaciones.getBloqueZ() == location.getBlockZ() && location.getWorld().getName().equalsIgnoreCase(localizaciones.getWorld())){
                    encontroRegalo = true;
                }
            }
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && encontroRegalo
                    && !contadorRegalos.containsKey(event.getPlayer().getName())) {
                contadorRegalos.put(event.getPlayer().getName(), 1);
                event.getPlayer().sendMessage(CC.translate("&a¡Enhorabuena! encontraste  &6" + contadorRegalos.get(event.getPlayer().getName()) + "/" + CommandAddGift.itemStackHashMap.size() + " &2Regalos"));
                player.playSound(player.getLocation(), Sound.FIREWORK_BLAST2, 10f, 10f);
                ArrayList<Localizaciones> arrayList = new ArrayList();
                arrayList.add(new Localizaciones(location.getBlockX(),
                        location.getBlockY(), location.getBlockZ(), location.getBlock().getWorld().getName()));
                regalosEncontrados.put(event.getPlayer().getName(), arrayList);
                DecimalFormat formatter = new DecimalFormat ( "#,###" );
                int reward = General.getLVL ( player ) * 2;
                player.sendMessage ( CC.translate ( "&8[&b+&8] &b" + formatter.format(reward) ) );
                org.delaware.tools.General.setPlayerTps(player, reward);
            } else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && encontroRegalo
                    && contadorRegalos.containsKey(event.getPlayer().getName()) && regalosEncontrados.containsKey(event.getPlayer().getName())) {

                ArrayList<Localizaciones> localizacionesArrayList = regalosEncontrados.get(event.getPlayer().getName());
                boolean regaloYaEncontrado = false;
                for(Localizaciones localizaciones : localizacionesArrayList){
                    if(localizaciones.getBloqueX() == location.getBlockX() &&
                    localizaciones.getBloqueY() == location.getBlockY() && localizaciones.getBloqueZ() == location.getBlockZ()
                     && localizaciones.getWorld().equalsIgnoreCase(location.getBlock().getWorld().getName())){
                        regaloYaEncontrado = true;
                    }
                }

                if (regaloYaEncontrado) {
                    player.sendMessage(CC.translate("&4¡Ya encontraste este regalo!"));
                    return;
                }
                contadorRegalos.put(event.getPlayer().getName(), contadorRegalos.get(event.getPlayer().getName()) + 1);
                ArrayList<Localizaciones> arrayList = regalosEncontrados.get(player.getName());
                arrayList.add(new Localizaciones(location.getBlockX(),
                        location.getBlockY(), location.getBlockZ(), location.getBlock().getWorld().getName()));
                regalosEncontrados.put(event.getPlayer().getName(), arrayList);
                event.getPlayer().sendMessage(CC.translate("&a¡Enhorabuena! encontraste  &6" + contadorRegalos.get(event.getPlayer().getName()) + "/" + CommandAddGift.itemStackHashMap.size() + " &2Regalos"));
                player.playSound(player.getLocation(), Sound.FIREWORK_BLAST2, 10f, 10f);
                DecimalFormat formatter = new DecimalFormat ( "#,###" );
                int reward = General.getLVL ( player ) * 2;
                player.sendMessage ( CC.translate ( "&8[&b+&8] &b" + formatter.format(reward) ) );
                org.delaware.tools.General.setPlayerTps(player, reward);
            }
            DecimalFormat formatter = new DecimalFormat ( "#,###" );
            if(contadorRegalos.get(player.getName()) == CommandAddGift.itemStackHashMap.size() && !misionCompletada.contains(player.getName())){
                int lvl = General.getLVL (player );
                reward = lvl * MULTIPLIER;
                Main.instance.getServer().broadcastMessage(CC.translate("&5&l&m---") + CC.translate("-------&d&l&m----------&5&l&m----------&d&l&m----------&5&l&m-----"));
                Main.instance.getServer().broadcastMessage("");
                Main.instance.getServer().broadcastMessage(centeredMessage01 + CC.translate("&6¡El jugador &c" + player.getName() + "&6 ha completado todos los regalos"));
                Main.instance.getServer().broadcastMessage("");
                Main.instance.getServer().broadcastMessage(centeredMessage01 + CC.translate("&6¡Ha obtenido como recompensa :&e "+  formatter.format(reward) + " TPS" ));
                Main.instance.getServer().broadcastMessage("");
                Main.instance.getServer().broadcastMessage(centeredMessage01 + CC.translate("&6¡Gracias por participar en el evento!"));
                Main.instance.getServer().broadcastMessage("");
                Main.instance.getServer().broadcastMessage( CC.translate("&5&l&m---") + CC.translate("-------&d&l&m----------&5&l&m----------&d&l&m----------&5&l&m-----"));
                misionCompletada.add(player.getName());
                org.delaware.tools.General.setPlayerTps(player, reward);
                player.sendMessage ( CC.translate ( "&8[&b+&8] &b" + formatter.format(reward) ) );
            }
            }catch (NullPointerException e){}

        }

}
