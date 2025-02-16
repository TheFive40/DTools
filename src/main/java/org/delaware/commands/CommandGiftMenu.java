package org.delaware.commands;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;
import java.util.List;

public class CommandGiftMenu extends BaseCommand {
    SmartInventory INVENTORY;
    @Command(name = "regalos", aliases = "regalos", permission = "dbcplugin.regalo")
    @Override
    public void onCommand( CommandArgs command) throws IOException {
        if(!CommandAddGift.itemStackHashMap.isEmpty()){
            INVENTORY = SmartInventory.builder().type(InventoryType.CHEST)
                    .size(6, 9).title( CC.translate("&c&lUBICACION DE REGALOS")).id("regalosnavidad")
                    .provider(new InventoryProvider() {
                        @Override
                        public void init(Player player, InventoryContents inventoryContents) {
                            inventoryContents.fillBorders(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE)));
                            ClickableItem[] clickableItem = new ClickableItem[CommandAddGift.itemStacks.size()];
                            Pagination pagination = inventoryContents.pagination();
                            for (int i = 0; i < clickableItem.length; i++) {
                                int finalI = i;
                                clickableItem[i] = ClickableItem.of(CommandAddGift.itemStacks.get(i), UPDATE->{
                                    List<String> lore = CommandAddGift.itemStacks.get(finalI).getItemMeta().getLore();
                                    String coords = lore.get(0);
                                    String coords_sin_espacios = coords.trim();
                                    int inicio_x = coords_sin_espacios.indexOf('x')+3;
                                    String valor_x = "";
                                    int x = 0;
                                    int fin_x = coords.trim().indexOf(" y");
                                    for(int j = inicio_x;j<fin_x;j++){
                                        valor_x += coords_sin_espacios.charAt(j);
                                        if (General.isConvertibleToInt ( valor_x)){
                                            x = Integer.parseInt(valor_x);
                                        }
                                    }
                                    int inicio_y = coords_sin_espacios.indexOf('y')+3;
                                    String valor_y = "";
                                    int y = 0;
                                    int fin_y = coords.trim().indexOf(" z");
                                    for(int j = inicio_y;j<fin_y;j++){
                                        valor_y += coords_sin_espacios.charAt(j);
                                        if (General.isConvertibleToInt ( valor_y )){
                                            y = Integer.parseInt(valor_y);
                                        }
                                    }
                                    int inicio_z = coords_sin_espacios.indexOf('z')+3;
                                    String valor_z = "";
                                    int z = 0;
                                    for(int j = inicio_z;j<coords_sin_espacios.length();j++){
                                        valor_z += coords_sin_espacios.charAt(j);
                                        if (General.isConvertibleToInt ( valor_z )){
                                            z = Integer.parseInt(valor_z);
                                        }
                                    }
                                    String wordLore = lore.get(1).trim();
                                    int inicio = wordLore.indexOf(":")+4;
                                    String world = "";
                                    for(int j = inicio;j<wordLore.length();j++){
                                        world += wordLore.charAt(j);
                                    }
                                    World worldTeleport = Main.instance.getServer().getWorld(world);
                                    Location location = new Location(worldTeleport, x,y+1,z);
                                    player.sendMessage(CC.translate("&cTeletransportando..."));
                                    player.teleport(location);
                                });
                            }
                            pagination.setItems(clickableItem);
                            pagination.setItemsPerPage(21);
                            ItemStack previous = new ItemStack(Material.ARROW);
                            ItemMeta itemMeta = previous.getItemMeta();
                            itemMeta.setDisplayName(CC.translate("&c&lAtrás"));
                            ItemStack next = new ItemStack(Material.ARROW);
                            ItemMeta itemMetaNext = next.getItemMeta();
                            itemMetaNext.setDisplayName(CC.translate("&2&lSiguiente"));
                            previous.setItemMeta(itemMeta);
                            next.setItemMeta(itemMetaNext);
                            pagination.addToIterator(inventoryContents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1));
                            inventoryContents.set(4, 3, ClickableItem.of(previous,
                                    e -> INVENTORY.open(command.getPlayer(), pagination.previous().getPage())));
                            inventoryContents.set(4, 5, ClickableItem.of(next,
                                    e -> INVENTORY.open(command.getPlayer(), pagination.next().getPage())));

                        }
                        @Override
                        public void update(Player player, InventoryContents inventoryContents) {
                            inventoryContents.fillBorders(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE)));
                        }
                    }).build();
            INVENTORY.open(command.getPlayer());
            return;
        }
        command.getPlayer().sendMessage(CC.translate("&cAl parecer no hay regalos disponibles"));
    }
}
