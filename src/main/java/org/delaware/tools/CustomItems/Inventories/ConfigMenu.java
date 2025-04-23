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
import org.delaware.tools.General;
import org.delaware.tools.Pastebin.PastebinReader;
import org.delaware.tools.input.PlayerInput;

import java.util.ArrayList;
import java.util.List;

public class ConfigMenu implements InventoryProvider {
    private final CustomItems item;
    private final String itemID;
    public ConfigMenu(CustomItems item) {
        this.item = item;
        this.itemID = CustomItems.getLinkedCustomItem(item.toItemStack());
    }
    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        inventoryContents.fillBorders(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 11)));
        inventoryContents.set(4, 1, ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 11)));
        inventoryContents.set(4, 7, ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 11)));
        inventoryContents.set(5, 0, null);
        inventoryContents.set(5, 8, null);
        ItemStack itemCopy = item.toItemStack();
        ItemMeta metaCopy = itemCopy.getItemMeta();
        List<String> lore = metaCopy.getLore();
        if(lore == null) {
            lore = new ArrayList<>();
            lore.add(CC.translate("&6ID: &e" + this.itemID));
        }else lore.add(CC.translate("&6ID: &e" + this.itemID));
        metaCopy.setLore(lore);
        itemCopy.setItemMeta(metaCopy);
        inventoryContents.set(0, 4, ClickableItem.empty(itemCopy));
        ItemStack changeName = new ItemStack(Material.BOOK_AND_QUILL);
        ItemMeta metaName = changeName.getItemMeta();
        metaName.setDisplayName(CC.translate("&eCambiar nombre"));
        changeName.setItemMeta(metaName);
        ItemStack changeLore = new ItemStack(Material.BOOK_AND_QUILL);
        ItemMeta metaLore = changeLore.getItemMeta();
        metaLore.setDisplayName(CC.translate("&dCambiar lore"));
        changeLore.setItemMeta(metaLore);
        ItemStack changeDmg = new ItemStack(Material.IRON_SWORD);
        ItemMeta metaDmg = changeDmg.getItemMeta();
        metaDmg.setDisplayName(CC.translate("&cModificar daño"));
        changeDmg.setItemMeta(metaDmg);
        ItemStack changeMaterial = new ItemStack(Material.BOOKSHELF);
        ItemMeta metaMaterial = changeMaterial.getItemMeta();
        metaMaterial.setDisplayName(CC.translate("&5Modificar material"));
        changeMaterial.setItemMeta(metaMaterial);
        ItemStack changeTexture = new ItemStack(Material.STONE);
        ItemMeta textureMeta = changeTexture.getItemMeta();
        textureMeta.setDisplayName(CC.translate("&fComing soon..."));
        changeTexture.setItemMeta(textureMeta);
        ItemStack changeUnbreakable = new ItemStack(Material.ANVIL);
        ItemMeta metaUnbreakable = changeUnbreakable.getItemMeta();
        if(item.isUnbreakable()) metaUnbreakable.setDisplayName(CC.translate("&8Irrompible: &aON"));
        else metaUnbreakable.setDisplayName(CC.translate("&8Irrompible: &cOFF"));
        changeUnbreakable.setItemMeta(metaUnbreakable);
        ItemStack giveItem = new ItemStack(Material.COMMAND);
        ItemMeta metaGive = giveItem.getItemMeta();
        metaGive.setDisplayName(CC.translate("&aObtener el item."));
        giveItem.setItemMeta(metaGive);
        inventoryContents.set(1, 2, ClickableItem.of(changeName, e -> changeItemName(player, inventoryContents.inventory())));
        inventoryContents.set(1, 4, ClickableItem.of(changeLore, e -> changeItemLore(player, inventoryContents.inventory())));
        inventoryContents.set(1, 6, ClickableItem.of(changeDmg, e -> changeItemDmg(player, inventoryContents.inventory())));
        inventoryContents.set(2, 2, ClickableItem.of(changeMaterial, e -> changeItemMaterial(player, inventoryContents.inventory())));
        inventoryContents.set(2, 4, ClickableItem.empty(changeTexture));
        inventoryContents.set(2, 6, ClickableItem.of(changeUnbreakable, e -> changeItemUnbreakable(player, inventoryContents.inventory())));
        inventoryContents.set(3, 2, ClickableItem.of(giveItem, e -> player.getInventory().addItem(item.toItemStack())));
        inventoryContents.set(3, 4, ClickableItem.empty(changeTexture));
        inventoryContents.set(3, 6, ClickableItem.empty(changeTexture));
        ItemStack closeInv = new ItemStack(Material.STAINED_CLAY, 1, (byte) 14);
        ItemMeta closeMeta = closeInv.getItemMeta();
        closeMeta.setDisplayName(CC.translate("&cCerrar inventario"));
        closeInv.setItemMeta(closeMeta);
        inventoryContents.set(5, 0, ClickableItem.of(closeInv, e -> inventoryContents.inventory().close(player)));
        ItemStack deleteItem = new ItemStack(Material.LAVA_BUCKET);
        ItemMeta deleteMeta = deleteItem.getItemMeta();
        deleteMeta.setDisplayName(CC.translate("&4Eliminar item"));
        deleteItem.setItemMeta(deleteMeta);
        inventoryContents.set(5, 8, ClickableItem.of(deleteItem, e -> {
            final SmartInventory INV = SmartInventory.builder().provider(new DeleteConfirmation(player, item)).title(CC.translate("&6&l¿Estás seguro?")).size(3, 9).build();
            inventoryContents.inventory().close(player);
            INV.open(player);
        }));
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
                    item.setLore(newLore);
                    player.sendMessage(CC.translate("&6Lore del item &e" + itemID + "&6 cambiado correctamente!"));
                    CustomItems.items.put(itemID, item);
                    inventory.open(player);
                });
            });
        });
    }
    private void changeItemDmg(Player player, SmartInventory inventory) {
        player.sendMessage(CC.translate("&6------------------------"));
        player.sendMessage(CC.translate("&eIngresa el nuevo daño"));
        player.sendMessage(CC.translate("&eNOTA: Deben ser números enteros"));
        player.sendMessage(CC.translate("&6------------------------"));
        inventory.close(player);
        PlayerInput input = new PlayerInput(player, Main.instance);
        input.waitForPlayerInput(20, "&cTiempo de espera agotado", newDmg -> {
            if(!General.isConvertibleToInt(newDmg)) {
                player.sendMessage(CC.translate("&cEl daño debe ser un número entero!"));
                return;
            }
            item.setDamage(Integer.parseInt(newDmg));
            player.sendMessage(CC.translate("&6Nuevo daño aplicado al item &e" + itemID));
            CustomItems.items.put(itemID, item);
            inventory.open(player);
        });
    }
    private void changeItemMaterial(Player player, SmartInventory inventory) {
        player.sendMessage(CC.translate("&6------------------------"));
        player.sendMessage(CC.translate("&eIngresa la nueva ID"));
        player.sendMessage(CC.translate("&eNOTA: Debe ser un número entero (IDs)"));
        player.sendMessage(CC.translate("&6------------------------"));
        inventory.close(player);
        PlayerInput input = new PlayerInput(player, Main.instance);
        input.waitForPlayerInput(30, "&cTiempo de espera agotado", newID -> {
            if(!General.isConvertibleToInt(newID)) {
                player.sendMessage(CC.translate("&cID inválida!"));
                return;
            }
            item.setItemID(Integer.parseInt(newID));
            player.sendMessage(CC.translate("&6ID del item &e " + itemID + " &6cambiada correctamente"));
            CustomItems.items.put(itemID, item);
            inventory.open(player);
        });
    }
    private void changeItemUnbreakable(Player player, SmartInventory inventory) {
        item.setUnbreakable(!item.isUnbreakable());
        inventory.close(player);
        inventory.open(player);
    }
}