package org.delaware.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;
import org.delaware.tools.model.entities.TP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class TPSCommand extends BaseCommand {
    public static ConcurrentHashMap<Integer, TP> tps = new ConcurrentHashMap<> ( );

    @Command(name = "dbtps", aliases = "dbtps", permission = "DBCFuture.dbtps"
            , usage = "&7Prueba utilizando: &b/dbtps register <amount>")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        if (command.getSender ( ) instanceof Player && command.getArgs ( ).length >= 1) {
            Player player = command.getPlayer ( );
            String args = command.getArgs ( 0 );
            switch (args) {
                case "register":
                    int tpValue = Integer.parseInt(command.getArgs(1));

                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(CC.translate("&7Al darle clic derecho"));
                    lore.add(CC.translate("&7en el aire te da " + tpValue + " TPS."));
                    lore.add(CC.translate("&c "));
                    lore.add(CC.translate("&c&lConsumible"));

                    TP tp = new TP();
                    tp.setDisplayName(CC.translate("&e➤ &f&l" + tpValue + " TPS"))
                            .setId(player.getItemInHand().getType().getId())
                            .setLore(lore)
                            .setValue(tpValue);

                    tps.put(tpValue, tp);

                    player.sendMessage(CC.translate("&8[&bDBZ&8] &a&l➤ &eTPS registrado correctamente y listo para usar"));
                    player.playSound(player.getLocation(), "random.orb", 1.0f, 1.0f);
                    break;
                case "give":
                    tpValue = Integer.parseInt(command.getArgs(1));
                    int tpAmount = Integer.parseInt(command.getArgs(2));
                    String username = command.getArgs(3);

                    player.sendMessage(CC.translate("&8[&bDBZ&8] &a&l➤ &eHas recibido " + tpValue + " TPS en tu cuenta"));

                    if (!tps.containsKey(tpValue)) {
                        player.sendMessage(CC.translate("&8[&4&lError&8] &4&l➤ &cCantidad de TPS no registrada, ¡por favor intenta de nuevo!"));
                        return;
                    }

                    tp = tps.get(tpValue);
                    ItemStack item = wrapItemStack(tp);
                    item.setAmount(tpAmount);

                    Player player1 = Bukkit.getServer().getPlayer(username);
                    if (player1 == null) {
                        player.sendMessage(CC.translate("&8[&4&lError&8] &4&l➤ &cEl jugador no existe o no está conectado"));
                        return;
                    }

                    player1.getInventory().addItem(item);
                    player.playSound(player.getLocation(), "random.orb", 1.0f, 1.0f);

                    break;
                case "remove":
                case "delete":
                    tpValue = Integer.parseInt(command.getArgs(1));

                    if (!tps.containsKey(tpValue)) {
                        player.sendMessage(CC.translate("&8[&4&lError&8] &4&l➤ &cCantidad de TP no registrada, ¡por favor intenta de nuevo!"));
                        return;
                    }

                    player.sendMessage(CC.translate("&8[&bDBZ&8] &a&l➤ &eSe ha eliminado correctamente " + tpValue + " TP"));
                    tps.remove(tpValue);

                    player.playSound(player.getLocation(), "random.orb", 1.0f, 1.0f);
                    break;
                case "name":
                    tpValue = Integer.parseInt(command.getArgs(1));

                    if (!tps.containsKey(tpValue)) {
                        player.sendMessage(CC.translate("&8[&bDBZ&8] &4&l➤ &cCantidad de TP no registrada, ¡por favor intenta de nuevo!"));
                        return;
                    }

                    StringBuilder display = new StringBuilder();

                    if (command.length() == 3) {
                        display.append(CC.translate(command.getArgs(2)));
                    } else {
                        for (int i = 2; i < command.length(); i++) {
                            display.append(CC.translate(command.getArgs(i))).append(" ");
                        }
                    }

                    tp = tps.get(tpValue);
                    tp.setDisplayName(CC.translate(display.toString()));
                    player.setItemInHand(wrapItemStack(tp));

                    player.sendMessage(CC.translate("&8[&bDBZ&8] &a&l➤ &eNombre actualizado correctamente"));
                    player.playSound(player.getLocation(), "random.orb", 1.0f, 1.0f);
                    break;
                case "list":
                    if (tps.isEmpty()) {
                        player.sendMessage(CC.translate("&8[&bDBZ&8] &eNo hay TP registrados actualmente."));
                    } else {
                        player.sendMessage(CC.translate("&8[&bDBZ&8] &eLista de TP registrados (orden ascendente):"));

                        tps.keySet().stream()
                                .sorted()
                                .forEach(value -> {
                                    TP trainingPoint = tps.get(value);
                                    player.sendMessage(CC.translate("&6- &e" + value + " TP: &7" + trainingPoint.getDisplayName()));
                                });
                    }

                    player.playSound(player.getLocation(), "random.orb", 1.0f, 1.0f);
                    break;
                case "lore":
                    int index = 0;
                    try {
                        index = Integer.parseInt ( command.getArgs ( 1 ) );
                    } catch (NumberFormatException exception) {
                        player.sendMessage ( CC.translate ( "&8[&4&lError&8] &4&l➤ &cInvalid line number! Please enter a valid number." ) );
                        return;
                    }
                    tp = findTP ( player.getItemInHand ( ) );
                    if (tp != null) {
                        lore = tp.getLore();
                        boolean bucle = true;
                        StringBuilder loreText = new StringBuilder();

                        if (command.getArgs(2).equalsIgnoreCase(" ") || command.getArgs(2).equalsIgnoreCase("")
                                || command.getArgs(2).equalsIgnoreCase("_")) {
                            loreText.append(" ");
                            bucle = false;
                        }

                        if (bucle) {
                            for (int i = 2; i < command.length(); i++) {
                                loreText.append(command.getArgs(i)).append(" ");
                            }
                        }

                        if (index >= 0 && index < lore.size()) {
                            lore.set(index, CC.translate(loreText.toString()));
                            tp.setLore(lore);
                            player.setItemInHand(wrapItemStack(tp));
                            player.sendMessage(CC.translate("&8[&bDBZ&8] &a&l➤ &eLínea del lore actualizada correctamente en el índice " + index));
                            player.playSound(player.getLocation(), "random.orb", 1.0f, 1.0f);
                            return;
                        }
                        lore.add(CC.translate(loreText.toString()));
                        tp.setLore(lore);
                        player.setItemInHand(wrapItemStack(tp));
                        player.sendMessage(CC.translate("&8[&bDBZ&8] &a&l➤ &eNueva línea de lore añadida al final"));
                        player.playSound(player.getLocation(), "random.orb", 1.0f, 1.0f);

                        return;
                    }
                    player.sendMessage(CC.translate("&8[&bDBZ&8] &4&l➤ &c¡Cantidad de TP inválida! Por favor registra primero una cantidad de TP."));
                    break;
            }
        } else {
            command.getSender().sendMessage(CC.translate("&8[&bDBZ&8] &eLista de comandos disponibles:"));
            command.getSender().sendMessage(CC.translate("&6/dbtps register <valor> &7- Registra un nuevo valor de TP."));
            command.getSender().sendMessage(CC.translate("&6/dbtps give <valor> <cantidad> <jugador> &7- Da un ítem con el valor de TP registrado."));
            command.getSender().sendMessage(CC.translate("&6/dbtps remove <valor> &7- Elimina un valor de TP registrado."));
            command.getSender().sendMessage(CC.translate("&6/dbtps name <valor> <nombre> &7- Cambia el nombre del ítem con el valor de TP registrado."));
            command.getSender().sendMessage(CC.translate("&6/dbtps list &7- Muestra todos los valores de TP registrados."));
            command.getSender().sendMessage(CC.translate("&6/dbtps lore <línea> <lore> &7- Modifica el lore del ítem en una línea específica."));
        }

    }

    public ItemStack wrapItemStack ( TP tp ) {
        ItemStack itemStack = new ItemStack ( tp.getId ( ), 1 );
        ItemMeta meta = itemStack.getItemMeta ( );
        meta.setDisplayName ( CC.translate ( tp.getDisplayName ( ) ) );
        meta.setLore ( tp.getLore ( ) );
        itemStack.setItemMeta ( meta );
        return itemStack;
    }

    public static TP findTP ( ItemStack itemStack ) {
        if (itemStack == null || itemStack.getItemMeta ( ) == null) {
            return null;
        }
        if (tps == null || tps.isEmpty ( )) {
            return null;
        }
        int itemId = itemStack.getType ( ).getId ( );
        String itemDisplayName = itemStack.getItemMeta ( ).getDisplayName ( );
        List<String> itemLore = itemStack.getItemMeta ( ).getLore ( );

        for (TP tp : tps.values ( )) {
            if (tp.getId ( ) == itemId &&
                    Objects.equals ( tp.getDisplayName ( ), CC.translate ( itemDisplayName != null ? itemDisplayName : "" ) ) &&
                    Objects.equals ( tp.getLore ( ), itemLore )) {
                return tp;
            }
        }

        return null;
    }


}
