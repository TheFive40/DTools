package org.delaware.tools.CustomItems.Inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.CustomItems.CustomItems;
import org.delaware.tools.General;
import org.delaware.tools.input.PlayerInput;

import java.util.ArrayList;
import java.util.Collections;

public class MainMenu implements InventoryProvider {
    private final ArrayList<CustomItems> allItems;
    public MainMenu(@NonNull ArrayList<CustomItems> allItems) {
        this.allItems = allItems;
    }
    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        inventoryContents.fillBorders(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 9)));
        Pagination pagination = inventoryContents.pagination();
        ClickableItem[] clickableItems = new ClickableItem[this.allItems.size()];
        for(int i = 0; i < this.allItems.size(); i++) {
            CustomItems item = this.allItems.get(i);
            clickableItems[i] = ClickableItem.of(item.toItemStack(), e -> itemClick(player, item));
        }
        pagination.setItems(clickableItems);
        pagination.setItemsPerPage(28);
        SlotIterator iterator = inventoryContents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1);
        iterator.blacklist(1, 8);
        iterator.blacklist(2, 8);
        iterator.blacklist(3, 8);
        iterator.blacklist(2, 0);
        iterator.blacklist(3, 0);
        iterator.blacklist(4, 0);
        pagination.addToIterator(iterator);
        ItemStack previousPageItem = new ItemStack(Material.STAINED_CLAY, 1, (byte) 14);
        ItemMeta meta = previousPageItem.getItemMeta();
        meta.setDisplayName(CC.translate("&fPágina anterior"));
        previousPageItem.setItemMeta(meta);
        ItemStack nextPageItem = new ItemStack(Material.STAINED_CLAY, 1, (byte) 5);
        ItemMeta metaN = nextPageItem.getItemMeta();
        metaN.setDisplayName(CC.translate("&fSiguiente página"));
        nextPageItem.setItemMeta(metaN);
        ItemStack createItem = new ItemStack(Material.DIAMOND_BLOCK);
        ItemMeta createMeta = createItem.getItemMeta();
        createMeta.setDisplayName(CC.translate("&aCrear nuevo item"));
        createItem.setItemMeta(createMeta);
        inventoryContents.set(5, 2, ClickableItem.of(previousPageItem,
                e -> inventoryContents.inventory().open(player, pagination.previous().getPage())));
        inventoryContents.set(5, 6, ClickableItem.of(nextPageItem,
                e -> inventoryContents.inventory().open(player, pagination.next().getPage())));
        inventoryContents.set(0, 8, ClickableItem.of(createItem, e -> createItem(player, inventoryContents.inventory())));
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {}
    private void itemClick(Player player, CustomItems item) {
        final SmartInventory NEW_INV = SmartInventory.builder().provider(new ConfigMenu(item)).size(6, 9).id("ItemsConfig").title(CC.translate("&eConfiguración")).build();
        NEW_INV.open(player);
    }
    private void createItem(Player player, SmartInventory inventory) {
        player.sendMessage(CC.translate("&6------------------------"));
        player.sendMessage(CC.translate("&eIngresa la ID del item a crear"));
        player.sendMessage(CC.translate("&eNOTA: Debes tener un item en la mano"));
        player.sendMessage(CC.translate("&6------------------------"));
        inventory.close(player);
        PlayerInput input = new PlayerInput(player, Main.instance);
        input.waitForPlayerInput(30, "&cTiempo de espera agotado", ID -> {
            if(player.getItemInHand().getType().equals(Material.AIR)) {
                player.sendMessage(CC.translate("&cDebes tener un item en la mano!"));
                inventory.open(player);
                return;
            }
            if(ID.contains(" ")) ID = ID.replace(" ", "");
            if(CustomItems.contains(ID.toUpperCase().trim())) {
                player.sendMessage(CC.translate("&cItem con ID " + ID + " ya existe en la config!"));
                inventory.open(player);
                return;
            }
            CustomItems add = new CustomItems(player.getItemInHand());
            add.addCustomItem(ID.toUpperCase().trim());
            player.sendMessage(CC.translate("&aItem agregado correctamente"));
            allItems.add(CustomItems.getCustomItem(ID));
            inventory.open(player);
        });
    }
}
