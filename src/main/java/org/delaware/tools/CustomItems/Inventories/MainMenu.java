package org.delaware.tools.CustomItems.Inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.delaware.tools.CC;
import org.delaware.tools.CustomItems.CustomItems;
import org.delaware.tools.General;

import java.util.ArrayList;
import java.util.Collections;

public class MainMenu implements InventoryProvider {
    private ArrayList<CustomItems> allItems;
    public MainMenu(@NonNull ArrayList<CustomItems> allItems) {
        this.allItems = allItems;
    }
    @Override
    public void init(Player player, InventoryContents inventoryContents) {
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
        meta.setDisplayName(CC.translate("&fPagina anterior"));
        previousPageItem.setItemMeta(meta);
        ItemStack nextPageItem = new ItemStack(Material.STAINED_CLAY, 1, (byte) 5);
        ItemMeta metaN = nextPageItem.getItemMeta();
        metaN.setDisplayName(CC.translate("&fPagina anterior"));
        nextPageItem.setItemMeta(metaN);
        inventoryContents.set(5, 2, ClickableItem.of(previousPageItem,
                e -> inventoryContents.inventory().open(player, pagination.previous().getPage())));
        inventoryContents.set(5, 6, ClickableItem.of(nextPageItem,
                e -> inventoryContents.inventory().open(player, pagination.next().getPage())));
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {
        inventoryContents.fillBorders(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) General.getRandomNumber(0, 15))));
    }
    private void itemClick(Player player, CustomItems item) {
        player.sendMessage("you clicked");
    }
}
