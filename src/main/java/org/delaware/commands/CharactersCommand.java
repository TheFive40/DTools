package org.delaware.commands;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.delaware.listeners.PlayerJoin;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;
import org.delaware.tools.model.Character;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class CharactersCommand extends BaseCommand {
    private ArrayList<ArrayList<String>> lores = new ArrayList<>();
    private AtomicReference<String> name = new AtomicReference<>(" ");

    private HashSet<String> displayNames = new HashSet<>();

    @Command(name = "characters", aliases = {"personajes", "characters"}, description = "Despliega el menu de tus personajes"
            , usage = "&cPrueba utilizando &7/personajes", permission = "characters.menu")
    @Override
    public void onCommand(CommandArgs command) throws IOException {

         SmartInventory INVENTORY = PlayerJoin.inventorysPlayers.get(command.getPlayer().getName()).title(CC.translate("&cSelector de Personajes"))
                .id(command.getPlayer().getName()).size(3, 9).type(InventoryType.CHEST)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents inventoryContents) {
                        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData());
                        inventoryContents.fillBorders(ClickableItem.empty(itemStack));
                        AtomicInteger position = new AtomicInteger(0);
                        SelectCharacterCommand.characterHashMap.forEach((k, v) -> {
                            if (k.equalsIgnoreCase(player.getName())) {
                                for (Character c : v) {
                                    ArrayList<String> lore = new ArrayList<>();
                                    lore.add(CC.translate("&cNivel &a" + c.getLevel()));
                                    lore.add(CC.translate("&cTps &a" + c.getTps()));
                                    lore.add(CC.translate("&cStatus &a:" + c.isInUse()));
                                    lores.add(lore);
                                    String status = getStatus(lore);
                                    ItemStack wool;
                                    ItemMeta woolMeta;
                                    if (status.toString().equalsIgnoreCase("true")) {
                                        wool = new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getWoolData());
                                        woolMeta = wool.getItemMeta();
                                        woolMeta.setLore(lore);
                                        woolMeta.setDisplayName(CC.translate("&6" + c.getName()));
                                        name.set(c.getName());
                                        displayNames.add(woolMeta.getDisplayName());
                                    } else {
                                        wool = new ItemStack(Material.WOOL, 1, DyeColor.RED.getWoolData());
                                        woolMeta = wool.getItemMeta();
                                        woolMeta.setLore(lore);
                                        woolMeta.setDisplayName(CC.translate("&6" + c.getName()));
                                        displayNames.add(woolMeta.getDisplayName());
                                    }
                                    wool.setItemMeta(woolMeta);
                                    position.getAndAdd(2);
                                    inventoryContents.set(1, position.get(), ClickableItem.of(wool, new Consumer<InventoryClickEvent>() {
                                        @Override
                                        public void accept(InventoryClickEvent inventoryClickEvent) {
                                            ItemStack itemOnClicked = inventoryClickEvent.getCurrentItem();
                                            ItemMeta metaItemClicked = itemOnClicked.getItemMeta();
                                            ArrayList<String> loreClicked = (ArrayList<String>) metaItemClicked.getLore();
                                            String statusClicked = getStatus(loreClicked);
                                            String toName = getDisplayName(itemOnClicked.getItemMeta().getDisplayName());
                                            player.performCommand("selectCharacter " + name.get() + " " + toName);
                                            if (statusClicked.equalsIgnoreCase("false")) {
                                                ItemMeta itemMetaNew = itemOnClicked.getItemMeta();
                                                itemOnClicked = new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getWoolData());
                                                loreClicked.set(2, CC.translate("&cStatus &a:" + "true"));
                                                itemMetaNew.setLore(loreClicked);
                                                itemOnClicked.setItemMeta(itemMetaNew);
                                                for (int j = 2; j < 9; j++) {
                                                    Optional<ClickableItem> optionalClickableItem = inventoryContents.get(1, j);
                                                    ItemStack finalItemOnClicked = itemOnClicked;
                                                    int finalJ = j;
                                                    optionalClickableItem.ifPresent(clickableItem -> {
                                                        String displayName = clickableItem.getItem().getItemMeta().getDisplayName();
                                                        ArrayList<String> loreItem = (ArrayList<String>) clickableItem.getItem().getItemMeta().getLore();
                                                        displayNames.forEach(e -> {
                                                            if(displayName!=null){
                                                                if (!CC.translate(finalItemOnClicked.getItemMeta().getDisplayName()).equalsIgnoreCase(CC.translate(e))) {
                                                                    ItemStack itemStack1 = clickableItem.getItem();
                                                                    ItemMeta itemMeta = itemStack1.getItemMeta();
                                                                    itemStack1 = new ItemStack(Material.WOOL, 1, DyeColor.RED.getWoolData());
                                                                    loreItem.set(2, CC.translate("&cStatus &a:" + "false"));
                                                                    itemMeta.setDisplayName(e);
                                                                    itemMeta.setLore(loreItem);
                                                                    itemStack1.setItemMeta(itemMeta);
                                                                    inventoryContents.set(new SlotPos(1, finalJ), ClickableItem.of(itemStack1, this));
                                                                }
                                                            }
                                                        });
                                                    });
                                                }
                                                int clickedSlot = inventoryClickEvent.getSlot();
                                                int columns = 9;
                                                int columnClicked = clickedSlot % columns;
                                                inventoryContents.set(new SlotPos(1, columnClicked), ClickableItem.of(itemOnClicked, this));
                                            }
                                            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 10F, 10F);
                                            player.closeInventory();
                                        }
                                    }));

                                }

                            }
                        });
                    }

                    @Override
                    public void update(Player player, InventoryContents inventoryContents) {
                        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData());
                        inventoryContents.fillBorders(ClickableItem.empty(itemStack));
                    }
                }).build();
        INVENTORY.open(command.getPlayer());
    }

    public String getStatus(ArrayList<String> lore) {
        String loreStatus = lore.get(2);
        int indexStatus = loreStatus.indexOf(":") + 1;
        StringBuilder status = new StringBuilder();
        for (int j = indexStatus; j < loreStatus.length(); j++) {
            status.append(loreStatus.charAt(j));
        }
        return status.toString();
    }
    public String getDisplayName(String DisplayName){
        int indexInit = DisplayName.indexOf('&')+2;
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = indexInit;i<DisplayName.length();i++){
            stringBuilder.append(DisplayName.charAt(i));
        }
        return stringBuilder.toString();
    }
}
