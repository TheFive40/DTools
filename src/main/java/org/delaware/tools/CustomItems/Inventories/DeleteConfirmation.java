package org.delaware.tools.CustomItems.Inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.delaware.tools.CC;
import org.delaware.tools.CustomItems.CustomItems;

public class DeleteConfirmation implements InventoryProvider {
    private CustomItems item;
    private Player player;
    public DeleteConfirmation(Player player, CustomItems item) {
        this.player = player;
        this.item = item;
    }
    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        ItemStack yes = new ItemStack(Material.STAINED_CLAY, 1, (byte) 5);
        ItemMeta yesMeta = yes.getItemMeta();
        yesMeta.setDisplayName(CC.translate("&cSí"));
        yes.setItemMeta(yesMeta);
        ItemStack no = new ItemStack(Material.STAINED_CLAY, 1, (byte) 14);
        ItemMeta noMeta = no.getItemMeta();
        noMeta.setDisplayName(CC.translate("&cNo"));
        no.setItemMeta(noMeta);
        inventoryContents.set(1, 3, ClickableItem.of(yes, e -> {
            String itemID = CustomItems.getLinkedCustomItem(item.toItemStack());
            CustomItems.removeItem(itemID);
            player.sendMessage(CC.translate("&cEliminado item " + itemID));
            inventoryContents.inventory().close(player);
        }));
        inventoryContents.set(1, 5, ClickableItem.of(no, e -> {
            inventoryContents.inventory().close(player);
        }));
    }
    @Override
    public void update(Player player, InventoryContents inventoryContents) {}
}
