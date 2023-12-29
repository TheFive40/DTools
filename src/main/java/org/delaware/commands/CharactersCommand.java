package org.delaware.commands;

import fr.minuskube.inv.ClickableItem;
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
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;
import org.delaware.tools.model.Character;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class CharactersCommand extends BaseCommand {
    private ArrayList<ArrayList<String>> lores = new ArrayList<>();
    private ArrayList<String> displayNames = new ArrayList<>();

    @Command(name = "characters", aliases = {"personajes", "characters"}, description = "Despliega el menu de tus personajes"
            , usage = "&cPrueba utilizando &7/personajes")
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        SmartInventory INVENTORY = SmartInventory.builder().title(CC.translate("&cSelector de Personajes"))
                .id("characters").size(3, 9).type(InventoryType.CHEST)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents inventoryContents) {
                        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData());
                        inventoryContents.fillBorders(ClickableItem.empty(itemStack));
                        AtomicInteger position = new AtomicInteger(0);
                        SelectCharacterCommand.characterHashMap.forEach((k, v) -> {
                            if (k.equalsIgnoreCase(player.getName())) {
                                for (Character c : v) {
                                    displayNames.add(c.getName());
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
                                    } else {
                                        wool = new ItemStack(Material.WOOL, 1, DyeColor.RED.getWoolData());
                                        woolMeta = wool.getItemMeta();
                                        woolMeta.setLore(lore);
                                        woolMeta.setDisplayName(CC.translate("&6" + c.getName()));
                                    }
                                    wool.setItemMeta(woolMeta);
                                    inventoryContents.set(1, position.addAndGet(2), ClickableItem.of(wool, Clicker -> {
                                        ItemStack itemOnClicked = Clicker.getCurrentItem();
                                        ItemMeta metaItemClicked = itemOnClicked.getItemMeta();
                                        ArrayList<String> loreClicked = (ArrayList<String>) metaItemClicked.getLore();
                                        String statusClicked = getStatus(loreClicked);
                                        if (statusClicked.equalsIgnoreCase("false")) {
                                            itemOnClicked = new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getWoolData());
                                            ItemMeta itemMetaNew = itemOnClicked.getItemMeta();
                                            itemMetaNew.setDisplayName(metaItemClicked.getDisplayName());
                                            loreClicked.add(2,CC.translate("&cStatus &a:" + "true"));
                                            itemMetaNew.setLore(loreClicked);
                                            itemOnClicked.setItemMeta(itemMetaNew);
                                            inventoryContents.set(new SlotPos(1, Clicker.getSlot()), ClickableItem.of(itemOnClicked, (Consumer<InventoryClickEvent>) Clicker));
                                            for (int j = 2; j < 9; j++) {
                                                Optional<ClickableItem> optionalClickableItem = inventoryContents.get(1, j);
                                                String displayName = optionalClickableItem.get().getItem().getItemMeta().getDisplayName();
                                                ArrayList<String> loreItem = (ArrayList<String>) optionalClickableItem.get().getItem().getItemMeta().getLore();
                                                int finalJ = j;
                                                displayNames.forEach(e->{
                                                    if(!displayName.equalsIgnoreCase(e)){
                                                      ItemStack itemStack1 =  optionalClickableItem.get().getItem();
                                                      ItemMeta itemMeta = itemStack1.getItemMeta();
                                                      itemStack1 = new ItemStack(Material.WOOL, 1, DyeColor.RED.getWoolData());
                                                      loreItem.add(2,CC.translate("&cStatus &a:" + "false"));
                                                      itemMeta.setDisplayName(itemMeta.getDisplayName());
                                                      itemMeta.setLore(loreItem);
                                                      itemStack1.setItemMeta(itemMeta);
                                                    }
                                                });
                                            }
                                        }
                                    }));
                                }

                            }
                            player.playSound(player.getLocation(), Sound.ANVIL_LAND,10F,10F);
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
}
