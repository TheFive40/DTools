package org.delaware.tools;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.delaware.Main;
import org.delaware.events.PlayerChatEvent;

import java.text.DecimalFormat;
import java.util.*;

public class BankInventory implements InventoryProvider {
    private static final Map<String, Long> cooldownMap = new HashMap<> ();
    private static final long COOLDOWN_TIME = 2 * 60 * 1000; // 2 minutos en milisegundos

    private boolean isOnCooldown(Player player) {
        long currentTime = System.currentTimeMillis();
        return cooldownMap.containsKey(player.getName()) &&
                (currentTime - cooldownMap.get(player.getName()) < COOLDOWN_TIME);
    }

    private void setCooldown(Player player) {
        cooldownMap.put(player.getName(), System.currentTimeMillis());
    }
    public static void openTrader(Player player) {
        BankInventory.getInventory().open(player);
    }
    @Override
    public void init ( Player player, InventoryContents inventoryContents ) {
        ItemStack border = new ItemStack( Material.STAINED_GLASS_PANE, 1, (short) 10);
        inventoryContents.fillBorders ( ClickableItem.empty (  new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData ( ) )  ) );
        inventoryContents.set ( 1,1,ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData ( ) )  ) );
        inventoryContents.set ( 1,7,ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData ( ) )  ) );
        ItemStack depositar = new ItemStack ( 6078 );
        ItemMeta depositarMeta = depositar.getItemMeta ( );
        depositarMeta.setDisplayName ( CC.translate ( "&eDepositar" ) );
        depositar.setItemMeta ( depositarMeta );
        inventoryContents.set ( 2, 2, ClickableItem.of ( depositar, e -> {
            if (isOnCooldown(player)) {
                player.sendMessage(CC.translate("&cDebes esperar 2 minutos antes de usar esta opción otra vez."));
                return;
            }
            setCooldown(player);
            player.sendMessage ( CC.translate ( "&aEscriba la cantidad a depositar" ) );
            PlayerChatEvent.waitForDeposit(player.getName());
        } ) );
        ItemStack retirar = new ItemStack ( 4444 );
        ItemMeta retirarMeta = retirar.getItemMeta ( );
        retirarMeta.setDisplayName ( CC.translate ( "&aRetirar" ) );
        retirar.setItemMeta ( retirarMeta );
        inventoryContents.set ( 2, 4, ClickableItem.of ( retirar, e -> {
            if (isOnCooldown(player)) {
                player.sendMessage(CC.translate("&cDebes esperar antes de usar esta opción nuevamente. Tiempo de espera: 2 minutos."));
                return;
            }
            setCooldown(player);
            player.sendMessage ( CC.translate ( "&aEscriba la cantidad a retirar" ) );
            PlayerChatEvent.waitForWithdraw(player.getName());
        } ) );
        ItemStack subirNivel = new ItemStack ( 4427 );
        ItemMeta subirNivelMeta = subirNivel.getItemMeta ( );
        subirNivelMeta.setDisplayName ( CC.translate ( "&6Subir de nivel" ) );
        subirNivel.setItemMeta ( subirNivelMeta );
        inventoryContents.set ( 2, 6, ClickableItem.of ( subirNivel, e -> {
            SmartInventory lvlear = SmartInventory.builder ( ).title ( CC.translate ( "&c&lSubir de Nivel" ) )
                    .size ( 3,9 )
                    .id ( "bank_lvl" )
                    .provider ( new InventoryProvider ( ) {
                        @Override
                        public void init ( Player player, InventoryContents inventoryContents ) {
                            inventoryContents.fillBorders(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData())));
                            addBankUpgradeButton(inventoryContents, player, 1, 2, 1, 4444);
                            addBankUpgradeButton(inventoryContents, player, 1, 3, 2, 4444);
                            addBankUpgradeButton(inventoryContents, player, 1, 4, 3, 4444);
                            addBankUpgradeButton(inventoryContents, player, 1, 5, 4, 4444);
                            addBankUpgradeButton(inventoryContents, player, 1, 6, 5, 4444);
                        }

                        @Override
                        public void update ( Player player, InventoryContents inventoryContents ) {
                            inventoryContents.fillBorders ( ClickableItem.empty (  new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) )  ) );

                        }
                    } )
                    .build ();
            lvlear.open ( player );
        } ) );

    }
    private void addBankUpgradeButton(InventoryContents inventoryContents, Player player, int slotRow, int slotCol, int targetLevel, int itemId) {
        ItemStack item = new ItemStack(itemId);
        ItemMeta meta = item.getItemMeta();
        DecimalFormat formatter = new DecimalFormat ("#,###");
        item.setAmount ( targetLevel );
        long levelLimit = BankManager.levelLimits.get(targetLevel);
        long cost = (long) (levelLimit * 0.35);

        meta.setDisplayName(CC.translate("&bNivel " + targetLevel));
        meta.setLore(Arrays.asList(
                CC.translate("&7Mejora tu banco al nivel &a" + targetLevel),
                CC.translate("&7Capacidad máxima: &f" + formatter.format(levelLimit) + " TPS"),
                CC.translate("&7Costo de mejora: &c" + formatter.format(cost) + " TPS")
        ));


        item.setItemMeta(meta);

        inventoryContents.set(slotRow, slotCol, ClickableItem.of(item, e -> {
            String playerName = player.getName();
            IDBCPlayer dbcPlayer = NpcAPI.Instance().getPlayer(playerName).getDBCPlayer();
            int currentLevel = BankManager.getBankLevel(playerName);

            if (currentLevel >= targetLevel) {
                player.sendMessage(CC.translate("&eYa has desbloqueado el &aNivel " + targetLevel + "&e de tu banco."));
                player.playSound(player.getLocation(), Sound.NOTE_BASS, 1f, 0.8f);
                return;
            }

            if (dbcPlayer.getTP() < cost) {
                player.sendMessage(CC.translate("&cNo tienes suficientes TPs para mejorar al &aNivel " + targetLevel + "&c. Se requieren &f" + cost + " TPS."));
                player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1f, 1f);
                return;
            }

            dbcPlayer.setTP(dbcPlayer.getTP() - (int) cost);
            BankManager.setBankLevel(playerName, targetLevel);
            player.sendMessage(CC.translate("&a✔ Banco mejorado con éxito. &7Tu cuenta ha alcanzado el &aNivel " + targetLevel + "&7."));
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f, 1f);
        }));
    }


    @Override
    public void update ( Player player, InventoryContents inventoryContents ) {

    }
    public static SmartInventory getInventory() {
        return SmartInventory.builder()
                .id("banco")
                .provider(new BankInventory ())
                .size(5, 9)
                .title("§2☼ Banco de TPS ☼")
                .build();
    }
}
