package org.delaware.commands;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MenuCommand extends BaseCommand {
    @Command(name = "menu", aliases = {"menu"})
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        SmartInventory INVENTORY = SmartInventory.builder ( )
                .size ( 6, 9 )
                .id ( "zenkai" )
                .type ( InventoryType.CHEST )
                .title ( CC.translate ( "&bDragon Block Zenkai" ) )
                .provider ( new InventoryProvider ( ) {
                    @Override
                    public void init ( Player player, InventoryContents inventoryContents ) {
                        ArrayList<String> loreTraining = new ArrayList<> ( );
                        loreTraining.add ( CC.translate ( "&c¡Clic derecho para abrir!" ) );
                        ItemStack trainings = new ItemStack ( 4960, 1 );
                        ItemMeta trainingMeta = trainings.getItemMeta ( );
                        trainingMeta.setLore ( loreTraining );
                        trainingMeta.setDisplayName ( CC.translate ( "&4&lSistema de Progresion" ) );
                        trainings.setItemMeta ( trainingMeta );
                        inventoryContents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                        inventoryContents.set ( 1, 1, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                        inventoryContents.set ( 4, 1, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                        inventoryContents.set ( 1, 7, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                        inventoryContents.set ( 4, 7, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                        inventoryContents.set ( 2, 1, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 2, 3, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 2, 5, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 2, 7, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 1, 2, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 1, 3, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 1, 4, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 1, 5, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 1, 6, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 3, 1, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 3, 2, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 3, 4, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 3, 6, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 3, 7, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 4, 2, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 4, 3, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 4, 4, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 4, 5, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 4, 6, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );

                        inventoryContents.set ( 2, 2, ClickableItem.of ( trainings, e -> {
                            SmartInventory trainingMenu = SmartInventory.builder ( )
                                    .size ( 6, 9 )
                                    .id ( "training_principal" )
                                    .type ( InventoryType.CHEST )
                                    .title ( CC.translate ( "&6&lOpciones de Entrenamiento" ) )
                                    .provider ( new InventoryProvider ( ) {
                                        @Override
                                        public void init ( Player player, InventoryContents inventoryContents ) {


                                            ItemStack weight = new ItemStack ( 6196 );
                                            ItemMeta weightMeta = weight.getItemMeta ( );
                                            weightMeta.setDisplayName ( CC.translate ( "&c&lTrainings" ) );
                                            weightMeta.setLore ( Arrays.asList (
                                                    CC.translate ( "&7Zona de entrenamiento especial con NPCs." ),
                                                    CC.translate ( "&7Al derrotarlos, obtendrás &eTPs&7 como recompensa." ),
                                                    "",
                                                    CC.translate ( "&a⚡ La forma más fácil y común de entrenar." ),
                                                    CC.translate ( "&8Perfecto para mejorar tu poder rápidamente." )
                                            ) );
                                            weight.setItemMeta ( weightMeta );
                                            weight.setItemMeta ( weightMeta );
                                            inventoryContents.set ( 1, 2, ClickableItem.of ( weight, event -> {
                                                // Asume que este código está dentro de un ClickableItem.of(...) que abre el menú.

                                                SmartInventory smallChestMenu = SmartInventory.builder ( )
                                                        .size ( 3, 9 ) // 3 filas, 9 columnas
                                                        .id ( "small_chest_menu" )
                                                        .type ( InventoryType.CHEST )
                                                        .title ( CC.translate ( "&4&lOpciones de Habilidad" ) )
                                                        .provider ( new InventoryProvider ( ) {
                                                            @Override
                                                            public void init ( Player player, InventoryContents inventoryContents ) {

                                                                ItemStack bloodline = new ItemStack ( 176 );
                                                                ItemMeta bloodlineMeta = bloodline.getItemMeta ( );
                                                                bloodlineMeta.setDisplayName ( CC.translate ( "&cGuerrero" ) );
                                                                bloodline.setItemMeta ( bloodlineMeta );
                                                                inventoryContents.set ( 1, 3, ClickableItem.of ( bloodline, event -> {

                                                                    SmartInventory abilitiesMenu = SmartInventory.builder ( )
                                                                            .size ( 6, 9 )
                                                                            .id ( "trainingwarriors" )
                                                                            .type ( InventoryType.CHEST )
                                                                            .title ( CC.translate ( "&4&lTrainings Warriors" ) )
                                                                            .provider ( new InventoryProvider ( ) {
                                                                                @Override
                                                                                public void init ( Player player, InventoryContents inventoryContents ) {

                                                                                    ItemStack item1 = new ItemStack ( 4977 );
                                                                                    ItemMeta meta1 = item1.getItemMeta ( );
                                                                                    meta1.setDisplayName ( CC.translate ( "&e&lTraining 1" ) );
                                                                                    item1.setItemMeta ( meta1 );

                                                                                    inventoryContents.set ( 1, 2, ClickableItem.of ( item1, event -> {
                                                                                        player.performCommand ( "warp t1" );
                                                                                    } ) );

                                                                                    ItemStack item2 = new ItemStack ( 4975 );
                                                                                    ItemMeta meta2 = item2.getItemMeta ( );
                                                                                    meta2.setDisplayName ( CC.translate ( "&e&lTraining 2" ) );
                                                                                    item2.setItemMeta ( meta2 );

                                                                                    inventoryContents.set ( 1, 6, ClickableItem.of ( item2, event -> {
                                                                                        player.performCommand ( "warp t2" );
                                                                                    } ) );

                                                                                    ItemStack item3 = new ItemStack ( 4973 );
                                                                                    ItemMeta meta3 = item3.getItemMeta ( );
                                                                                    meta3.setDisplayName ( CC.translate ( "&e&lTraining 3" ) );
                                                                                    item3.setItemMeta ( meta3 );

                                                                                    inventoryContents.set ( 2, 4, ClickableItem.of ( item3, event -> {
                                                                                        player.performCommand ( "warp t3" );
                                                                                    } ) );

                                                                                    ItemStack item4 = new ItemStack ( 6171 );
                                                                                    ItemMeta meta4 = item4.getItemMeta ( );
                                                                                    meta4.setDisplayName ( CC.translate ( "&e&lTraining 4" ) );
                                                                                    item4.setItemMeta ( meta4 );

                                                                                    inventoryContents.set ( 3, 2, ClickableItem.of ( item4, event -> {
                                                                                        player.performCommand ( "warp t4" );
                                                                                    } ) );

                                                                                    ItemStack item5 = new ItemStack ( Material.BEDROCK );
                                                                                    ItemMeta meta5 = item5.getItemMeta ( );
                                                                                    meta5.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    item5.setItemMeta ( meta5 );

                                                                                    inventoryContents.set ( 3, 6, ClickableItem.of ( item5, event -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente Training 5..." ) );
                                                                                    } ) );

                                                                                    ItemStack back = new ItemStack ( Material.REDSTONE );
                                                                                    ItemMeta backMeta = back.getItemMeta ( );
                                                                                    backMeta.setDisplayName ( CC.translate ( "&c&l<- Volver" ) );
                                                                                    back.setItemMeta ( backMeta );
                                                                                    inventoryContents.set ( 5, 0, ClickableItem.of ( back, event -> {
                                                                                        player.closeInventory ( );
                                                                                    } ) );

                                                                                    ItemStack confirm = new ItemStack ( 4460 );
                                                                                    ItemMeta confirmMeta = confirm.getItemMeta ( );
                                                                                    confirmMeta.setDisplayName ( CC.translate ( "&a&lConfirmar" ) );
                                                                                    confirm.setItemMeta ( confirmMeta );
                                                                                    inventoryContents.set ( 5, 8, ClickableItem.of ( confirm, event -> {
                                                                                        player.sendMessage ( CC.translate ( "&aAcción confirmada." ) );
                                                                                    } ) );

                                                                                    ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData ( ) );
                                                                                    ItemMeta panelMeta = panel.getItemMeta ( );
                                                                                    panelMeta.setDisplayName ( " " );
                                                                                    panel.setItemMeta ( panelMeta );

                                                                                    ClickableItem redPane = ClickableItem.empty ( panel );

                                                                                    for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                                                        for (int col = 0; col < 9; col++) {
                                                                                            if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                                                                inventoryContents.set ( row, col, redPane );
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void update ( Player player, InventoryContents inventoryContents ) {
                                                                                    // Estático, no necesita update.
                                                                                }
                                                                            } ).build ( );

                                                                    abilitiesMenu.open ( player );
                                                                } ) );

                                                                ItemStack skill = new ItemStack ( 4142 );
                                                                ItemMeta skillMeta = skill.getItemMeta ( );
                                                                skillMeta.setDisplayName ( CC.translate ( "&bEspiritualistas" ) );
                                                                skill.setItemMeta ( skillMeta );
                                                                inventoryContents.set ( 1, 5, ClickableItem.of ( skill, event -> {
                                                                    SmartInventory abilitiesMenu = SmartInventory.builder ( )
                                                                            .size ( 6, 9 ) // 6 filas, 9 columnas
                                                                            .id ( "tespiritualist" )
                                                                            .type ( InventoryType.CHEST )
                                                                            .title ( CC.translate ( "&4&lTrainings Espiritualistas" ) )
                                                                            .provider ( new InventoryProvider ( ) {
                                                                                @Override
                                                                                public void init ( Player player, InventoryContents inventoryContents ) {

                                                                                    ItemStack item1 = new ItemStack ( 4977 );
                                                                                    ItemMeta meta1 = item1.getItemMeta ( );
                                                                                    meta1.setDisplayName ( CC.translate ( "&e&lTraining 1" ) );
                                                                                    item1.setItemMeta ( meta1 );

                                                                                    inventoryContents.set ( 1, 2, ClickableItem.of ( item1, event -> {
                                                                                        player.performCommand ( "warp t1e" );
                                                                                    } ) );

                                                                                    ItemStack item2 = new ItemStack ( 4975 );
                                                                                    ItemMeta meta2 = item2.getItemMeta ( );
                                                                                    meta2.setDisplayName ( CC.translate ( "&e&lTraining 2" ) );
                                                                                    item2.setItemMeta ( meta2 );

                                                                                    inventoryContents.set ( 1, 6, ClickableItem.of ( item2, event -> {
                                                                                        player.performCommand ( "warp t2e" );
                                                                                    } ) );

                                                                                    ItemStack item3 = new ItemStack ( 4973 );
                                                                                    ItemMeta meta3 = item3.getItemMeta ( );
                                                                                    meta3.setDisplayName ( CC.translate ( "&e&lTraining 3" ) );
                                                                                    item3.setItemMeta ( meta3 );

                                                                                    inventoryContents.set ( 2, 4, ClickableItem.of ( item3, event -> {
                                                                                        player.performCommand ( "warp t3e" );
                                                                                    } ) );

                                                                                    ItemStack item4 = new ItemStack ( 6171 );
                                                                                    ItemMeta meta4 = item4.getItemMeta ( );
                                                                                    meta4.setDisplayName ( CC.translate ( "&e&lTraining 4" ) );
                                                                                    item4.setItemMeta ( meta4 );

                                                                                    inventoryContents.set ( 3, 2, ClickableItem.of ( item4, event -> {
                                                                                        player.performCommand ( "warp t4e" );
                                                                                    } ) );

                                                                                    ItemStack item5 = new ItemStack ( Material.BEDROCK );
                                                                                    ItemMeta meta5 = item5.getItemMeta ( );
                                                                                    meta5.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    item5.setItemMeta ( meta5 );

                                                                                    inventoryContents.set ( 3, 6, ClickableItem.of ( item5, event -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente Training 5..." ) );
                                                                                    } ) );

                                                                                    ItemStack back = new ItemStack ( Material.REDSTONE );
                                                                                    ItemMeta backMeta = back.getItemMeta ( );
                                                                                    backMeta.setDisplayName ( CC.translate ( "&c&l<- Volver" ) );
                                                                                    back.setItemMeta ( backMeta );
                                                                                    inventoryContents.set ( 5, 0, ClickableItem.of ( back, event -> {
                                                                                        player.closeInventory ( );
                                                                                    } ) );

                                                                                    ItemStack confirm = new ItemStack ( 4460 );
                                                                                    ItemMeta confirmMeta = confirm.getItemMeta ( );
                                                                                    confirmMeta.setDisplayName ( CC.translate ( "&a&lConfirmar" ) );
                                                                                    confirm.setItemMeta ( confirmMeta );
                                                                                    inventoryContents.set ( 5, 8, ClickableItem.of ( confirm, event -> {
                                                                                        player.sendMessage ( CC.translate ( "&aAcción confirmada." ) );
                                                                                    } ) );

                                                                                    ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData ( ) );
                                                                                    ItemMeta panelMeta = panel.getItemMeta ( );
                                                                                    panelMeta.setDisplayName ( " " );
                                                                                    panel.setItemMeta ( panelMeta );

                                                                                    ClickableItem redPane = ClickableItem.empty ( panel );

                                                                                    for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                                                        for (int col = 0; col < 9; col++) {
                                                                                            if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                                                                inventoryContents.set ( row, col, redPane );
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void update ( Player player, InventoryContents inventoryContents ) {
                                                                                    // Estático, no necesita update.
                                                                                }
                                                                            } ).build ( );

                                                                    abilitiesMenu.open ( player );
                                                                } ) );

                                                                ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData ( ) );
                                                                ItemMeta panelMeta = panel.getItemMeta ( );
                                                                panelMeta.setDisplayName ( " " );
                                                                panel.setItemMeta ( panelMeta );

                                                                ClickableItem redPane = ClickableItem.empty ( panel );

                                                                for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                                    for (int col = 0; col < 9; col++) {
                                                                        if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                                            inventoryContents.set ( row, col, redPane );
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void update ( Player player, InventoryContents inventoryContents ) {
                                                                // Estático, no necesita update.
                                                            }
                                                        } ).build ( );

                                                smallChestMenu.open ( player );
                                            } ) );

                                            ItemStack card = new ItemStack ( 6191 );
                                            ItemMeta cardMeta = card.getItemMeta ( );
                                            cardMeta.setDisplayName ( CC.translate ( "&4&lRaids" ) );
                                            cardMeta.setLore ( Arrays.asList (
                                                    CC.translate ( "&7Enfrenta &coladas de enemigos &7y &cjefes poderosos&7." ),
                                                    CC.translate ( "&7Obtén una &emayor cantidad de TPs &7como recompensa." ),
                                                    "",
                                                    CC.translate ( "&aPuedes luchar solo o junto a tus amigos." ),
                                                    CC.translate ( "&8Requiere un mayor grado de dificultad." )
                                            ) );
                                            card.setItemMeta ( cardMeta );

                                            inventoryContents.set ( 1, 4, ClickableItem.of ( card, event -> {

                                                SmartInventory sagasRaidMenu = SmartInventory.builder ( )
                                                        .size ( 6, 9 ) // 6 filas, 9 columnas
                                                        .id ( "sagas_raid_menu" )
                                                        .type ( InventoryType.CHEST )
                                                        .title ( CC.translate ( "&4&lSistema de Raids" ) )
                                                        .provider ( new InventoryProvider ( ) {
                                                            @Override
                                                            public void init ( Player player, InventoryContents inventoryContents ) {

                                                                ItemStack raid1 = new ItemStack ( 6192 );
                                                                ItemMeta meta1 = raid1.getItemMeta ( );
                                                                meta1.setDisplayName ( CC.translate ( "&4&lAsalto a la Red Ribborn" ) );
                                                                raid1.setItemMeta ( meta1 );
                                                                inventoryContents.set ( 2, 1, ClickableItem.of ( raid1, event -> {
                                                                    player.performCommand ( "warp raidrr" );
                                                                } ) );

                                                                // 2. Invasión Saiyajin (6206) - Posición (1, 4)
                                                                ItemStack raid2 = new ItemStack ( 6206 );
                                                                ItemMeta meta2 = raid2.getItemMeta ( );
                                                                meta2.setDisplayName ( CC.translate ( "&4&lInvasión Saiyajin" ) );
                                                                raid2.setItemMeta ( meta2 );
                                                                inventoryContents.set ( 1, 2, ClickableItem.of ( raid2, event -> {
                                                                    player.performCommand ( "warp raidt1npc" );
                                                                } ) );

                                                                // 3. Invasión de Coolers (6219) - Posición (1, 6)
                                                                ItemStack raid3 = new ItemStack ( 6219 );
                                                                ItemMeta meta3 = raid3.getItemMeta ( );
                                                                meta3.setDisplayName ( CC.translate ( "&4&lInvasión de Coolers" ) );
                                                                raid3.setItemMeta ( meta3 );
                                                                inventoryContents.set ( 2, 3, ClickableItem.of ( raid3, event -> {
                                                                    player.performCommand ( "warp raidt2npc" );
                                                                } ) );

                                                                // 4. ¡El legendario Super Saiyajin! (6178) - Posición (1, 4)
                                                                ItemStack raid4 = new ItemStack ( 6178 );
                                                                ItemMeta meta4 = raid4.getItemMeta ( );
                                                                meta4.setDisplayName ( CC.translate ( "&4&l¡El legendario Super Saiyajin!" ) );
                                                                raid4.setItemMeta ( meta4 );
                                                                inventoryContents.set ( 1, 4, ClickableItem.of ( raid4, event -> {
                                                                    player.performCommand ( "warp raidt3npc" );
                                                                } ) );

                                                                // 5. ¡La danza Metamoru! (6194) - Posición (3, 5)
                                                                ItemStack raid5 = new ItemStack ( 6194 );
                                                                ItemMeta meta5 = raid5.getItemMeta ( );
                                                                meta5.setDisplayName ( CC.translate ( "&4&l¡La danza Metamoru!" ) );
                                                                raid5.setItemMeta ( meta5 );
                                                                inventoryContents.set ( 2, 5, ClickableItem.of ( raid5, event -> {
                                                                    player.performCommand ( "warp raidmetamoru" );
                                                                } ) );

                                                                // 6. ¡¡Yoshaa!! (6203) - Posición (3, 6)
                                                                ItemStack raid6 = new ItemStack ( 6203 );
                                                                ItemMeta meta6 = raid6.getItemMeta ( );
                                                                meta6.setDisplayName ( CC.translate ( "&4&l¡¡Yoshaa!!" ) );
                                                                raid6.setItemMeta ( meta6 );
                                                                inventoryContents.set ( 1, 6, ClickableItem.of ( raid6, event -> {
                                                                    player.performCommand ( "warp raidvegeto" );
                                                                } ) );

                                                                ItemStack raid7 = new ItemStack ( 6192 ); // Reutilizamos ID, si tienes un ID diferente para SSJ4, cámbialo.
                                                                ItemMeta meta7 = raid7.getItemMeta ( );
                                                                meta7.setDisplayName ( CC.translate ( "&4&lRaid SSJ4" ) );
                                                                raid7.setItemMeta ( meta7 );
                                                                inventoryContents.set ( 2, 7, ClickableItem.of ( raid7, event -> {
                                                                    player.performCommand ( "warp raidssj4" );
                                                                } ) );
                                                                
                                                                ItemStack bedrock = new ItemStack ( Material.BEDROCK, 1 );
                                                                ItemMeta bedrockMeta = bedrock.getItemMeta ( );
                                                                bedrockMeta.setDisplayName ( " " ); // Ocultar nombre
                                                                bedrock.setItemMeta ( bedrockMeta );
                                                                ClickableItem bedrockPane = ClickableItem.empty ( bedrock );

                                                                int[][] bedrockPositions = {
                                                                        {3, 2}, {3, 4}, {3, 6},
                                                                        {4, 1}, {4, 3}, {4, 5}, {4, 7}
                                                                };

                                                                for (int[] pos : bedrockPositions) {
                                                                    inventoryContents.set ( pos[0], pos[1], bedrockPane );
                                                                }

                                                                ItemStack redPanel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData ( ) );
                                                                ItemMeta panelMeta = redPanel.getItemMeta ( );
                                                                panelMeta.setDisplayName ( " " );
                                                                redPanel.setItemMeta ( panelMeta );

                                                                ClickableItem redPane = ClickableItem.empty ( redPanel );

                                                                for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                                    for (int col = 0; col < 9; col++) {
                                                                        // Rellena con el panel rojo SOLO si la posición está vacía
                                                                        if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                                            inventoryContents.set ( row, col, redPane );
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void update ( Player player, InventoryContents inventoryContents ) {
                                                                // Estático, no necesita update.
                                                            }
                                                        } ).build ( );

                                                sagasRaidMenu.open ( player );
                                            } ) );

                                            ItemStack chamber = new ItemStack ( 6215 );
                                            ItemMeta chamberMeta = chamber.getItemMeta ( );
                                            chamberMeta.setDisplayName ( CC.translate ( "&7&lSagas" ) );
                                            chamberMeta.setLore ( Arrays.asList (
                                                    CC.translate ( "&7Gana &eTPs &7completando el &6modo historia &7de Dragon Ball." ),
                                                    CC.translate ( "&7Revive &emomentos clásicos &7de tu infancia en esta aventura." )
                                            ) );
                                            chamber.setItemMeta ( chamberMeta );

                                            inventoryContents.set ( 1, 6, ClickableItem.of ( chamber, event -> {

                                                SmartInventory sagasMenu = SmartInventory.builder ( )
                                                        .size ( 6, 9 )
                                                        .id ( "sagas_menu_v2" )
                                                        .type ( InventoryType.CHEST )
                                                        .title ( CC.translate ( "&4&lMenú de Sagas" ) )
                                                        .provider ( new InventoryProvider ( ) {
                                                            @Override
                                                            public void init ( Player player, InventoryContents inventoryContents ) {

                                                                ItemStack item1 = new ItemStack ( 4977 );
                                                                ItemMeta meta1 = item1.getItemMeta ( );
                                                                meta1.setDisplayName ( CC.translate ( "&e&lSaga Saiyan" ) );
                                                                item1.setItemMeta ( meta1 );
                                                                inventoryContents.set ( 1, 2, ClickableItem.of ( item1, event -> {
                                                                    player.performCommand ( "warp sagasaiyan" );
                                                                } ) );

                                                                ItemStack item2 = new ItemStack ( 4975 );
                                                                ItemMeta meta2 = item2.getItemMeta ( );
                                                                meta2.setDisplayName ( CC.translate ( "&b&lSaga Namek" ) );
                                                                item2.setItemMeta ( meta2 );
                                                                inventoryContents.set ( 3, 2, ClickableItem.of ( item2, event -> {
                                                                    player.performCommand ( "warp saganamek" );
                                                                } ) );

                                                                ItemStack item3 = new ItemStack ( 4973 );
                                                                ItemMeta meta3 = item3.getItemMeta ( );
                                                                meta3.setDisplayName ( CC.translate ( "&2&lSaga de Cell" ) );
                                                                item3.setItemMeta ( meta3 );
                                                                inventoryContents.set ( 2, 3, ClickableItem.of ( item3, event -> {
                                                                    player.performCommand ( "warp sagacell" );
                                                                } ) );

                                                                ItemStack item4 = new ItemStack ( 4960 );
                                                                ItemMeta meta4 = item4.getItemMeta ( );
                                                                meta4.setDisplayName ( CC.translate ( "&d&lSaga Buu" ) );
                                                                item4.setItemMeta ( meta4 );
                                                                inventoryContents.set ( 1, 4, ClickableItem.of ( item4, event -> {
                                                                    player.performCommand ( "warp sagabuu" );
                                                                } ) );


                                                                ItemStack back = new ItemStack ( Material.REDSTONE );
                                                                ItemMeta backMeta = back.getItemMeta ( );
                                                                backMeta.setDisplayName ( CC.translate ( "&c&l<- Volver" ) );
                                                                back.setItemMeta ( backMeta );
                                                                inventoryContents.set ( 5, 0, ClickableItem.of ( back, event -> {
                                                                    player.closeInventory ( );
                                                                } ) );

                                                                ItemStack confirm = new ItemStack ( 4460 );
                                                                ItemMeta confirmMeta = confirm.getItemMeta ( );
                                                                confirmMeta.setDisplayName ( CC.translate ( "&a&lContinuar" ) );
                                                                confirm.setItemMeta ( confirmMeta );
                                                                inventoryContents.set ( 5, 8, ClickableItem.of ( confirm, event -> {
                                                                    player.sendMessage ( CC.translate ( "&aContinuando a la siguiente sección..." ) );
                                                                } ) );


                                                                ItemStack bedrock = new ItemStack ( Material.BEDROCK, 1 );
                                                                ItemMeta bedrockMeta = bedrock.getItemMeta ( );
                                                                bedrockMeta.setDisplayName ( " " );
                                                                bedrock.setItemMeta ( bedrockMeta );
                                                                ClickableItem bedrockPane = ClickableItem.empty ( bedrock );

                                                                inventoryContents.set ( 1, 6, bedrockPane );
                                                                inventoryContents.set ( 3, 4, bedrockPane );
                                                                inventoryContents.set ( 3, 6, bedrockPane );


                                                                ItemStack redPanel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData ( ) );
                                                                ItemMeta panelMeta = redPanel.getItemMeta ( );
                                                                panelMeta.setDisplayName ( " " );
                                                                redPanel.setItemMeta ( panelMeta );

                                                                ClickableItem redPane = ClickableItem.empty ( redPanel );

                                                                for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                                    for (int col = 0; col < 9; col++) {
                                                                        if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                                            inventoryContents.set ( row, col, redPane );
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void update ( Player player, InventoryContents inventoryContents ) {

                                                            }
                                                        } ).build ( );

                                                sagasMenu.open ( player );
                                            } ) );

                                            ItemStack pickaxe = new ItemStack ( 278 );
                                            ItemMeta pickaxeMeta = pickaxe.getItemMeta ( );
                                            pickaxeMeta.setDisplayName ( CC.translate ( "&2&lFactions" ) );
                                            pickaxeMeta.setLore ( Arrays.asList (
                                                    CC.translate ( "&7¿Te gusta más la &aacción &7y pelear por &erecursos&7?" ),
                                                    CC.translate ( "&7Gana &ex2 TPs &7que en los trainings normales." ),
                                                    "",
                                                    CC.translate ( "&7Domina &azonas de farming&7, desafía a otros jugadores" ),
                                                    CC.translate ( "&7y demuestra &equién manda &7en el campo de batalla." )
                                            ) );
                                            pickaxe.setItemMeta ( pickaxeMeta );
                                            inventoryContents.set ( 3, 4, ClickableItem.of ( pickaxe, event -> {
                                                player.performCommand ( "warp mine" );
                                                player.sendMessage ( CC.translate ( "&cProximamente" ) );
                                            } ) );


                                            ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) );
                                            ItemMeta panelMeta = panel.getItemMeta ( );
                                            panelMeta.setDisplayName ( " " );
                                            panel.setItemMeta ( panelMeta );

                                            ClickableItem yellowPane = ClickableItem.empty ( panel );

                                            for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                for (int col = 0; col < 9; col++) {
                                                    if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                        inventoryContents.set ( row, col, yellowPane );
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void update ( Player player, InventoryContents inventoryContents ) {
                                            // Dejamos vacío ya que este menú es estático.
                                        }
                                    } ).build ( );

                            trainingMenu.open ( player );
                        } ) );
                        ItemStack zonas = new ItemStack ( 2, 1 );
                        ItemMeta zonasMeta = zonas.getItemMeta ( );
                        zonasMeta.setLore ( loreTraining );
                        zonasMeta.setDisplayName ( CC.translate ( "&6&lZonas del Servidor" ) );
                        zonas.setItemMeta ( zonasMeta );
                        inventoryContents.set ( 3, 3, ClickableItem.of ( zonas, e -> {
                            SmartInventory dbcInventory = SmartInventory.builder ( ).size ( 6, 9 )
                                    .type ( InventoryType.CHEST )
                                    .title ( CC.translate ( "&6&lZonas del Servidor" ) )
                                    .provider ( new InventoryProvider ( ) {
                                        @Override
                                        public void init ( Player player, InventoryContents inventoryContents ) {
                                            ItemStack humanos = new ItemStack ( 399 );
                                            ItemMeta humanosMeta = humanos.getItemMeta ( );
                                            humanosMeta.setDisplayName ( CC.translate ( "&b&lSpawn" ) );
                                            humanos.setItemMeta ( humanosMeta );
                                            inventoryContents.set ( 1, 1, ClickableItem.of ( humanos, ( event ) -> {
                                                player.performCommand ( "warp spawn" );
                                            } ) );

                                            ItemStack arcosianos = new ItemStack ( 6217 );
                                            ItemMeta arcosianosMeta = arcosianos.getItemMeta ( );
                                            arcosianosMeta.setDisplayName ( CC.translate ( "&3&lComida" ) );
                                            arcosianos.setItemMeta ( arcosianosMeta );
                                            inventoryContents.set ( 1, 3, ClickableItem.of ( arcosianos, ( event ) -> {
                                                player.performCommand ( "warp comida" );
                                            } ) );

                                            ItemStack saiyans = new ItemStack ( 6184 );
                                            ItemMeta saiyansMeta = saiyans.getItemMeta ( );
                                            saiyansMeta.setDisplayName ( CC.translate ( "&c&lTutorial" ) );
                                            saiyans.setItemMeta ( saiyansMeta );
                                            inventoryContents.set ( 3, 1, ClickableItem.of ( saiyans, ( event ) -> {
                                                player.performCommand ( "warp tutorial" );
                                            } ) );

                                            ItemStack Majin = new ItemStack ( 6208 );
                                            ItemMeta MajinMeta = Majin.getItemMeta ( );
                                            MajinMeta.setDisplayName ( CC.translate ( "&e&lPvP" ) );
                                            Majin.setItemMeta ( MajinMeta );
                                            inventoryContents.set ( 3, 3, ClickableItem.of ( Majin, ( event ) -> {
                                                player.performCommand ( "warp torneo" );
                                            } ) );

                                            ItemStack Namekianos = new ItemStack ( 6124 );
                                            ItemMeta NamekianosMeta = Namekianos.getItemMeta ( );
                                            NamekianosMeta.setDisplayName ( CC.translate ( "&4&lHalloween" ) );
                                            Namekianos.setItemMeta ( NamekianosMeta );
                                            inventoryContents.set ( 2, 5, ClickableItem.of ( Namekianos, ( event ) -> {
                                                player.performCommand ( "warp halloween" );
                                            } ) );

                                            ItemStack Kaioken = new ItemStack ( 6200 );
                                            ItemMeta KaiokenMeta = Kaioken.getItemMeta ( );
                                            KaiokenMeta.setDisplayName ( CC.translate ( "&cZona del &bFuturo" ) );
                                            Kaioken.setItemMeta ( KaiokenMeta );
                                            inventoryContents.set ( 1, 7, ClickableItem.of ( Kaioken, ( event ) -> {
                                                player.performCommand ( "warp futuro" );

                                            } ) );

                                            ItemStack Mistico = new ItemStack ( 6229 );
                                            ItemMeta MisticoMeta = Mistico.getItemMeta ( );
                                            MisticoMeta.setDisplayName ( CC.translate ( "&c&lOtherWorld" ) );
                                            Mistico.setItemMeta ( MisticoMeta );
                                            inventoryContents.set ( 3, 7, ClickableItem.of ( Mistico, ( event ) -> {
                                                player.performCommand ( "warp enma" );

                                            } ) );

                                            ItemStack Salir = new ItemStack ( 331 );
                                            ItemMeta SalirMeta = Salir.getItemMeta ( );
                                            SalirMeta.setDisplayName ( CC.translate ( "&4Salir" ) );
                                            Salir.setItemMeta ( SalirMeta );
                                            inventoryContents.set ( 5, 0, ClickableItem.of ( Salir, ( event ) -> {
                                                player.closeInventory ( );
                                            } ) );

                                            ItemStack siguientePagina = new ItemStack ( 4460 );
                                            ItemMeta siguientePaginaMeta = siguientePagina.getItemMeta ( );
                                            siguientePaginaMeta.setDisplayName ( CC.translate ( "&aSiguiente pagina" ) );
                                            siguientePagina.setItemMeta ( siguientePaginaMeta );
                                            inventoryContents.set ( 5, 8, ClickableItem.of ( siguientePagina, ( event ) -> {
                                                SmartInventory nextPage = SmartInventory.builder ( ).title ( CC.translate ( "&eZonas del Servidor" ) )
                                                        .type ( InventoryType.CHEST )
                                                        .size ( 6, 9 )
                                                        .id ( "zonaszenkai" )
                                                        .provider ( new InventoryProvider ( ) {
                                                            @Override
                                                            public void init ( Player player, InventoryContents inventoryContents ) {
                                                                ItemStack factions = new ItemStack ( 2 );
                                                                ItemMeta factionsMeta = factions.getItemMeta ( );
                                                                factionsMeta.setDisplayName ( CC.translate ( "&aFactions" ) );
                                                                factions.setItemMeta ( factionsMeta );
                                                                inventoryContents.set ( 1, 1, ClickableItem.of ( factions, ( event ) -> {
                                                                    player.performCommand ( "warp factions" );

                                                                } ) );


                                                                ItemStack staff = new ItemStack ( 4389 );
                                                                ItemMeta staffMeta = staff.getItemMeta ( );
                                                                staffMeta.setDisplayName ( CC.translate ( "&bStaff" ) );
                                                                staff.setItemMeta ( staffMeta );
                                                                inventoryContents.set ( 1, 3, ClickableItem.of ( staff, ( event ) -> {
                                                                    player.performCommand ( "warp staff" );

                                                                } ) );


                                                                ItemStack sagas = new ItemStack ( 6216 );
                                                                ItemMeta sagasMeta = sagas.getItemMeta ( );
                                                                sagasMeta.setDisplayName ( CC.translate ( "&6Sagas" ) );
                                                                sagas.setItemMeta ( sagasMeta );
                                                                inventoryContents.set ( 1, 5, ClickableItem.of ( sagas, ( event ) -> {
                                                                    player.performCommand ( "warp sagas" );

                                                                } ) );


                                                                ItemStack eventos = new ItemStack ( 6214 );
                                                                ItemMeta eventosMeta = eventos.getItemMeta ( );
                                                                eventosMeta.setDisplayName ( CC.translate ( "&eEventos" ) );
                                                                eventos.setItemMeta ( eventosMeta );
                                                                inventoryContents.set ( 1, 7, ClickableItem.of ( eventos, ( event ) -> {
                                                                    player.performCommand ( "warp eventos" );

                                                                } ) );

                                                                ItemStack Recursos = new ItemStack ( 17 );
                                                                ItemMeta recursosMeta = Recursos.getItemMeta ( );
                                                                recursosMeta.setDisplayName ( CC.translate ( "&eRecursos" ) );
                                                                Recursos.setItemMeta ( recursosMeta );
                                                                inventoryContents.set ( 3, 1, ClickableItem.of ( Recursos, ( event ) -> {
                                                                    player.performCommand ( "warp recursos" );
                                                                } ) );

                                                                ItemStack Reglas = new ItemStack ( 690 );
                                                                ItemMeta ReglasMeta = Reglas.getItemMeta ( );
                                                                ReglasMeta.setDisplayName ( CC.translate ( "&4Reglas" ) );
                                                                Reglas.setItemMeta ( ReglasMeta );
                                                                inventoryContents.set ( 3, 3, ClickableItem.of ( Reglas, ( event ) -> {
                                                                    player.performCommand ( "warp reglas" );
                                                                } ) );

                                                                ItemStack Mercado = new ItemStack ( 6200 );
                                                                ItemMeta MercadoMeta = Mercado.getItemMeta ( );
                                                                MercadoMeta.setDisplayName ( CC.translate ( "&6Mercado" ) );
                                                                Mercado.setItemMeta ( MercadoMeta );
                                                                inventoryContents.set ( 3, 5, ClickableItem.of ( Mercado, ( event ) -> {
                                                                    player.performCommand ( "warp mercado" );
                                                                } ) );

                                                                ItemStack Banco = new ItemStack ( 6080 );
                                                                ItemMeta BancoMeta = Banco.getItemMeta ( );
                                                                BancoMeta.setDisplayName ( CC.translate ( "&8[&fBanco&8]" ) );
                                                                Banco.setItemMeta ( BancoMeta );
                                                                inventoryContents.set ( 3, 7, ClickableItem.of ( Banco, ( event ) -> {
                                                                    player.performCommand ( "warp banco" );
                                                                } ) );

                                                                ItemStack Salir = new ItemStack ( 331 );
                                                                ItemMeta SalirMeta = Salir.getItemMeta ( );
                                                                SalirMeta.setDisplayName ( CC.translate ( "&4Salir" ) );
                                                                Salir.setItemMeta ( SalirMeta );
                                                                inventoryContents.set ( 5, 0, ClickableItem.of ( Salir, ( event ) -> {
                                                                    player.closeInventory ( );
                                                                } ) );

                                                                ItemStack siguientePagina = new ItemStack ( 4460 );
                                                                ItemMeta siguientePaginaMeta = siguientePagina.getItemMeta ( );
                                                                siguientePaginaMeta.setDisplayName ( CC.translate ( "&aSiguiente pagina" ) );
                                                                siguientePagina.setItemMeta ( siguientePaginaMeta );
                                                                inventoryContents.set ( 5, 8, ClickableItem.of ( siguientePagina, ( event ) -> {
                                                                } ) );
                                                                ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData ( ) );
                                                                ItemMeta panelMeta = panel.getItemMeta ( );
                                                                panelMeta.setDisplayName ( " " );
                                                                panel.setItemMeta ( panelMeta );

                                                                for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                                    for (int col = 0; col < 9; col++) {
                                                                        if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                                            inventoryContents.set ( row, col, ClickableItem.empty ( panel ) );
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void update ( Player player, InventoryContents inventoryContents ) {
                                                                ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData ( ) );
                                                                ItemMeta panelMeta = panel.getItemMeta ( );
                                                                panelMeta.setDisplayName ( " " );
                                                                panel.setItemMeta ( panelMeta );

                                                                for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                                    for (int col = 0; col < 9; col++) {
                                                                        if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                                            inventoryContents.set ( row, col, ClickableItem.empty ( panel ) );
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } )
                                                        .build ( );
                                                nextPage.open ( player );
                                            } ) );
                                            ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData ( ) );
                                            ItemMeta panelMeta = panel.getItemMeta ( );
                                            panelMeta.setDisplayName ( " " );
                                            panel.setItemMeta ( panelMeta );

                                            for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                for (int col = 0; col < 9; col++) {
                                                    if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                        inventoryContents.set ( row, col, ClickableItem.empty ( panel ) );
                                                    }
                                                }
                                            }

                                        }

                                        @Override
                                        public void update ( Player player, InventoryContents inventoryContents ) {
                                            ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData ( ) );
                                            ItemMeta panelMeta = panel.getItemMeta ( );
                                            panelMeta.setDisplayName ( " " );
                                            panel.setItemMeta ( panelMeta );
                                            for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                for (int col = 0; col < 9; col++) {
                                                    if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                        inventoryContents.set ( row, col, ClickableItem.empty ( panel ) );
                                                    }
                                                }
                                            }
                                        }
                                    } )
                                    .build ( );
                            dbcInventory.open ( player );
                        } ) );
                        ItemStack dbc = new ItemStack ( 5144, 1 );
                        ItemMeta dbcMeta = dbc.getItemMeta ( );
                        dbcMeta.setLore ( loreTraining );
                        dbcMeta.setDisplayName ( CC.translate ( "&e&lDragon Block C" ) );
                        dbc.setItemMeta ( dbcMeta );
                        inventoryContents.set ( 2, 4, ClickableItem.of ( dbc, e -> {
                            SmartInventory dbcInventory = SmartInventory.builder ( ).size ( 6, 9 )
                                    .type ( InventoryType.CHEST )
                                    .title ( CC.translate ( "&e&lZonas DBC" ) )
                                    .provider ( new InventoryProvider ( ) {
                                        @Override
                                        public void init ( Player player, InventoryContents inventoryContents ) {
                                            ItemStack kaio = new ItemStack ( 6180 );
                                            ItemMeta kaioMeta = kaio.getItemMeta ( );
                                            kaioMeta.setDisplayName ( CC.translate ( "&aKaio" ) );
                                            kaio.setItemMeta ( kaioMeta );
                                            inventoryContents.set ( 1, 1, ClickableItem.of ( kaio, ( event ) -> {
                                                player.performCommand ( "warp kaio" );
                                            } ) );

                                            ItemStack whis = new ItemStack ( 4629 );
                                            ItemMeta whisMeta = whis.getItemMeta ( );
                                            whisMeta.setDisplayName ( CC.translate ( "&bWhis" ) );
                                            whis.setItemMeta ( whisMeta );
                                            inventoryContents.set ( 1, 3, ClickableItem.of ( whis, ( event ) -> {
                                                player.performCommand ( "warp whis" );
                                            } ) );

                                            ItemStack Goku = new ItemStack ( 5936 );
                                            ItemMeta GokuMeta = Goku.getItemMeta ( );
                                            GokuMeta.setDisplayName ( CC.translate ( "&6Goku" ) );
                                            Goku.setItemMeta ( GokuMeta );
                                            inventoryContents.set ( 1, 5, ClickableItem.of ( Goku, ( event ) -> {
                                                player.performCommand ( "warp goku" );
                                            } ) );

                                            ItemStack Freezer = new ItemStack ( 5256 );
                                            ItemMeta FreezerMeta = Freezer.getItemMeta ( );
                                            FreezerMeta.setDisplayName ( CC.translate ( "&5Freezer" ) );
                                            Freezer.setItemMeta ( FreezerMeta );
                                            inventoryContents.set ( 1, 7, ClickableItem.of ( Freezer, ( event ) -> {
                                                player.performCommand ( "warp Freezer" );
                                            } ) );

                                            ItemStack Babidi = new ItemStack ( 4529 );
                                            ItemMeta BabidiMeta = Babidi.getItemMeta ( );
                                            BabidiMeta.setDisplayName ( CC.translate ( "&dBabidi" ) );
                                            Babidi.setItemMeta ( BabidiMeta );
                                            inventoryContents.set ( 3, 1, ClickableItem.of ( Babidi, ( event ) -> {
                                                player.performCommand ( "warp babidi" );
                                            } ) );


                                            ItemStack Kami = new ItemStack ( 4696 );
                                            ItemMeta KamiMeta = Kami.getItemMeta ( );
                                            KamiMeta.setDisplayName ( CC.translate ( "&eKami" ) );
                                            Kami.setItemMeta ( KamiMeta );
                                            inventoryContents.set ( 3, 3, ClickableItem.of ( Kami, ( event ) -> {
                                                player.performCommand ( "warp kami" );
                                            } ) );

                                            ItemStack Roshi = new ItemStack ( 5774 );
                                            ItemMeta RoshiMeta = Roshi.getItemMeta ( );
                                            RoshiMeta.setDisplayName ( CC.translate ( "&dRoshi" ) );
                                            Roshi.setItemMeta ( RoshiMeta );
                                            inventoryContents.set ( 3, 5, ClickableItem.of ( Roshi, ( event ) -> {
                                                player.performCommand ( "warp roshi" );
                                            } ) );

                                            ItemStack Cell = new ItemStack ( 5235 );
                                            ItemMeta CellMeta = Cell.getItemMeta ( );
                                            CellMeta.setDisplayName ( CC.translate ( "&2Cell" ) );
                                            Cell.setItemMeta ( CellMeta );
                                            inventoryContents.set ( 3, 7, ClickableItem.of ( Cell, ( event ) -> {
                                                player.performCommand ( "warp cell" );
                                            } ) );
                                            ItemStack Guru = new ItemStack ( 724 );
                                            ItemMeta GuruMeta = Guru.getItemMeta ( );
                                            GuruMeta.setDisplayName ( CC.translate ( "&2Guru" ) );
                                            Guru.setItemMeta ( GuruMeta );
                                            inventoryContents.set ( 5, 0, ClickableItem.of ( Guru, ( event ) -> {
                                                player.performCommand ( "warp guru" );
                                            } ) );
                                            ItemStack JinRyuu = new ItemStack ( 4748 );
                                            ItemMeta JinryuuMeta = JinRyuu.getItemMeta ( );
                                            JinryuuMeta.setDisplayName ( CC.translate ( "&bJin Ryuu" ) );
                                            JinRyuu.setItemMeta ( JinryuuMeta );
                                            inventoryContents.set ( 5, 8, ClickableItem.of ( JinRyuu, ( event ) -> {
                                                player.performCommand ( "warp jinryuu" );
                                            } ) );
                                            ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData ( ) );
                                            ItemMeta panelMeta = panel.getItemMeta ( );
                                            panelMeta.setDisplayName ( " " );
                                            panel.setItemMeta ( panelMeta );
                                            for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                for (int col = 0; col < 9; col++) {
                                                    if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                        inventoryContents.set ( row, col, ClickableItem.empty ( panel ) );
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void update ( Player player, InventoryContents inventoryContents ) {
                                            ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData ( ) );
                                            ItemMeta panelMeta = panel.getItemMeta ( );
                                            panelMeta.setDisplayName ( " " );
                                            panel.setItemMeta ( panelMeta );
                                            for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                for (int col = 0; col < 9; col++) {
                                                    if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                        inventoryContents.set ( row, col, ClickableItem.empty ( panel ) );
                                                    }
                                                }
                                            }
                                        }
                                    } )
                                    .build ( );
                            dbcInventory.open ( player );
                        } ) );
                        ItemStack Transformaciones = new ItemStack ( 6202, 1 );
                        ItemMeta tiendaMeta = Transformaciones.getItemMeta ( );
                        tiendaMeta.setDisplayName ( CC.translate ( "&7&lTransformaciones" ) );
                        Transformaciones.setItemMeta ( tiendaMeta );
                        ItemMeta transformacionesMeta = Transformaciones.getItemMeta ( );
                        transformacionesMeta.setDisplayName ( CC.translate ( "&7&lTransformaciones" ) );
                        Transformaciones.setItemMeta ( transformacionesMeta );
                        inventoryContents.set ( 2, 6, ClickableItem.of ( Transformaciones, er -> {
                            SmartInventory transformationsInventory = SmartInventory.builder ( )
                                    .size ( 6, 9 )
                                    .id ( "transformations_menu" )
                                    .type ( InventoryType.CHEST )
                                    .title ( CC.translate ( "&7&lTransformaciones" ) )
                                    .provider ( new InventoryProvider ( ) {
                                        @Override
                                        public void init ( Player player, InventoryContents inventoryContents ) {

                                            ItemStack saiyan = new ItemStack ( 6208 ); // ID base, puedes cambiarlo
                                            ItemMeta saiyanMeta = saiyan.getItemMeta ( );
                                            saiyanMeta.setDisplayName ( CC.translate ( "&e&lSaiyan" ) );
                                            saiyan.setItemMeta ( saiyanMeta );
                                            inventoryContents.set ( 1, 4, ClickableItem.of ( saiyan, event -> {
                                                // === INICIO DEL NUEVO MENÚ DE TRANSFORMACIONES SAIYAN ===
                                                SmartInventory saiyanInventory = SmartInventory.builder ( )
                                                        .size ( 6, 9 )
                                                        .id ( "saiyan_transformations" )
                                                        .type ( InventoryType.CHEST )
                                                        .title ( CC.translate ( "&e&lTransformaciones Saiyan" ) )
                                                        .provider ( new InventoryProvider ( ) {
                                                            @Override
                                                            public void init ( Player player, InventoryContents inventoryContents ) {

                                                                ItemStack ssb = new ItemStack ( 6192 );
                                                                ItemMeta ssbMeta = ssb.getItemMeta ( );
                                                                ssbMeta.setDisplayName ( CC.translate ( "&c&lKaioken" ) );
                                                                ssb.setItemMeta ( ssbMeta );
                                                                inventoryContents.set ( 1, 1, ClickableItem.of ( ssb, e -> {
                                                                    player.performCommand ( "warp kaioken" );
                                                                } ) );

                                                                ItemStack ssj1 = new ItemStack ( 6202 );
                                                                ItemMeta ssj1Meta = ssj1.getItemMeta ( );
                                                                ssj1Meta.setDisplayName ( CC.translate ( "&e&lSSJ&c&lF" ) );
                                                                ssj1.setItemMeta ( ssj1Meta );
                                                                inventoryContents.set ( 1, 3, ClickableItem.of ( ssj1, e -> {
                                                                    player.performCommand ( "warp ssjf" );
                                                                } ) );

                                                                ItemStack ssj2 = new ItemStack ( 6202 );
                                                                ItemMeta ssj2Meta = ssj2.getItemMeta ( );
                                                                ssj2Meta.setDisplayName ( CC.translate ( "&e&lSuper Saiyan 1" ) );
                                                                ssj2.setItemMeta ( ssj2Meta );
                                                                inventoryContents.set ( 1, 5, ClickableItem.of ( ssj2, e -> {
                                                                    player.performCommand ( "warp ssj1" );
                                                                } ) );

                                                                ItemStack ssj3 = new ItemStack ( 6202 );
                                                                ItemMeta ssj3Meta = ssj3.getItemMeta ( );
                                                                ssj3Meta.setDisplayName ( CC.translate ( "&e&lSuper Saiyan 2" ) );
                                                                ssj3.setItemMeta ( ssj3Meta );
                                                                inventoryContents.set ( 1, 7, ClickableItem.of ( ssj3, e -> {
                                                                    player.performCommand ( "warp ssj2" );
                                                                } ) );

                                                                ItemStack ssjr = new ItemStack ( 5767 );
                                                                ItemMeta ssjrMeta = ssjr.getItemMeta ( );
                                                                ssjrMeta.setDisplayName ( CC.translate ( "&7&lAndroide 1" ) );
                                                                ssjr.setItemMeta ( ssjrMeta );
                                                                inventoryContents.set ( 3, 1, ClickableItem.of ( ssjr, e -> {
                                                                    player.performCommand ( "warp androide1" );
                                                                } ) );

                                                                ItemStack ssjbe = new ItemStack ( 6202 );
                                                                ItemMeta ssjbeMeta = ssjbe.getItemMeta ( );
                                                                ssjbeMeta.setDisplayName ( CC.translate ( "&e&lSuper Saiyan 3" ) );
                                                                ssjbe.setItemMeta ( ssjbeMeta );
                                                                inventoryContents.set ( 3, 3, ClickableItem.of ( ssjbe, e -> {
                                                                    player.performCommand ( "warp ssj3" );
                                                                } ) );

                                                                ItemStack ssjg = new ItemStack ( 5767 );
                                                                ItemMeta ssjgMeta = ssjg.getItemMeta ( );
                                                                ssjgMeta.setDisplayName ( CC.translate ( "&7&lAndroide 2" ) );
                                                                ssjg.setItemMeta ( ssjgMeta );
                                                                inventoryContents.set ( 3, 5, ClickableItem.of ( ssjg, e -> {
                                                                    player.performCommand ( "warp androide2" );
                                                                } ) );

                                                                ItemStack ui = new ItemStack ( 6210 );
                                                                ItemMeta uiMeta = ui.getItemMeta ( );
                                                                uiMeta.setDisplayName ( CC.translate ( "&f&lMistico" ) );
                                                                ui.setItemMeta ( uiMeta );
                                                                inventoryContents.set ( 3, 7, ClickableItem.of ( ui, e -> {
                                                                    player.performCommand ( "warp mistico" );
                                                                } ) );

                                                                ItemStack back = new ItemStack ( Material.REDSTONE );
                                                                ItemMeta backMeta = back.getItemMeta ( );
                                                                backMeta.setDisplayName ( CC.translate ( "&c&lSalir" ) );
                                                                back.setItemMeta ( backMeta );
                                                                inventoryContents.set ( 5, 0, ClickableItem.of ( back, e -> {
                                                                    player.closeInventory ( );
                                                                } ) );

                                                                ItemStack optional = new ItemStack ( 4460 );
                                                                ItemMeta optionalMeta = optional.getItemMeta ( );
                                                                optionalMeta.setDisplayName ( CC.translate ( "&a&lSiguiente" ) );
                                                                optional.setItemMeta ( optionalMeta );
                                                                inventoryContents.set ( 5, 8, ClickableItem.of ( optional, e -> {
                                                                    SmartInventory saiyanInventory = SmartInventory.builder ( )
                                                                            .size ( 6, 9 )
                                                                            .id ( "saiyan2_transformation" )
                                                                            .type ( InventoryType.CHEST )
                                                                            .title ( CC.translate ( "&e&lTransformaciones Saiyan" ) )
                                                                            .provider ( new InventoryProvider ( ) {
                                                                                @Override
                                                                                public void init ( Player player, InventoryContents inventoryContents ) {

                                                                                    ItemStack ssb = new ItemStack ( 6192 );
                                                                                    ItemMeta ssbMeta = ssb.getItemMeta ( );
                                                                                    ssbMeta.setDisplayName ( CC.translate ( "&c&lSSJ4" ) );
                                                                                    ssb.setItemMeta ( ssbMeta );
                                                                                    inventoryContents.set ( 1, 1, ClickableItem.of ( ssb, e -> {
                                                                                        player.performCommand ( "warp ssj4" );
                                                                                    } ) );

                                                                                    ItemStack ssj1 = new ItemStack ( 7 );
                                                                                    ItemMeta ssj1Meta = ssj1.getItemMeta ( );
                                                                                    ssj1Meta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssj1.setItemMeta ( ssj1Meta );
                                                                                    inventoryContents.set ( 1, 3, ClickableItem.of ( ssj1, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssj2 = new ItemStack ( 7 );
                                                                                    ItemMeta ssj2Meta = ssj2.getItemMeta ( );
                                                                                    ssj2Meta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssj2.setItemMeta ( ssj2Meta );
                                                                                    inventoryContents.set ( 1, 5, ClickableItem.of ( ssj2, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssj3 = new ItemStack ( 7 );
                                                                                    ItemMeta ssj3Meta = ssj3.getItemMeta ( );
                                                                                    ssj3Meta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssj3.setItemMeta ( ssj3Meta );
                                                                                    inventoryContents.set ( 1, 7, ClickableItem.of ( ssj3, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssjr = new ItemStack ( 7 );
                                                                                    ItemMeta ssjrMeta = ssjr.getItemMeta ( );
                                                                                    ssjrMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssjr.setItemMeta ( ssjrMeta );
                                                                                    inventoryContents.set ( 3, 1, ClickableItem.of ( ssjr, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssjbe = new ItemStack ( 7 );
                                                                                    ItemMeta ssjbeMeta = ssjbe.getItemMeta ( );
                                                                                    ssjbeMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssjbe.setItemMeta ( ssjbeMeta );
                                                                                    inventoryContents.set ( 3, 3, ClickableItem.of ( ssjbe, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssjg = new ItemStack ( 7 );
                                                                                    ItemMeta ssjgMeta = ssjg.getItemMeta ( );
                                                                                    ssjgMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssjg.setItemMeta ( ssjgMeta );
                                                                                    inventoryContents.set ( 3, 5, ClickableItem.of ( ssjg, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ui = new ItemStack ( 7 );
                                                                                    ItemMeta uiMeta = ui.getItemMeta ( );
                                                                                    uiMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ui.setItemMeta ( uiMeta );
                                                                                    inventoryContents.set ( 3, 7, ClickableItem.of ( ui, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack back = new ItemStack ( Material.REDSTONE );
                                                                                    ItemMeta backMeta = back.getItemMeta ( );
                                                                                    backMeta.setDisplayName ( CC.translate ( "&c&lSalir" ) );
                                                                                    back.setItemMeta ( backMeta );
                                                                                    inventoryContents.set ( 5, 0, ClickableItem.of ( back, e -> {
                                                                                        player.closeInventory ( );
                                                                                    } ) );

                                                                                    ItemStack optional = new ItemStack ( 4460 );
                                                                                    ItemMeta optionalMeta = optional.getItemMeta ( );
                                                                                    optionalMeta.setDisplayName ( CC.translate ( "&aSiguiente" ) );
                                                                                    optional.setItemMeta ( optionalMeta );
                                                                                    inventoryContents.set ( 5, 8, ClickableItem.of ( optional, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&a¡Mostrando estadísticas Saiyan!" ) );
                                                                                    } ) );

                                                                                    ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) );
                                                                                    ItemMeta panelMeta = panel.getItemMeta ( );
                                                                                    panelMeta.setDisplayName ( " " );
                                                                                    panel.setItemMeta ( panelMeta );

                                                                                    ClickableItem yellowPane = ClickableItem.empty ( panel );

                                                                                    for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                                                        for (int col = 0; col < 9; col++) {
                                                                                            if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                                                                inventoryContents.set ( row, col, yellowPane );
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void update ( Player player, InventoryContents inventoryContents ) {
                                                                                }
                                                                            } ).build ( );
                                                                    saiyanInventory.open ( player );
                                                                    // === FIN DEL NUEVO MENÚ PAGINA 2 SAIYAN
                                                                } ) );


                                                                ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) );
                                                                ItemMeta panelMeta = panel.getItemMeta ( );
                                                                panelMeta.setDisplayName ( " " );
                                                                panel.setItemMeta ( panelMeta );

                                                                ClickableItem yellowPane = ClickableItem.empty ( panel );

                                                                for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                                    for (int col = 0; col < 9; col++) {
                                                                        if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                                            inventoryContents.set ( row, col, yellowPane );
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void update ( Player player, InventoryContents inventoryContents ) {
                                                            }
                                                        } ).build ( );
                                                saiyanInventory.open ( player );
                                                // === FIN DEL NUEVO MENÚ DE TRANSFORMACIONES SAIYAN ===
                                            } ) );

                                            ItemStack majin = new ItemStack ( 6190 ); // ID base
                                            ItemMeta majinMeta = majin.getItemMeta ( );
                                            majinMeta.setDisplayName ( CC.translate ( "&5&lMajin" ) );
                                            majin.setItemMeta ( majinMeta );
                                            inventoryContents.set ( 2, 2, ClickableItem.of ( majin, event -> {
                                                // === INICIO DEL NUEVO MENÚ DE TRANSFORMACIONES MAJIN ===
                                                SmartInventory saiyanInventory = SmartInventory.builder ( )
                                                        .size ( 6, 9 )
                                                        .id ( "majin_transformation" ) // ID para este sub-menú
                                                        .type ( InventoryType.CHEST )
                                                        .title ( CC.translate ( "&d&lTransformaciones Majin" ) )
                                                        .provider ( new InventoryProvider ( ) {
                                                            @Override
                                                            public void init ( Player player, InventoryContents inventoryContents ) {

                                                                ItemStack ssb = new ItemStack ( 6192 );
                                                                ItemMeta ssbMeta = ssb.getItemMeta ( );
                                                                ssbMeta.setDisplayName ( CC.translate ( "&c&lKaioken" ) );
                                                                ssb.setItemMeta ( ssbMeta );
                                                                inventoryContents.set ( 1, 1, ClickableItem.of ( ssb, e -> {
                                                                    player.performCommand ( "warp kaioken" );
                                                                } ) );

                                                                ItemStack ssj1 = new ItemStack ( 6183 );
                                                                ItemMeta ssj1Meta = ssj1.getItemMeta ( );
                                                                ssj1Meta.setDisplayName ( CC.translate ( "&c&lSatajin" ) );
                                                                ssj1.setItemMeta ( ssj1Meta );
                                                                inventoryContents.set ( 1, 3, ClickableItem.of ( ssj1, e -> {
                                                                    player.performCommand ( "warp satajin" );
                                                                } ) );

                                                                ItemStack ssj2 = new ItemStack ( 6183 );
                                                                ItemMeta ssj2Meta = ssj2.getItemMeta ( );
                                                                ssj2Meta.setDisplayName ( CC.translate ( "&c&lRegeneration" ) );
                                                                ssj2.setItemMeta ( ssj2Meta );
                                                                inventoryContents.set ( 1, 5, ClickableItem.of ( ssj2, e -> {
                                                                    player.performCommand ( "warp regeneration" );
                                                                } ) );

                                                                ItemStack ssj3 = new ItemStack ( 6183 );
                                                                ItemMeta ssj3Meta = ssj3.getItemMeta ( );
                                                                ssj3Meta.setDisplayName ( CC.translate ( "&c&lEvil Form" ) );
                                                                ssj3.setItemMeta ( ssj3Meta );
                                                                inventoryContents.set ( 1, 7, ClickableItem.of ( ssj3, e -> {
                                                                    player.performCommand ( "warp evilform" );
                                                                } ) );

                                                                ItemStack ssjr = new ItemStack ( 6183 );
                                                                ItemMeta ssjrMeta = ssjr.getItemMeta ( );
                                                                ssjrMeta.setDisplayName ( CC.translate ( "&c&lSuper Form" ) );
                                                                ssjr.setItemMeta ( ssjrMeta );
                                                                inventoryContents.set ( 3, 1, ClickableItem.of ( ssjr, e -> {
                                                                    player.performCommand ( "warp superform" );
                                                                } ) );

                                                                ItemStack ssjbe = new ItemStack ( 5767 );
                                                                ItemMeta ssjbeMeta = ssjbe.getItemMeta ( );
                                                                ssjbeMeta.setDisplayName ( CC.translate ( "&7&lAndroide 1" ) );
                                                                ssjbe.setItemMeta ( ssjbeMeta );
                                                                inventoryContents.set ( 3, 3, ClickableItem.of ( ssjbe, e -> {
                                                                    player.performCommand ( "warp androide1" );
                                                                } ) );

                                                                ItemStack ssjg = new ItemStack ( 6183 );
                                                                ItemMeta ssjgMeta = ssjg.getItemMeta ( );
                                                                ssjgMeta.setDisplayName ( CC.translate ( "&c&lPure Form" ) );
                                                                ssjg.setItemMeta ( ssjgMeta );
                                                                inventoryContents.set ( 3, 5, ClickableItem.of ( ssjg, e -> {
                                                                    player.performCommand ( "warp pureform" );
                                                                } ) );

                                                                ItemStack ui = new ItemStack ( 5767 );
                                                                ItemMeta uiMeta = ui.getItemMeta ( );
                                                                uiMeta.setDisplayName ( CC.translate ( "&7&lAndroide 2" ) );
                                                                ui.setItemMeta ( uiMeta );
                                                                inventoryContents.set ( 3, 7, ClickableItem.of ( ui, e -> {
                                                                    player.performCommand ( "warp androide2" );
                                                                } ) );

                                                                ItemStack back = new ItemStack ( Material.REDSTONE );
                                                                ItemMeta backMeta = back.getItemMeta ( );
                                                                backMeta.setDisplayName ( CC.translate ( "&c&lSalir" ) );
                                                                back.setItemMeta ( backMeta );
                                                                inventoryContents.set ( 5, 0, ClickableItem.of ( back, e -> {
                                                                    player.closeInventory ( );
                                                                } ) );

                                                                ItemStack optional = new ItemStack ( 4460 );
                                                                ItemMeta optionalMeta = optional.getItemMeta ( );
                                                                optionalMeta.setDisplayName ( CC.translate ( "&a&lSiguiente" ) );
                                                                optional.setItemMeta ( optionalMeta );
                                                                inventoryContents.set ( 5, 8, ClickableItem.of ( optional, e -> {
                                                                    SmartInventory saiyanInventory = SmartInventory.builder ( )
                                                                            .size ( 6, 9 )
                                                                            .id ( "majin2_transformation" )
                                                                            .type ( InventoryType.CHEST )
                                                                            .title ( CC.translate ( "&d&lTransformaciones Majin" ) )
                                                                            .provider ( new InventoryProvider ( ) {
                                                                                @Override
                                                                                public void init ( Player player, InventoryContents inventoryContents ) {

                                                                                    ItemStack ssb = new ItemStack ( 6210 );
                                                                                    ItemMeta ssbMeta = ssb.getItemMeta ( );
                                                                                    ssbMeta.setDisplayName ( CC.translate ( "&f&lMistico" ) );
                                                                                    ssb.setItemMeta ( ssbMeta );
                                                                                    inventoryContents.set ( 1, 1, ClickableItem.of ( ssb, e -> {
                                                                                        player.performCommand ( "warp mistico" );
                                                                                    } ) );

                                                                                    ItemStack ssj1 = new ItemStack ( 6183 );
                                                                                    ItemMeta ssj1Meta = ssj1.getItemMeta ( );
                                                                                    ssj1Meta.setDisplayName ( CC.translate ( "&4&lFase 4" ) );
                                                                                    ssj1.setItemMeta ( ssj1Meta );
                                                                                    inventoryContents.set ( 1, 3, ClickableItem.of ( ssj1, e -> {
                                                                                        player.performCommand ( "warp majinfase4" );
                                                                                    } ) );

                                                                                    ItemStack ssj2 = new ItemStack ( 7 );
                                                                                    ItemMeta ssj2Meta = ssj2.getItemMeta ( );
                                                                                    ssj2Meta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssj2.setItemMeta ( ssj2Meta );
                                                                                    inventoryContents.set ( 1, 5, ClickableItem.of ( ssj2, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssj3 = new ItemStack ( 7 );
                                                                                    ItemMeta ssj3Meta = ssj3.getItemMeta ( );
                                                                                    ssj3Meta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssj3.setItemMeta ( ssj3Meta );
                                                                                    inventoryContents.set ( 1, 7, ClickableItem.of ( ssj3, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssjr = new ItemStack ( 7 );
                                                                                    ItemMeta ssjrMeta = ssjr.getItemMeta ( );
                                                                                    ssjrMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssjr.setItemMeta ( ssjrMeta );
                                                                                    inventoryContents.set ( 3, 1, ClickableItem.of ( ssjr, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssjbe = new ItemStack ( 7 );
                                                                                    ItemMeta ssjbeMeta = ssjbe.getItemMeta ( );
                                                                                    ssjbeMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssjbe.setItemMeta ( ssjbeMeta );
                                                                                    inventoryContents.set ( 3, 3, ClickableItem.of ( ssjbe, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssjg = new ItemStack ( 7 );
                                                                                    ItemMeta ssjgMeta = ssjg.getItemMeta ( );
                                                                                    ssjgMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssjg.setItemMeta ( ssjgMeta );
                                                                                    inventoryContents.set ( 3, 5, ClickableItem.of ( ssjg, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ui = new ItemStack ( 7 );
                                                                                    ItemMeta uiMeta = ui.getItemMeta ( );
                                                                                    uiMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ui.setItemMeta ( uiMeta );
                                                                                    inventoryContents.set ( 3, 7, ClickableItem.of ( ui, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack back = new ItemStack ( Material.REDSTONE );
                                                                                    ItemMeta backMeta = back.getItemMeta ( );
                                                                                    backMeta.setDisplayName ( CC.translate ( "&c&lSalir" ) );
                                                                                    back.setItemMeta ( backMeta );
                                                                                    inventoryContents.set ( 5, 0, ClickableItem.of ( back, e -> {
                                                                                        player.closeInventory ( );
                                                                                    } ) );

                                                                                    ItemStack optional = new ItemStack ( 4460 );
                                                                                    ItemMeta optionalMeta = optional.getItemMeta ( );
                                                                                    optionalMeta.setDisplayName ( CC.translate ( "&aSiguiente" ) );
                                                                                    optional.setItemMeta ( optionalMeta );
                                                                                    inventoryContents.set ( 5, 8, ClickableItem.of ( optional, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&a¡Mostrando estadísticas Saiyan!" ) );
                                                                                    } ) );

                                                                                    ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) );
                                                                                    ItemMeta panelMeta = panel.getItemMeta ( );
                                                                                    panelMeta.setDisplayName ( " " );
                                                                                    panel.setItemMeta ( panelMeta );

                                                                                    ClickableItem yellowPane = ClickableItem.empty ( panel );

                                                                                    for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                                                        for (int col = 0; col < 9; col++) {
                                                                                            if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                                                                inventoryContents.set ( row, col, yellowPane );
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void update ( Player player, InventoryContents inventoryContents ) {
                                                                                }
                                                                            } ).build ( );
                                                                    saiyanInventory.open ( player );
                                                                    // === FIN DEL NUEVO MENÚ PAGINA 2 MAJIN
                                                                } ) );

                                                                ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) );
                                                                ItemMeta panelMeta = panel.getItemMeta ( );
                                                                panelMeta.setDisplayName ( " " );
                                                                panel.setItemMeta ( panelMeta );

                                                                ClickableItem yellowPane = ClickableItem.empty ( panel );

                                                                for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                                    for (int col = 0; col < 9; col++) {
                                                                        if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                                            inventoryContents.set ( row, col, yellowPane );
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void update ( Player player, InventoryContents inventoryContents ) {
                                                            }
                                                        } ).build ( );
                                                saiyanInventory.open ( player );
                                            } ) );

                                            ItemStack arcosian = new ItemStack ( 6219 ); // ID base
                                            ItemMeta arcosianMeta = arcosian.getItemMeta ( );
                                            arcosianMeta.setDisplayName ( CC.translate ( "&f&lArcosian" ) );
                                            arcosian.setItemMeta ( arcosianMeta );
                                            inventoryContents.set ( 2, 6, ClickableItem.of ( arcosian, event -> {
                                                SmartInventory saiyanInventory = SmartInventory.builder ( )
                                                        .size ( 6, 9 )
                                                        .id ( "arcosian_transformation" )
                                                        .type ( InventoryType.CHEST )
                                                        .title ( CC.translate ( "&5&lTransformaciones Arco" ) )
                                                        .provider ( new InventoryProvider ( ) {
                                                            @Override
                                                            public void init ( Player player, InventoryContents inventoryContents ) {

                                                                ItemStack ssb = new ItemStack ( 6192 );
                                                                ItemMeta ssbMeta = ssb.getItemMeta ( );
                                                                ssbMeta.setDisplayName ( CC.translate ( "&c&lKaioken" ) );
                                                                ssb.setItemMeta ( ssbMeta );
                                                                inventoryContents.set ( 1, 1, ClickableItem.of ( ssb, e -> {
                                                                    player.performCommand ( "warp kaioken" );
                                                                } ) );

                                                                ItemStack ssj1 = new ItemStack ( 6183 );
                                                                ItemMeta ssj1Meta = ssj1.getItemMeta ( );
                                                                ssj1Meta.setDisplayName ( CC.translate ( "&5&lThird Form" ) );
                                                                ssj1.setItemMeta ( ssj1Meta );
                                                                inventoryContents.set ( 1, 3, ClickableItem.of ( ssj1, e -> {
                                                                    player.performCommand ( "warp thirdform" );
                                                                } ) );

                                                                ItemStack ssj2 = new ItemStack ( 6183 );
                                                                ItemMeta ssj2Meta = ssj2.getItemMeta ( );
                                                                ssj2Meta.setDisplayName ( CC.translate ( "&5&lFinal Form" ) );
                                                                ssj2.setItemMeta ( ssj2Meta );
                                                                inventoryContents.set ( 1, 5, ClickableItem.of ( ssj2, e -> {
                                                                    player.performCommand ( "warp finalform" );
                                                                } ) );

                                                                ItemStack ssj3 = new ItemStack ( 6183 );
                                                                ItemMeta ssj3Meta = ssj3.getItemMeta ( );
                                                                ssj3Meta.setDisplayName ( CC.translate ( "&5&lFinal Form 100%" ) );
                                                                ssj3.setItemMeta ( ssj3Meta );
                                                                inventoryContents.set ( 1, 7, ClickableItem.of ( ssj3, e -> {
                                                                    player.performCommand ( "warp finalform100" );
                                                                } ) );

                                                                ItemStack ssjr = new ItemStack ( 5767 );
                                                                ItemMeta ssjrMeta = ssjr.getItemMeta ( );
                                                                ssjrMeta.setDisplayName ( CC.translate ( "&7&lAndroide 1" ) );
                                                                ssjr.setItemMeta ( ssjrMeta );
                                                                inventoryContents.set ( 3, 1, ClickableItem.of ( ssjr, e -> {
                                                                    player.performCommand ( "warp androide1" );
                                                                } ) );

                                                                ItemStack ssjbe = new ItemStack ( 6183 );
                                                                ItemMeta ssjbeMeta = ssjbe.getItemMeta ( );
                                                                ssjbeMeta.setDisplayName ( CC.translate ( "&5&lCooler Final Form" ) );
                                                                ssjbe.setItemMeta ( ssjbeMeta );
                                                                inventoryContents.set ( 3, 3, ClickableItem.of ( ssjbe, e -> {
                                                                    player.performCommand ( "warp coolerfinalform" );
                                                                } ) );

                                                                ItemStack ssjg = new ItemStack ( 5767 );
                                                                ItemMeta ssjgMeta = ssjg.getItemMeta ( );
                                                                ssjgMeta.setDisplayName ( CC.translate ( "&7&lAndroide 2" ) );
                                                                ssjg.setItemMeta ( ssjgMeta );
                                                                inventoryContents.set ( 3, 5, ClickableItem.of ( ssjg, e -> {
                                                                    player.performCommand ( "saiyan ssjg" );
                                                                } ) );

                                                                ItemStack ui = new ItemStack ( 6210 );
                                                                ItemMeta uiMeta = ui.getItemMeta ( );
                                                                uiMeta.setDisplayName ( CC.translate ( "&f&lMistico" ) );
                                                                ui.setItemMeta ( uiMeta );
                                                                inventoryContents.set ( 3, 7, ClickableItem.of ( ui, e -> {
                                                                    player.performCommand ( "warp mistico" );
                                                                } ) );

                                                                ItemStack back = new ItemStack ( Material.REDSTONE );
                                                                ItemMeta backMeta = back.getItemMeta ( );
                                                                backMeta.setDisplayName ( CC.translate ( "&c&lSalir" ) );
                                                                back.setItemMeta ( backMeta );
                                                                inventoryContents.set ( 5, 0, ClickableItem.of ( back, e -> {
                                                                    player.closeInventory ( );
                                                                } ) );

                                                                ItemStack optional = new ItemStack ( 4460 );
                                                                ItemMeta optionalMeta = optional.getItemMeta ( );
                                                                optionalMeta.setDisplayName ( CC.translate ( "&aSiguiente" ) );
                                                                optional.setItemMeta ( optionalMeta );
                                                                inventoryContents.set ( 5, 8, ClickableItem.of ( optional, e -> {
                                                                    SmartInventory saiyanInventory = SmartInventory.builder ( )
                                                                            .size ( 6, 9 )
                                                                            .id ( "arcosian2_transformation" )
                                                                            .type ( InventoryType.CHEST )
                                                                            .title ( CC.translate ( "&5&lTransformaciones Arco" ) )
                                                                            .provider ( new InventoryProvider ( ) {
                                                                                @Override
                                                                                public void init ( Player player, InventoryContents inventoryContents ) {

                                                                                    ItemStack ssb = new ItemStack ( 6210 );
                                                                                    ItemMeta ssbMeta = ssb.getItemMeta ( );
                                                                                    ssbMeta.setDisplayName ( CC.translate ( "&4&lFase 4" ) );
                                                                                    ssb.setItemMeta ( ssbMeta );
                                                                                    inventoryContents.set ( 1, 1, ClickableItem.of ( ssb, e -> {
                                                                                        player.performCommand ( "warp arcofase4" );
                                                                                    } ) );

                                                                                    ItemStack ssj1 = new ItemStack ( 7 );
                                                                                    ItemMeta ssj1Meta = ssj1.getItemMeta ( );
                                                                                    ssj1Meta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssj1.setItemMeta ( ssj1Meta );
                                                                                    inventoryContents.set ( 1, 3, ClickableItem.of ( ssj1, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssj2 = new ItemStack ( 7 );
                                                                                    ItemMeta ssj2Meta = ssj2.getItemMeta ( );
                                                                                    ssj2Meta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssj2.setItemMeta ( ssj2Meta );
                                                                                    inventoryContents.set ( 1, 5, ClickableItem.of ( ssj2, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssj3 = new ItemStack ( 7 );
                                                                                    ItemMeta ssj3Meta = ssj3.getItemMeta ( );
                                                                                    ssj3Meta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssj3.setItemMeta ( ssj3Meta );
                                                                                    inventoryContents.set ( 1, 7, ClickableItem.of ( ssj3, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssjr = new ItemStack ( 7 );
                                                                                    ItemMeta ssjrMeta = ssjr.getItemMeta ( );
                                                                                    ssjrMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssjr.setItemMeta ( ssjrMeta );
                                                                                    inventoryContents.set ( 3, 1, ClickableItem.of ( ssjr, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssjbe = new ItemStack ( 7 );
                                                                                    ItemMeta ssjbeMeta = ssjbe.getItemMeta ( );
                                                                                    ssjbeMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssjbe.setItemMeta ( ssjbeMeta );
                                                                                    inventoryContents.set ( 3, 3, ClickableItem.of ( ssjbe, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssjg = new ItemStack ( 7 );
                                                                                    ItemMeta ssjgMeta = ssjg.getItemMeta ( );
                                                                                    ssjgMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssjg.setItemMeta ( ssjgMeta );
                                                                                    inventoryContents.set ( 3, 5, ClickableItem.of ( ssjg, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ui = new ItemStack ( 7 );
                                                                                    ItemMeta uiMeta = ui.getItemMeta ( );
                                                                                    uiMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ui.setItemMeta ( uiMeta );
                                                                                    inventoryContents.set ( 3, 7, ClickableItem.of ( ui, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack back = new ItemStack ( Material.REDSTONE );
                                                                                    ItemMeta backMeta = back.getItemMeta ( );
                                                                                    backMeta.setDisplayName ( CC.translate ( "&c&lSalir" ) );
                                                                                    back.setItemMeta ( backMeta );
                                                                                    inventoryContents.set ( 5, 0, ClickableItem.of ( back, e -> {
                                                                                        player.closeInventory ( );
                                                                                    } ) );

                                                                                    ItemStack optional = new ItemStack ( 4460 );
                                                                                    ItemMeta optionalMeta = optional.getItemMeta ( );
                                                                                    optionalMeta.setDisplayName ( CC.translate ( "&aSiguiente" ) );
                                                                                    optional.setItemMeta ( optionalMeta );
                                                                                    inventoryContents.set ( 5, 8, ClickableItem.of ( optional, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&a¡Mostrando estadísticas Saiyan!" ) );
                                                                                    } ) );

                                                                                    ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) );
                                                                                    ItemMeta panelMeta = panel.getItemMeta ( );
                                                                                    panelMeta.setDisplayName ( " " );
                                                                                    panel.setItemMeta ( panelMeta );

                                                                                    ClickableItem yellowPane = ClickableItem.empty ( panel );

                                                                                    for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                                                        for (int col = 0; col < 9; col++) {
                                                                                            if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                                                                inventoryContents.set ( row, col, yellowPane );
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void update ( Player player, InventoryContents inventoryContents ) {
                                                                                }
                                                                            } ).build ( );
                                                                    saiyanInventory.open ( player );
                                                                    // === FIN DEL NUEVO MENÚ PAGINA 2 ARCO
                                                                } ) );

                                                                ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) );
                                                                ItemMeta panelMeta = panel.getItemMeta ( );
                                                                panelMeta.setDisplayName ( " " );
                                                                panel.setItemMeta ( panelMeta );

                                                                ClickableItem yellowPane = ClickableItem.empty ( panel );

                                                                for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                                    for (int col = 0; col < 9; col++) {
                                                                        if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                                            inventoryContents.set ( row, col, yellowPane );
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void update ( Player player, InventoryContents inventoryContents ) {
                                                            }
                                                        } ).build ( );
                                                saiyanInventory.open ( player );
                                                // === FIN DEL NUEVO MENÚ DE TRANSFORMACIONES ARCO ===
                                            } ) );

                                            // Ítem de Humano (Posición 3, 4)
                                            ItemStack humano = new ItemStack ( 6207 ); // ID base
                                            ItemMeta humanoMeta = humano.getItemMeta ( );
                                            humanoMeta.setDisplayName ( CC.translate ( "&6&lHuman" ) );
                                            humano.setItemMeta ( humanoMeta );
                                            inventoryContents.set ( 3, 4, ClickableItem.of ( humano, event -> {
                                                SmartInventory saiyanInventory = SmartInventory.builder ( )
                                                        .size ( 6, 9 )
                                                        .id ( "human_transformation" )
                                                        .type ( InventoryType.CHEST )
                                                        .title ( CC.translate ( "&f&lTransformaciones Humano" ) )
                                                        .provider ( new InventoryProvider ( ) {
                                                            @Override
                                                            public void init ( Player player, InventoryContents inventoryContents ) {

                                                                ItemStack ssb = new ItemStack ( 6192 );
                                                                ItemMeta ssbMeta = ssb.getItemMeta ( );
                                                                ssbMeta.setDisplayName ( CC.translate ( "&c&lKaioken" ) );
                                                                ssb.setItemMeta ( ssbMeta );
                                                                inventoryContents.set ( 1, 1, ClickableItem.of ( ssb, e -> {
                                                                    player.performCommand ( "warp kaioken" );
                                                                } ) );

                                                                ItemStack ssj1 = new ItemStack ( 6183 );
                                                                ItemMeta ssj1Meta = ssj1.getItemMeta ( );
                                                                ssj1Meta.setDisplayName ( CC.translate ( "&f&lRoshi Form" ) );
                                                                ssj1.setItemMeta ( ssj1Meta );
                                                                inventoryContents.set ( 1, 3, ClickableItem.of ( ssj1, e -> {
                                                                    player.performCommand ( "warp roshiform" );
                                                                } ) );

                                                                ItemStack ssj2 = new ItemStack ( 6183 );
                                                                ItemMeta ssj2Meta = ssj2.getItemMeta ( );
                                                                ssj2Meta.setDisplayName ( CC.translate ( "&f&lForce unleashed" ) );
                                                                ssj2.setItemMeta ( ssj2Meta );
                                                                inventoryContents.set ( 1, 5, ClickableItem.of ( ssj2, e -> {
                                                                    player.performCommand ( "warp forceunleashed" );
                                                                } ) );

                                                                ItemStack ssj3 = new ItemStack ( 6183 );
                                                                ItemMeta ssj3Meta = ssj3.getItemMeta ( );
                                                                ssj3Meta.setDisplayName ( CC.translate ( "&f&lEmpowered" ) );
                                                                ssj3.setItemMeta ( ssj3Meta );
                                                                inventoryContents.set ( 1, 7, ClickableItem.of ( ssj3, e -> {
                                                                    player.performCommand ( "warp empowered" );
                                                                } ) );

                                                                ItemStack ssjr = new ItemStack ( 5767 );
                                                                ItemMeta ssjrMeta = ssjr.getItemMeta ( );
                                                                ssjrMeta.setDisplayName ( CC.translate ( "&7&lAndroide 1" ) );
                                                                ssjr.setItemMeta ( ssjrMeta );
                                                                inventoryContents.set ( 3, 1, ClickableItem.of ( ssjr, e -> {
                                                                    player.performCommand ( "warp androide1" );
                                                                } ) );

                                                                ItemStack ssjbe = new ItemStack ( 6183 );
                                                                ItemMeta ssjbeMeta = ssjbe.getItemMeta ( );
                                                                ssjbeMeta.setDisplayName ( CC.translate ( "&c&lSuper Kaioken" ) );
                                                                ssjbe.setItemMeta ( ssjbeMeta );
                                                                inventoryContents.set ( 3, 3, ClickableItem.of ( ssjbe, e -> {
                                                                    player.performCommand ( "warp superkaioken" );
                                                                } ) );

                                                                ItemStack ssjg = new ItemStack ( 5767 );
                                                                ItemMeta ssjgMeta = ssjg.getItemMeta ( );
                                                                ssjgMeta.setDisplayName ( CC.translate ( "&7&lAndroide 2" ) );
                                                                ssjg.setItemMeta ( ssjgMeta );
                                                                inventoryContents.set ( 3, 5, ClickableItem.of ( ssjg, e -> {
                                                                    player.performCommand ( "warp androide2" );
                                                                } ) );

                                                                ItemStack ui = new ItemStack ( 6210 );
                                                                ItemMeta uiMeta = ui.getItemMeta ( );
                                                                uiMeta.setDisplayName ( CC.translate ( "&f&lMistico" ) );
                                                                ui.setItemMeta ( uiMeta );
                                                                inventoryContents.set ( 3, 7, ClickableItem.of ( ui, e -> {
                                                                    player.performCommand ( "warp mistico" );
                                                                } ) );

                                                                ItemStack back = new ItemStack ( Material.REDSTONE );
                                                                ItemMeta backMeta = back.getItemMeta ( );
                                                                backMeta.setDisplayName ( CC.translate ( "&c&lSalir" ) );
                                                                back.setItemMeta ( backMeta );
                                                                inventoryContents.set ( 5, 0, ClickableItem.of ( back, e -> {
                                                                    player.closeInventory ( );
                                                                } ) );

                                                                ItemStack optional = new ItemStack ( 4460 );
                                                                ItemMeta optionalMeta = optional.getItemMeta ( );
                                                                optionalMeta.setDisplayName ( CC.translate ( "&aSiguiente" ) );
                                                                optional.setItemMeta ( optionalMeta );
                                                                inventoryContents.set ( 5, 8, ClickableItem.of ( optional, e -> {
                                                                    SmartInventory saiyanInventory = SmartInventory.builder ( )
                                                                            .size ( 6, 9 )
                                                                            .id ( "human2_transformation" )
                                                                            .type ( InventoryType.CHEST )
                                                                            .title ( CC.translate ( "&f&lTransformaciones Humano" ) )
                                                                            .provider ( new InventoryProvider ( ) {
                                                                                @Override
                                                                                public void init ( Player player, InventoryContents inventoryContents ) {

                                                                                    ItemStack ssb = new ItemStack ( 6210 );
                                                                                    ItemMeta ssbMeta = ssb.getItemMeta ( );
                                                                                    ssbMeta.setDisplayName ( CC.translate ( "&4&lFase 4" ) );
                                                                                    ssb.setItemMeta ( ssbMeta );
                                                                                    inventoryContents.set ( 1, 1, ClickableItem.of ( ssb, e -> {
                                                                                        player.performCommand ( "warp humanfase4" );
                                                                                    } ) );

                                                                                    ItemStack ssj1 = new ItemStack ( 7 );
                                                                                    ItemMeta ssj1Meta = ssj1.getItemMeta ( );
                                                                                    ssj1Meta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssj1.setItemMeta ( ssj1Meta );
                                                                                    inventoryContents.set ( 1, 3, ClickableItem.of ( ssj1, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssj2 = new ItemStack ( 7 );
                                                                                    ItemMeta ssj2Meta = ssj2.getItemMeta ( );
                                                                                    ssj2Meta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssj2.setItemMeta ( ssj2Meta );
                                                                                    inventoryContents.set ( 1, 5, ClickableItem.of ( ssj2, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssj3 = new ItemStack ( 7 );
                                                                                    ItemMeta ssj3Meta = ssj3.getItemMeta ( );
                                                                                    ssj3Meta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssj3.setItemMeta ( ssj3Meta );
                                                                                    inventoryContents.set ( 1, 7, ClickableItem.of ( ssj3, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssjr = new ItemStack ( 7 );
                                                                                    ItemMeta ssjrMeta = ssjr.getItemMeta ( );
                                                                                    ssjrMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssjr.setItemMeta ( ssjrMeta );
                                                                                    inventoryContents.set ( 3, 1, ClickableItem.of ( ssjr, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssjbe = new ItemStack ( 7 );
                                                                                    ItemMeta ssjbeMeta = ssjbe.getItemMeta ( );
                                                                                    ssjbeMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssjbe.setItemMeta ( ssjbeMeta );
                                                                                    inventoryContents.set ( 3, 3, ClickableItem.of ( ssjbe, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssjg = new ItemStack ( 7 );
                                                                                    ItemMeta ssjgMeta = ssjg.getItemMeta ( );
                                                                                    ssjgMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssjg.setItemMeta ( ssjgMeta );
                                                                                    inventoryContents.set ( 3, 5, ClickableItem.of ( ssjg, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ui = new ItemStack ( 7 );
                                                                                    ItemMeta uiMeta = ui.getItemMeta ( );
                                                                                    uiMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ui.setItemMeta ( uiMeta );
                                                                                    inventoryContents.set ( 3, 7, ClickableItem.of ( ui, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack back = new ItemStack ( Material.REDSTONE );
                                                                                    ItemMeta backMeta = back.getItemMeta ( );
                                                                                    backMeta.setDisplayName ( CC.translate ( "&c&lSalir" ) );
                                                                                    back.setItemMeta ( backMeta );
                                                                                    inventoryContents.set ( 5, 0, ClickableItem.of ( back, e -> {
                                                                                        player.closeInventory ( );
                                                                                    } ) );

                                                                                    ItemStack optional = new ItemStack ( 4460 );
                                                                                    ItemMeta optionalMeta = optional.getItemMeta ( );
                                                                                    optionalMeta.setDisplayName ( CC.translate ( "&aSiguiente" ) );
                                                                                    optional.setItemMeta ( optionalMeta );
                                                                                    inventoryContents.set ( 5, 8, ClickableItem.of ( optional, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&a¡Mostrando estadísticas Saiyan!" ) );
                                                                                    } ) );

                                                                                    ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) );
                                                                                    ItemMeta panelMeta = panel.getItemMeta ( );
                                                                                    panelMeta.setDisplayName ( " " );
                                                                                    panel.setItemMeta ( panelMeta );

                                                                                    ClickableItem yellowPane = ClickableItem.empty ( panel );

                                                                                    for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                                                        for (int col = 0; col < 9; col++) {
                                                                                            if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                                                                inventoryContents.set ( row, col, yellowPane );
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void update ( Player player, InventoryContents inventoryContents ) {
                                                                                }
                                                                            } ).build ( );
                                                                    saiyanInventory.open ( player );
                                                                    // === FIN DEL NUEVO MENÚ PAGINA 2 ARCO
                                                                } ) );

                                                                ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) );
                                                                ItemMeta panelMeta = panel.getItemMeta ( );
                                                                panelMeta.setDisplayName ( " " );
                                                                panel.setItemMeta ( panelMeta );

                                                                ClickableItem yellowPane = ClickableItem.empty ( panel );

                                                                for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                                    for (int col = 0; col < 9; col++) {
                                                                        if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                                            inventoryContents.set ( row, col, yellowPane );
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void update ( Player player, InventoryContents inventoryContents ) {
                                                            }
                                                        } ).build ( );
                                                saiyanInventory.open ( player );
                                                // === FIN DEL NUEVO MENÚ DE TRANSFORMACIONES HUMAN ===
                                            } ) );

                                            // Ítem de Namekian (Posición 4, 2)
                                            ItemStack namekian = new ItemStack ( 6187 ); // ID base
                                            ItemMeta namekianMeta = namekian.getItemMeta ( );
                                            namekianMeta.setDisplayName ( CC.translate ( "&2&lNamekian" ) );
                                            namekian.setItemMeta ( namekianMeta );
                                            inventoryContents.set ( 4, 2, ClickableItem.of ( namekian, event -> {
                                                SmartInventory saiyanInventory = SmartInventory.builder ( )
                                                        .size ( 6, 9 )
                                                        .id ( "namek_transformation" )
                                                        .type ( InventoryType.CHEST )
                                                        .title ( CC.translate ( "&2&lTransformaciones Namek" ) )
                                                        .provider ( new InventoryProvider ( ) {
                                                            @Override
                                                            public void init ( Player player, InventoryContents inventoryContents ) {

                                                                ItemStack ssb = new ItemStack ( 6192 );
                                                                ItemMeta ssbMeta = ssb.getItemMeta ( );
                                                                ssbMeta.setDisplayName ( CC.translate ( "&c&lKaioken" ) );
                                                                ssb.setItemMeta ( ssbMeta );
                                                                inventoryContents.set ( 1, 1, ClickableItem.of ( ssb, e -> {
                                                                    player.performCommand ( "warp kaioken" );
                                                                } ) );

                                                                ItemStack ssj1 = new ItemStack ( 6211 );
                                                                ItemMeta ssj1Meta = ssj1.getItemMeta ( );
                                                                ssj1Meta.setDisplayName ( CC.translate ( "&2&lEmpowerment" ) );
                                                                ssj1.setItemMeta ( ssj1Meta );
                                                                inventoryContents.set ( 1, 3, ClickableItem.of ( ssj1, e -> {
                                                                    player.performCommand ( "warp empowerment" );
                                                                } ) );

                                                                ItemStack ssj2 = new ItemStack ( 6192 );
                                                                ItemMeta ssj2Meta = ssj2.getItemMeta ( );
                                                                ssj2Meta.setDisplayName ( CC.translate ( "&c&lDemon Form" ) );
                                                                ssj2.setItemMeta ( ssj2Meta );
                                                                inventoryContents.set ( 1, 5, ClickableItem.of ( ssj2, e -> {
                                                                    player.performCommand ( "warp demonform" );
                                                                } ) );

                                                                ItemStack ssj3 = new ItemStack ( 6192 );
                                                                ItemMeta ssj3Meta = ssj3.getItemMeta ( );
                                                                ssj3Meta.setDisplayName ( CC.translate ( "&c&lKing Demon" ) );
                                                                ssj3.setItemMeta ( ssj3Meta );
                                                                inventoryContents.set ( 1, 7, ClickableItem.of ( ssj3, e -> {
                                                                    player.performCommand ( "warp kingdemon" );
                                                                } ) );

                                                                ItemStack ssjr = new ItemStack ( 5767 );
                                                                ItemMeta ssjrMeta = ssjr.getItemMeta ( );
                                                                ssjrMeta.setDisplayName ( CC.translate ( "&7&lAndroide 1" ) );
                                                                ssjr.setItemMeta ( ssjrMeta );
                                                                inventoryContents.set ( 3, 1, ClickableItem.of ( ssjr, e -> {
                                                                    player.performCommand ( "warp androide1" );
                                                                } ) );

                                                                ItemStack ssjbe = new ItemStack ( 6211 );
                                                                ItemMeta ssjbeMeta = ssjbe.getItemMeta ( );
                                                                ssjbeMeta.setDisplayName ( CC.translate ( "&2&lPower Released" ) );
                                                                ssjbe.setItemMeta ( ssjbeMeta );
                                                                inventoryContents.set ( 3, 3, ClickableItem.of ( ssjbe, e -> {
                                                                    player.performCommand ( "warp powerreleased" );
                                                                } ) );

                                                                ItemStack ssjg = new ItemStack ( 5767 );
                                                                ItemMeta ssjgMeta = ssjg.getItemMeta ( );
                                                                ssjgMeta.setDisplayName ( CC.translate ( "&7&lAndroide 2" ) );
                                                                ssjg.setItemMeta ( ssjgMeta );
                                                                inventoryContents.set ( 3, 5, ClickableItem.of ( ssjg, e -> {
                                                                    player.performCommand ( "warp androide2" );
                                                                } ) );

                                                                ItemStack ui = new ItemStack ( 6210 );
                                                                ItemMeta uiMeta = ui.getItemMeta ( );
                                                                uiMeta.setDisplayName ( CC.translate ( "&f&lMistico" ) );
                                                                ui.setItemMeta ( uiMeta );
                                                                inventoryContents.set ( 3, 7, ClickableItem.of ( ui, e -> {
                                                                    player.performCommand ( "warp mistico" );
                                                                } ) );

                                                                ItemStack back = new ItemStack ( Material.REDSTONE );
                                                                ItemMeta backMeta = back.getItemMeta ( );
                                                                backMeta.setDisplayName ( CC.translate ( "&c&lSalir" ) );
                                                                back.setItemMeta ( backMeta );
                                                                inventoryContents.set ( 5, 0, ClickableItem.of ( back, e -> {
                                                                    player.closeInventory ( );
                                                                } ) );

                                                                ItemStack optional = new ItemStack ( 4460 );
                                                                ItemMeta optionalMeta = optional.getItemMeta ( );
                                                                optionalMeta.setDisplayName ( CC.translate ( "&aSiguiente" ) );
                                                                optional.setItemMeta ( optionalMeta );
                                                                inventoryContents.set ( 5, 8, ClickableItem.of ( optional, e -> {
                                                                    SmartInventory saiyanInventory = SmartInventory.builder ( )
                                                                            .size ( 6, 9 )
                                                                            .id ( "namek2_transformation" )
                                                                            .type ( InventoryType.CHEST )
                                                                            .title ( CC.translate ( "&2&lTransformaciones Namek" ) )
                                                                            .provider ( new InventoryProvider ( ) {
                                                                                @Override
                                                                                public void init ( Player player, InventoryContents inventoryContents ) {

                                                                                    ItemStack ssb = new ItemStack ( 6210 );
                                                                                    ItemMeta ssbMeta = ssb.getItemMeta ( );
                                                                                    ssbMeta.setDisplayName ( CC.translate ( "&4&lFase 4" ) );
                                                                                    ssb.setItemMeta ( ssbMeta );
                                                                                    inventoryContents.set ( 1, 1, ClickableItem.of ( ssb, e -> {
                                                                                        player.performCommand ( "warp namekfase4" );
                                                                                    } ) );

                                                                                    ItemStack ssj1 = new ItemStack ( 7 );
                                                                                    ItemMeta ssj1Meta = ssj1.getItemMeta ( );
                                                                                    ssj1Meta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssj1.setItemMeta ( ssj1Meta );
                                                                                    inventoryContents.set ( 1, 3, ClickableItem.of ( ssj1, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssj2 = new ItemStack ( 7 );
                                                                                    ItemMeta ssj2Meta = ssj2.getItemMeta ( );
                                                                                    ssj2Meta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssj2.setItemMeta ( ssj2Meta );
                                                                                    inventoryContents.set ( 1, 5, ClickableItem.of ( ssj2, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssj3 = new ItemStack ( 7 );
                                                                                    ItemMeta ssj3Meta = ssj3.getItemMeta ( );
                                                                                    ssj3Meta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssj3.setItemMeta ( ssj3Meta );
                                                                                    inventoryContents.set ( 1, 7, ClickableItem.of ( ssj3, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssjr = new ItemStack ( 7 );
                                                                                    ItemMeta ssjrMeta = ssjr.getItemMeta ( );
                                                                                    ssjrMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssjr.setItemMeta ( ssjrMeta );
                                                                                    inventoryContents.set ( 3, 1, ClickableItem.of ( ssjr, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssjbe = new ItemStack ( 7 );
                                                                                    ItemMeta ssjbeMeta = ssjbe.getItemMeta ( );
                                                                                    ssjbeMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssjbe.setItemMeta ( ssjbeMeta );
                                                                                    inventoryContents.set ( 3, 3, ClickableItem.of ( ssjbe, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ssjg = new ItemStack ( 7 );
                                                                                    ItemMeta ssjgMeta = ssjg.getItemMeta ( );
                                                                                    ssjgMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ssjg.setItemMeta ( ssjgMeta );
                                                                                    inventoryContents.set ( 3, 5, ClickableItem.of ( ssjg, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack ui = new ItemStack ( 7 );
                                                                                    ItemMeta uiMeta = ui.getItemMeta ( );
                                                                                    uiMeta.setDisplayName ( CC.translate ( "&4&lProximamente" ) );
                                                                                    ui.setItemMeta ( uiMeta );
                                                                                    inventoryContents.set ( 3, 7, ClickableItem.of ( ui, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&cProximamente..." ) );
                                                                                    } ) );

                                                                                    ItemStack back = new ItemStack ( Material.REDSTONE );
                                                                                    ItemMeta backMeta = back.getItemMeta ( );
                                                                                    backMeta.setDisplayName ( CC.translate ( "&c&lSalir" ) );
                                                                                    back.setItemMeta ( backMeta );
                                                                                    inventoryContents.set ( 5, 0, ClickableItem.of ( back, e -> {
                                                                                        player.closeInventory ( );
                                                                                    } ) );

                                                                                    ItemStack optional = new ItemStack ( 4460 );
                                                                                    ItemMeta optionalMeta = optional.getItemMeta ( );
                                                                                    optionalMeta.setDisplayName ( CC.translate ( "&aSiguiente" ) );
                                                                                    optional.setItemMeta ( optionalMeta );
                                                                                    inventoryContents.set ( 5, 8, ClickableItem.of ( optional, e -> {
                                                                                        player.sendMessage ( CC.translate ( "&a¡Mostrando estadísticas Saiyan!" ) );
                                                                                    } ) );

                                                                                    ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) );
                                                                                    ItemMeta panelMeta = panel.getItemMeta ( );
                                                                                    panelMeta.setDisplayName ( " " );
                                                                                    panel.setItemMeta ( panelMeta );

                                                                                    ClickableItem yellowPane = ClickableItem.empty ( panel );

                                                                                    for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                                                        for (int col = 0; col < 9; col++) {
                                                                                            if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                                                                inventoryContents.set ( row, col, yellowPane );
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void update ( Player player, InventoryContents inventoryContents ) {
                                                                                }
                                                                            } ).build ( );
                                                                    saiyanInventory.open ( player );
                                                                    // === FIN DEL NUEVO MENÚ PAGINA 2 NAMEK
                                                                } ) );

                                                                ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) );
                                                                ItemMeta panelMeta = panel.getItemMeta ( );
                                                                panelMeta.setDisplayName ( " " );
                                                                panel.setItemMeta ( panelMeta );

                                                                ClickableItem yellowPane = ClickableItem.empty ( panel );

                                                                for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                                    for (int col = 0; col < 9; col++) {
                                                                        if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                                            inventoryContents.set ( row, col, yellowPane );
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void update ( Player player, InventoryContents inventoryContents ) {
                                                            }
                                                        } ).build ( );
                                                saiyanInventory.open ( player );
                                                // === FIN DEL NUEVO MENÚ DE TRANSFORMACIONES HUMAN ===
                                            } ) );

                                            // Ítem de Dark Form (Posición 4, 6)
                                            ItemStack darkForm = new ItemStack ( 6183 ); // ID base
                                            ItemMeta darkFormMeta = darkForm.getItemMeta ( );
                                            darkFormMeta.setDisplayName ( CC.translate ( "&8&l??" ) );
                                            darkForm.setItemMeta ( darkFormMeta );
                                            inventoryContents.set ( 4, 6, ClickableItem.of ( darkForm, event -> {
                                                player.sendMessage ( CC.translate ( "&a¡Seleccionaste Dark Form!" ) );
                                            } ) );

                                            // Botón para volver (opcional)
                                            ItemStack back = new ItemStack ( Material.REDSTONE );
                                            ItemMeta backMeta = back.getItemMeta ( );
                                            backMeta.setDisplayName ( CC.translate ( "&c&l<- Volver al Menú Principal" ) );
                                            back.setItemMeta ( backMeta );
                                            inventoryContents.set ( 5, 4, ClickableItem.of ( back, event -> {
                                                player.closeInventory ( );
                                            } ) );
                                            ItemStack panel = new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) );
                                            ItemMeta panelMeta = panel.getItemMeta ( );
                                            panelMeta.setDisplayName ( " " ); // Nombre vacío
                                            panel.setItemMeta ( panelMeta );
                                            ClickableItem yellowPane = ClickableItem.empty ( panel );
                                            for (int row = 0; row < inventoryContents.inventory ( ).getRows ( ); row++) {
                                                for (int col = 0; col < 9; col++) {
                                                    // Rellena con el panel amarillo SOLO si la posición está vacía
                                                    if (!inventoryContents.get ( row, col ).isPresent ( )) {
                                                        inventoryContents.set ( row, col, yellowPane );
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void update ( Player player, InventoryContents inventoryContents ) {
                                            // Dejar vacío. El inventario es estático.
                                        }
                                    } ).build ( );

                            // Abre el menú
                            transformationsInventory.open ( player );
                            // === FIN DEL NUEVO MENÚ DE TRANSFORMACIONES ===

                        } ) );
                        ArrayList<String> loreVIP = new ArrayList<> ( );
                        loreVIP.add ( CC.translate ( "&b¡Compra rango &c&lVIP &ben nuestra tienda!" ) );
                        loreVIP.add ( CC.translate ( "&b¡Y accede a un menú exclusivo!" ) );
                        loreVIP.add ( CC.translate ( "&b¡Solo para jugadores con rango &cVIP&b!" ) );
                        ItemStack vip = new ItemStack ( 264, 1 );
                        ItemMeta vipMeta = vip.getItemMeta ( );
                        vipMeta.setLore ( loreVIP );
                        vipMeta.setDisplayName ( CC.translate ( "&6&l&k::&c&lVIP&6&l&k::" ) );
                        vip.setItemMeta ( vipMeta );
                        inventoryContents.set ( 3, 5, ClickableItem.of ( vip, e -> {
                            player.performCommand ( "vip" );
                        } ) );
                    }

                    @Override
                    public void update ( Player player, InventoryContents inventoryContents ) {
                        inventoryContents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                        inventoryContents.set ( 1, 1, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                        inventoryContents.set ( 4, 1, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                        inventoryContents.set ( 1, 7, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                        inventoryContents.set ( 4, 7, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                        inventoryContents.set ( 2, 1, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 2, 3, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 2, 5, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 2, 7, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 1, 2, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 1, 3, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 1, 4, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 1, 5, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 1, 6, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 3, 1, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 3, 2, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 3, 4, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 3, 6, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 3, 7, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 4, 2, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 4, 3, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 4, 4, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 4, 5, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                        inventoryContents.set ( 4, 6, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData ( ) ) ) );
                    }
                } )
                .build ( );
        INVENTORY.open ( command.getPlayer ( ) );
    }
}
