package org.delaware.events;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.delaware.tools.model.entities.DBItem;
import static org.delaware.commands.DBItemsCommand.isDBItem;
import static org.delaware.commands.DBItemsCommand.wrapDBItem;
import static org.delaware.events.PlayerHeld.players;

public class DropItem implements Listener {
    @EventHandler
    public void onDropItem ( PlayerDropItemEvent event ) {
        Player player = event.getPlayer ( );
        ItemStack item = event.getItemDrop ( ).getItemStack ( );
        DBItem dbItem = wrapDBItem ( item );
        if (item != null && isDBItem ( item ) && players.contains ( player.getName ( ) )) {
            if (dbItem.getType () != null){
                if (dbItem.getType ().equalsIgnoreCase ( "SWORD" ) || dbItem.getType ().equalsIgnoreCase ( "ITEM" )){
                    PlayerHeld.restorePlayerData ( player, dbItem );
                }
            }
        }
    }
}
