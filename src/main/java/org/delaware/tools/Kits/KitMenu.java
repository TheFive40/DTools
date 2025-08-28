package org.delaware.tools.Kits;


import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotIterator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.delaware.tools.CC;
import org.delaware.tools.CustomItems.CustomItems;
import org.delaware.tools.NbtHandler.NbtHandler;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class KitMenu implements InventoryProvider {
    private final Random random = new Random ( );

    private final String category;

    public KitMenu ( String category ) {
        this.category = category;
    }

    public static SmartInventory getInventory ( Player player, String category ) {
        return SmartInventory.builder ( )
                .id ( "kitMenu:" + category )
                .provider ( new KitMenu ( category ) )
                .size ( 3, 9 )
                .title ( CC.translate ( "&8Kit de &a" + category ) )
                .build ( );
    }

    @Override
    public void init ( Player player, InventoryContents contents ) {
        contents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE ) ) );

        if (KitClaimTracker.hasClaimed ( player.getUniqueId ( ), category )) {
            contents.set ( 1, 4, ClickableItem.empty ( createItem ( Material.ANVIL, "&cYa reclamaste este kit." ) ) );
            return;
        }
        contents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, (short) 10 ) ) );
        Set<String> cItems = CustomItems.getAllCustomItems ( );
        List<CustomItems> filteredCustomItems = new ArrayList<> ( );
        List<ItemStack> kitItems = KitStorage.getItemsForCategory ( category );
        HashMap<String, String> lores = new HashMap<> ( );
        for (String customKey : cItems) {
            CustomItems customItem = CustomItems.getCustomItem ( customKey );
            ItemStack customStack = customItem.toItemStack ( );
            for (ItemStack kitItem : kitItems) {
                if (kitItem == null || customStack == null) continue;

                String kitName = kitItem.hasItemMeta ( ) && kitItem.getItemMeta ( ).hasDisplayName ( )
                        ? kitItem.getItemMeta ( ).getDisplayName ( ) : "";
                String customName = customStack.hasItemMeta ( ) && customStack.getItemMeta ( ).hasDisplayName ( )
                        ? customStack.getItemMeta ( ).getDisplayName ( ) : "";

                List<String> kitLore = kitItem.hasItemMeta ( ) && kitItem.getItemMeta ( ).hasLore ( )
                        ? new ArrayList<> ( kitItem.getItemMeta ( ).getLore ( ) ) : Collections.emptyList ( );
                List<String> customLore = customStack.hasItemMeta ( ) && customStack.getItemMeta ( ).hasLore ( )
                        ? customStack.getItemMeta ( ).getLore ( ) : Collections.emptyList ( );
                if (!kitLore.isEmpty ( )) {
                    lores.put ( customName, CC.translate ( kitLore.get ( kitLore.size ( ) - 1 ) ) );
                    kitLore = kitLore.subList ( 0, kitLore.size ( ) - 1 );
                    if (kitName.equals ( customName ) && kitLore.equals ( customLore )) {
                        filteredCustomItems.add ( customItem );
                        break;
                    }
                }
            }
        }
        SlotIterator it = contents.newIterator ( SlotIterator.Type.HORIZONTAL, 1, 1 );
        it.blacklist ( 1, 4 );
        for (CustomItems item : filteredCustomItems) {
            it.next ( ).set ( ClickableItem.empty ( item.toItemStack ( ) ) );
        }
        AtomicInteger count = new AtomicInteger ( );
        contents.set ( 1, 4, ClickableItem.of (
                createItem ( Material.EMERALD_BLOCK, "&a&lReclamar Kit" ),
                e -> {
                    if (!KitClaimTracker.hasClaimed ( player.getUniqueId ( ), category )) {
                        for (CustomItems item : filteredCustomItems) {
                            ItemStack itemStack = item.toItemStack();
                            if (itemStack == null || itemStack.getType() == Material.AIR) continue;

                            ItemMeta meta = itemStack.getItemMeta();
                            if (meta == null) continue;

                            List<String> lore = meta.getLore();
                            if (lore == null) lore = new ArrayList<>();

                            String extraLore = lores.get(item.getDisplayName());
                            if (extraLore != null) {
                                lore.add(CC.translate(extraLore));
                            }

                            meta.setLore(lore);
                            itemStack.setItemMeta(meta);
                            player.getInventory().addItem(itemStack);
                        }
                        KitClaimTracker.markClaimed ( player.getUniqueId ( ), category );
                        player.sendMessage ( CC.translate ( "&aReclamaste tu kit de &l" + category ) );
                        player.closeInventory ( );
                    }
                }
        ) );
    }

    @Override
    public void update ( Player player, InventoryContents contents ) {
        int state = contents.property ( "state", 0 );
        contents.setProperty ( "state", state + 1 );

        if (state % 5 != 0)
            return;

        short durability = (short) random.nextInt ( 15 );

        ItemStack glass = new ItemStack ( Material.STAINED_GLASS_PANE, 1, durability );
        contents.fillBorders ( ClickableItem.empty ( glass ) );
    }

    private ItemStack createItem ( Material material, String name ) {
        ItemStack item = new ItemStack ( material );
        NbtHandler nbtHandler = new NbtHandler ( item );

        if (item.hasItemMeta ( )) {
            ItemMeta meta = item.getItemMeta ( );
            if (meta.hasLore ( )) {
                for (String line : meta.getLore ( )) {
                    if (line.contains ( "Usages:" )) {
                        String raw = line.replaceAll ( "§[0-9a-fk-or]", "" );
                        String[] parts = raw.split ( ": " )[1].split ( "/" );
                        int usages = Integer.parseInt ( parts[0] );
                        int maxUses = Integer.parseInt ( parts[1] );
                        nbtHandler.setInteger ( "usages", usages );
                        nbtHandler.setInteger ( "maxUses", maxUses );
                        item = nbtHandler.getItemStack ( );
                        break;
                    }
                }
            }
        }
        nbtHandler.setString ( "CUSTOMID", "KITSYSTEM" );
        ItemMeta meta = item.getItemMeta ( );
        if (meta != null) {
            meta.setDisplayName ( CC.translate ( name ) );
            List<String> lore = new ArrayList<> ( );
            lore.add ( "§c§lRECLAMAR" );
            meta.setLore ( lore );
            item.setItemMeta ( meta );
        }

        return item;
    }

}

