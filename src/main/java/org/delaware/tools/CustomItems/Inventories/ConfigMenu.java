package org.delaware.tools.CustomItems.Inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.CustomItems.CustomItems;
import org.delaware.tools.Pastebin.PastebinReader;
import org.delaware.tools.input.PlayerInput;

import java.util.List;

public class ConfigMenu implements InventoryProvider {
    private CustomItems item;
    public ConfigMenu(CustomItems item) {
        this.item = item;
    }
    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        inventoryContents.fillBorders(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 11)));
        inventoryContents.set(4, 1, ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 11)));
        inventoryContents.set(4, 7, ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 11)));
        inventoryContents.set(5, 0, null);
        inventoryContents.set(5, 8, null);
        inventoryContents.set(0, 4, ClickableItem.empty(item.toItemStack()));
        ItemStack changeName = new ItemStack(Material.BOOK_AND_QUILL);
        ItemMeta metaName = changeName.getItemMeta();
        metaName.setDisplayName(CC.translate("&eCambiar Nombre"));
        changeName.setItemMeta(metaName);
        ItemStack changeLore = new ItemStack(Material.BOOK_AND_QUILL);
        ItemMeta metaLore = changeLore.getItemMeta();
        metaLore.setDisplayName(CC.translate("&dCambiar Lore"));
        changeLore.setItemMeta(metaLore);
        inventoryContents.set(1, 2, ClickableItem.of(changeName, e -> changeItemName(player, inventoryContents.inventory())));
        inventoryContents.set(1, 4, ClickableItem.of(changeLore, e -> changeItemLore(player, inventoryContents.inventory())));
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {}
    private void changeItemName(Player player, SmartInventory inventory) {
        player.sendMessage(CC.translate("&6------------------------"));
        player.sendMessage(CC.translate("&eIngresa un nombre nuevo"));
        player.sendMessage(CC.translate("&6------------------------"));
        inventory.close(player);
        PlayerInput input = new PlayerInput(player, Main.instance);
        input.waitForPlayerInput(20, "&cTiempo de espera agotado", newName -> {
            String itemID = CustomItems.getLinkedCustomItem(item.toItemStack());
            item.setDisplayName(CC.translate(newName));
            player.sendMessage(CC.translate("&6Nombre del item &e" + itemID + " &6cambiado a &e" + newName));
            CustomItems.items.put(itemID, item);
            inventory.open(player);
        });
    }
    private void changeItemLore(Player player, SmartInventory inventory) {
        player.sendMessage(CC.translate("&6------------------------"));
        player.sendMessage(CC.translate("&eIngresa el nuevo lore"));
        player.sendMessage(CC.translate("&eNOTA: Debes utilizar links de pastebin"));
        player.sendMessage(CC.translate("&6------------------------"));
        inventory.close(player);
        PlayerInput input = new PlayerInput(player, Main.instance);
        input.waitForPlayerInput(40, "&cTiempo de espera agotado", pastebinUrl -> {
            Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
                List<String> newLore = PastebinReader.getFromPastebin(pastebinUrl);
                Bukkit.getScheduler().runTask(Main.instance, () -> {
                    if(newLore == null) {
                        player.sendMessage(CC.translate("&cHa ocurrido un error al descargar el texto!"));
                        return;
                    }
                    String itemID = CustomItems.getLinkedCustomItem(item.toItemStack());
                    item.setLore(newLore);
                    player.sendMessage(CC.translate("&6Lore del item &e" + itemID + "&6 cambiado correctamente!"));
                    CustomItems.items.put(itemID, item);
                    inventory.open(player);
                });
            });
        });
    }
}
