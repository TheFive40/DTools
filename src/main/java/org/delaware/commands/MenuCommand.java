package org.delaware.commands;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
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

public class MenuCommand extends BaseCommand {
    @Command(name = "zenkai", aliases = {"zenkai", "zenkai"})
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
                        trainingMeta.setDisplayName ( CC.translate ( "&4&lSistema de Trainings" ) );
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
                            SmartInventory selectTrainingType = SmartInventory.builder ( ).title ( CC.translate ( "&c&lSistema de Trainings" ) )
                                    .type ( InventoryType.CHEST )
                                    .provider ( new InventoryProvider ( ) {
                                        @Override
                                        public void init ( Player player, InventoryContents inventoryContents ) {
                                            inventoryContents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                                            ItemStack tWarriors = new ItemStack ( 176 );
                                            ItemMeta t1Meta = tWarriors.getItemMeta ( );
                                            t1Meta.setDisplayName ( CC.translate ( "&4Guerreros" ) );
                                            tWarriors.setItemMeta ( t1Meta );
                                            inventoryContents.set ( 1, 3, ClickableItem.of ( tWarriors, e -> {
                                                SmartInventory trainingInventory = SmartInventory.builder ( ).title ( CC.translate ( "&c&lSistema de Trainings" ) )
                                                        .type ( InventoryType.CHEST )
                                                        .provider ( new InventoryProvider ( ) {
                                                            @Override
                                                            public void init ( Player player, InventoryContents inventoryContents ) {
                                                                inventoryContents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                                                                for (int row = 1; row <= 4; row++) {
                                                                    for (int col = 1; col <= 7; col++) {
                                                                        if (row == 2 && col == 2 || row == 2 && col == 4 || row == 2 && col == 6 || row == 4 && col == 4) {
                                                                            continue;
                                                                        }
                                                                        inventoryContents.set(row, col, ClickableItem.empty(
                                                                                new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData())
                                                                        ));
                                                                    }
                                                                }
                                                                ItemStack t1 = new ItemStack ( 4977 );
                                                                ItemMeta t1Meta = t1.getItemMeta ( );
                                                                t1Meta.setDisplayName ( CC.translate ( "&4Training 1" ) );
                                                                t1.setItemMeta ( t1Meta );
                                                                inventoryContents.set ( 2, 2, ClickableItem.of ( t1, e -> {
                                                                    player.performCommand ( "warp t1" );
                                                                } ) );
                                                                ItemStack t2 = new ItemStack ( 4975 );
                                                                ItemMeta t2Meta = t2.getItemMeta ( );
                                                                t2Meta.setDisplayName ( CC.translate ( "&4Training 2" ) );
                                                                t2.setItemMeta ( t2Meta );
                                                                inventoryContents.set ( 2, 4, ClickableItem.of ( t2, e -> {
                                                                    player.performCommand ( "warp t2" );

                                                                } ) );
                                                                ItemStack t3 = new ItemStack ( 4973 );
                                                                ItemMeta t3Meta = t3.getItemMeta ( );
                                                                t3Meta.setDisplayName ( CC.translate ( "&4Training 3" ) );
                                                                t3.setItemMeta ( t3Meta );
                                                                inventoryContents.set ( 2, 6, ClickableItem.of ( t3, e -> {
                                                                    player.performCommand ( "warp t3" );

                                                                } ) );
                                                                ItemStack t4 = new ItemStack ( 4960 );
                                                                ItemMeta t4Meta = t4.getItemMeta ( );
                                                                t4Meta.setDisplayName ( CC.translate ( "&4Training 4" ) );
                                                                t4.setItemMeta ( t4Meta );
                                                                inventoryContents.set ( 4, 4, ClickableItem.of ( t4, e -> {
                                                                    player.performCommand ( "warp t4" );
                                                                } ) );
                                                            }

                                                            @Override
                                                            public void update ( Player player, InventoryContents inventoryContents ) {
                                                                inventoryContents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                                                                for (int row = 1; row <= 4; row++) {
                                                                    for (int col = 1; col <= 7; col++) {
                                                                        if (row == 2 && col == 2 || row == 2 && col == 4 || row == 2 && col == 6 || row == 4 && col == 4) {
                                                                            continue;
                                                                        }
                                                                        inventoryContents.set(row, col, ClickableItem.empty(
                                                                                new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData())
                                                                        ));
                                                                    }
                                                                }
                                                            }
                                                        } ).id ( "trainings" )
                                                        .size ( 6, 9 ).build ( );
                                                trainingInventory.open ( player );
                                            } ) );
                                            ItemStack tSpiritualist = new ItemStack ( 4142 );
                                            ItemMeta t2Meta = tSpiritualist.getItemMeta ( );
                                            t2Meta.setDisplayName ( CC.translate ( "&bEspiritualistas" ) );
                                            tSpiritualist.setItemMeta ( t2Meta );
                                            inventoryContents.set ( 1, 5, ClickableItem.of ( tSpiritualist, e -> {
                                                SmartInventory trainingInventory = SmartInventory.builder ( ).title ( CC.translate ( "&c&lSistema de Trainings" ) )
                                                        .type ( InventoryType.CHEST )
                                                        .provider ( new InventoryProvider ( ) {
                                                            @Override
                                                            public void init ( Player player, InventoryContents inventoryContents ) {
                                                                inventoryContents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                                                                for (int row = 1; row <= 4; row++) {
                                                                    for (int col = 1; col <= 7; col++) {
                                                                        if (row == 2 && col == 2 || row == 2 && col == 4 || row == 2 && col == 6 || row == 4 && col == 4) {
                                                                            continue;
                                                                        }
                                                                        inventoryContents.set(row, col, ClickableItem.empty(
                                                                                new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData())
                                                                        ));
                                                                    }
                                                                }
                                                                ItemStack t1 = new ItemStack ( 4977 );
                                                                ItemMeta t1Meta = t1.getItemMeta ( );
                                                                t1Meta.setDisplayName ( CC.translate ( "&4Training 1" ) );
                                                                t1.setItemMeta ( t1Meta );
                                                                inventoryContents.set ( 2, 2, ClickableItem.of ( t1, e -> {
                                                                    player.performCommand ( "warp t1e" );
                                                                } ) );
                                                                ItemStack t2 = new ItemStack ( 4975 );
                                                                ItemMeta t2Meta = t2.getItemMeta ( );
                                                                t2Meta.setDisplayName ( CC.translate ( "&4Training 2" ) );
                                                                t2.setItemMeta ( t2Meta );
                                                                inventoryContents.set ( 2, 4, ClickableItem.of ( t2, e -> {
                                                                    player.performCommand ( "warp t2e" );

                                                                } ) );
                                                                ItemStack t3 = new ItemStack ( 4973 );
                                                                ItemMeta t3Meta = t3.getItemMeta ( );
                                                                t3Meta.setDisplayName ( CC.translate ( "&4Training 3" ) );
                                                                t3.setItemMeta ( t3Meta );
                                                                inventoryContents.set ( 2, 6, ClickableItem.of ( t3, e -> {
                                                                    player.performCommand ( "warp t3e" );

                                                                } ) );
                                                                ItemStack t4 = new ItemStack ( 4960 );
                                                                ItemMeta t4Meta = t4.getItemMeta ( );
                                                                t4Meta.setDisplayName ( CC.translate ( "&4Training 4" ) );
                                                                t4.setItemMeta ( t4Meta );
                                                                inventoryContents.set ( 4, 4, ClickableItem.of ( t4, e -> {
                                                                    player.performCommand ( "warp t4e" );
                                                                } ) );
                                                            }

                                                            @Override
                                                            public void update ( Player player, InventoryContents inventoryContents ) {
                                                                inventoryContents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                                                                for (int row = 1; row <= 4; row++) {
                                                                    for (int col = 1; col <= 7; col++) {
                                                                        if (row == 2 && col == 2 || row == 2 && col == 4 || row == 2 && col == 6 || row == 4 && col == 4) {
                                                                            continue;
                                                                        }
                                                                        inventoryContents.set(row, col, ClickableItem.empty(
                                                                                new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData())
                                                                        ));
                                                                    }
                                                                }
                                                            }
                                                        } ).id ( "trainingsE" )
                                                        .size ( 6, 9 ).build ( );
                                                trainingInventory.open ( player );
                                            } ) );
                                        }

                                        @Override
                                        public void update ( Player player, InventoryContents inventoryContents ) {
                                            inventoryContents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                                        }
                                    } ).id ( "selectTraining" )
                                    .size ( 3, 9 ).build ( );
                            selectTrainingType.open ( player );

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
                                            inventoryContents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData ( ) ) ) );
                                            ItemStack eventos = new ItemStack ( 4427 );
                                            ItemMeta eventosItemMeta = eventos.getItemMeta ( );
                                            eventosItemMeta.setDisplayName ( CC.translate ( "&6Eventos" ) );
                                            eventos.setItemMeta ( eventosItemMeta );
                                            inventoryContents.set ( 1, 2, ClickableItem.of ( eventos, e -> {
                                                player.performCommand ( "warp eventos" );
                                            } ) );
                                            ItemStack factions = new ItemStack ( 4144 );
                                            ItemMeta factionsItemMeta = factions.getItemMeta ( );
                                            factionsItemMeta.setDisplayName ( CC.translate ( "&cFactions" ) );
                                            factions.setItemMeta ( factionsItemMeta );
                                            inventoryContents.set ( 1, 4, ClickableItem.of ( factions, e -> {
                                                player.performCommand ( "warp factions" );

                                            } ) );
                                            ItemStack planetaVegeta = new ItemStack ( 4422 );
                                            ItemMeta planetaVegetaItemMeta = planetaVegeta.getItemMeta ( );
                                            planetaVegetaItemMeta.setDisplayName ( CC.translate ( "&4Planeta Vegeta" ) );
                                            planetaVegeta.setItemMeta ( planetaVegetaItemMeta );
                                            inventoryContents.set ( 1, 6, ClickableItem.of ( planetaVegeta, e -> {
                                                player.performCommand ( "warp planetavegeta" );
                                            } ) );
                                            ItemStack tienda = new ItemStack ( 4444 );
                                            ItemMeta tiendaItemMeta = tienda.getItemMeta ( );
                                            tiendaItemMeta.setDisplayName ( CC.translate ( "&2Tienda" ) );
                                            tienda.setItemMeta ( tiendaItemMeta );
                                            inventoryContents.set ( 2, 2, ClickableItem.of ( tienda, e -> {
                                                player.performCommand ( "warp tienda" );

                                            } ) );
                                            ItemStack talubias = new ItemStack ( 4461 );
                                            ItemMeta talubiasItemMeta = talubias.getItemMeta ( );
                                            talubiasItemMeta.setDisplayName ( CC.translate ( "&eTraining Alubias" ) );
                                            talubias.setItemMeta ( talubiasItemMeta );
                                            inventoryContents.set ( 2, 4, ClickableItem.of ( talubias, e -> {
                                                player.performCommand ( "warp talubias" );

                                            } ) );
                                            ItemStack parcelas = new ItemStack ( 44 );
                                            ItemMeta parcelasItemMeta = parcelas.getItemMeta ( );
                                            parcelasItemMeta.setDisplayName ( CC.translate ( "&fCapital del Oeste" ) );
                                            parcelas.setItemMeta ( parcelasItemMeta );
                                            inventoryContents.set ( 2, 6, ClickableItem.of ( parcelas, e -> {
                                                player.performCommand ( "warp capital" );
                                            } ) );


                                            ItemStack banco = new ItemStack ( 4407 );
                                            ItemMeta bancoItemMeta = banco.getItemMeta ( );
                                            bancoItemMeta.setDisplayName ( CC.translate ( "&bBanco" ) );
                                            banco.setItemMeta ( bancoItemMeta );
                                            inventoryContents.set ( 3, 2, ClickableItem.of ( banco, e -> {
                                                player.performCommand ( "warp banco" );

                                            } ) );
                                            ItemStack recursos = new ItemStack ( 4687 );
                                            ItemMeta recursosItemMeta = recursos.getItemMeta ( );
                                            recursosItemMeta.setDisplayName ( CC.translate ( "&dRecursos" ) );
                                            recursos.setItemMeta ( recursosItemMeta );
                                            inventoryContents.set ( 3, 4, ClickableItem.of ( recursos, e -> {
                                                player.performCommand ( "warp recursos" );

                                            } ) );
                                            ItemStack ritual = new ItemStack ( 4142 );
                                            ItemMeta ritualItemMeta = ritual.getItemMeta ( );
                                            ritualItemMeta.setDisplayName ( CC.translate ( "&eRitual &cSSG" ) );
                                            ritual.setItemMeta ( ritualItemMeta );
                                            inventoryContents.set ( 3, 6, ClickableItem.of ( ritual, e -> {
                                                player.performCommand ( "warp ritual" );
                                            } ) );


                                            ItemStack torneo = new ItemStack ( 4419 );
                                            ItemMeta torneoItemMeta = torneo.getItemMeta ( );
                                            torneoItemMeta.setDisplayName ( CC.translate ( "&cTorneo" ) );
                                            torneo.setItemMeta ( torneoItemMeta );
                                            inventoryContents.set ( 4, 2, ClickableItem.of ( torneo, e -> {
                                                player.performCommand ( "warp torneo" );

                                            } ) );
                                            ItemStack reglas = new ItemStack ( 4411 );
                                            ItemMeta reglasItemMeta = reglas.getItemMeta ( );
                                            reglasItemMeta.setDisplayName ( CC.translate ( "&7Reglas" ) );
                                            reglas.setItemMeta ( reglasItemMeta );
                                            inventoryContents.set ( 4, 4, ClickableItem.of ( reglas, e -> {
                                                player.performCommand ( "warp reglas" );

                                            } ) );
                                            ItemStack staffs = new ItemStack ( 4108 );
                                            ItemMeta staffsItemMeta = staffs.getItemMeta ( );
                                            staffsItemMeta.setDisplayName ( CC.translate ( "&4Staffs" ) );
                                            staffs.setItemMeta ( staffsItemMeta );
                                            inventoryContents.set ( 4, 6, ClickableItem.of ( staffs, e -> {
                                                player.performCommand ( "warp staffs" );
                                            } ) );
                                        }

                                        @Override
                                        public void update ( Player player, InventoryContents inventoryContents ) {
                                            inventoryContents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData ( ) ) ) );
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
                                            inventoryContents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.CYAN.getData ( ) ) ) );
                                            ItemStack patriarca = new ItemStack ( 724 );
                                            ItemMeta patriarcaItemMeta = patriarca.getItemMeta ( );
                                            patriarcaItemMeta.setDisplayName ( CC.translate ( "&aGran Patriarca" ) );
                                            patriarca.setItemMeta ( patriarcaItemMeta );
                                            inventoryContents.set ( 1, 2, ClickableItem.of ( patriarca, e -> {
                                                player.performCommand ( "warp patriarca" );
                                            } ) );
                                            ItemStack vegeta = new ItemStack ( 720 );
                                            ItemMeta vegetaM = vegeta.getItemMeta ( );
                                            vegetaM.setDisplayName ( CC.translate ( "&bVegeta" ) );
                                            vegeta.setItemMeta ( vegetaM );
                                            inventoryContents.set ( 1, 4, ClickableItem.of ( vegeta, e -> {
                                                player.performCommand ( "warp vegeta" );

                                            } ) );
                                            ItemStack kami = new ItemStack ( 4460 );
                                            ItemMeta kamiItemMeta = kami.getItemMeta ( );
                                            kamiItemMeta.setDisplayName ( CC.translate ( "&2Kami sama" ) );
                                            kami.setItemMeta ( kamiItemMeta );
                                            inventoryContents.set ( 1, 6, ClickableItem.of ( kami, e -> {
                                                player.performCommand ( "warp kami" );

                                            } ) );
                                            ItemStack kaio = new ItemStack ( 4457 );
                                            ItemMeta kaioItemMeta = kaio.getItemMeta ( );
                                            kaioItemMeta.setDisplayName ( CC.translate ( "&eKaio" ) );
                                            kaio.setItemMeta ( kaioItemMeta );
                                            inventoryContents.set ( 2, 2, ClickableItem.of ( kaio, e -> {
                                                player.performCommand ( "warp kaio" );

                                            } ) );
                                            ItemStack freezer = new ItemStack ( 4695 );
                                            ItemMeta freezerItemMeta = freezer.getItemMeta ( );
                                            freezerItemMeta.setDisplayName ( CC.translate ( "&4Freezer" ) );
                                            freezer.setItemMeta ( freezerItemMeta );
                                            inventoryContents.set ( 2, 4, ClickableItem.of ( freezer, e -> {
                                                player.performCommand ( "warp freezer" );

                                            } ) );
                                            ItemStack goku = new ItemStack ( 4456 );
                                            ItemMeta gokuItemMeta = goku.getItemMeta ( );
                                            gokuItemMeta.setDisplayName ( CC.translate ( "&6Goku" ) );
                                            goku.setItemMeta ( gokuItemMeta );
                                            inventoryContents.set ( 2, 6, ClickableItem.of ( goku, e -> {
                                                player.performCommand ( "warp goku" );
                                            } ) );


                                            ItemStack babidi = new ItemStack ( 4458 );
                                            ItemMeta babidiItemMeta = babidi.getItemMeta ( );
                                            babidiItemMeta.setDisplayName ( CC.translate ( "&9Babidi" ) );
                                            babidi.setItemMeta ( babidiItemMeta );
                                            inventoryContents.set ( 3, 2, ClickableItem.of ( babidi, e -> {
                                                player.performCommand ( "warp babidi" );

                                            } ) );
                                            ItemStack cell = new ItemStack ( 4462 );
                                            ItemMeta cellItemMeta = cell.getItemMeta ( );
                                            cellItemMeta.setDisplayName ( CC.translate ( "&2Cell" ) );
                                            cell.setItemMeta ( cellItemMeta );
                                            inventoryContents.set ( 3, 4, ClickableItem.of ( cell, e -> {
                                                player.performCommand ( "warp cell" );

                                            } ) );
                                            ItemStack roshi = new ItemStack ( 4463 );
                                            ItemMeta roshiItemMeta = roshi.getItemMeta ( );
                                            roshiItemMeta.setDisplayName ( CC.translate ( "&dRoshi" ) );
                                            roshi.setItemMeta ( roshiItemMeta );
                                            inventoryContents.set ( 3, 6, ClickableItem.of ( roshi, e -> {
                                                player.performCommand ( "warp roshi" );
                                            } ) );


                                            ItemStack rocky = new ItemStack ( 696 );
                                            ItemMeta rockyItemMeta = rocky.getItemMeta ( );
                                            rockyItemMeta.setDisplayName ( CC.translate ( "&6Rocky" ) );
                                            rocky.setItemMeta ( rockyItemMeta );
                                            inventoryContents.set ( 4, 2, ClickableItem.of ( rocky, e -> {
                                                player.performCommand ( "warp rocky" );

                                            } ) );
                                            ItemStack enma = new ItemStack ( 759 );
                                            ItemMeta enmaItemMeta = enma.getItemMeta ( );
                                            enmaItemMeta.setDisplayName ( CC.translate ( "&5Enma" ) );
                                            enma.setItemMeta ( enmaItemMeta );
                                            inventoryContents.set ( 4, 4, ClickableItem.of ( enma, e -> {
                                                player.performCommand ( "warp enma" );

                                            } ) );
                                            ItemStack whis = new ItemStack ( 4629 );
                                            ItemMeta whisItemMeta = whis.getItemMeta ( );
                                            whisItemMeta.setDisplayName ( CC.translate ( "&fWhis" ) );
                                            whis.setItemMeta ( whisItemMeta );
                                            inventoryContents.set ( 4, 6, ClickableItem.of ( whis, e -> {
                                                player.performCommand ( "warp whis" );
                                            } ) );
                                        }

                                        @Override
                                        public void update ( Player player, InventoryContents inventoryContents ) {
                                            inventoryContents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.CYAN.getData ( ) ) ) );
                                            for (int col = 0; col <= 8; col++) {
                                                inventoryContents.set ( 0, col, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                                            }
                                            inventoryContents.set ( 1, 0, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                                            inventoryContents.set ( 1, 8, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                                            inventoryContents.set ( 2, 0, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                                            inventoryContents.set ( 2, 8, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                                            inventoryContents.set ( 3, 0, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                                            inventoryContents.set ( 3, 8, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                                            inventoryContents.set ( 4, 0, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                                            inventoryContents.set ( 4, 8, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                                            for (int col = 0; col <= 8; col++) {
                                                inventoryContents.set ( 5, col, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                                            }

                                        }
                                    } )
                                    .build ( );
                            dbcInventory.open ( player );
                        } ) );
                        ArrayList<String> loreTienda = new ArrayList<> ( );
                        loreTienda.add ( CC.translate ( "&cProximamente" ) );
                        ItemStack tienda = new ItemStack ( 4407, 1 );
                        ItemMeta tiendaMeta = tienda.getItemMeta ( );
                        tiendaMeta.setLore ( loreTienda );
                        tiendaMeta.setDisplayName ( CC.translate ( "&6&lTienda" ) );
                        tienda.setItemMeta ( tiendaMeta );
                        inventoryContents.set ( 2, 6, ClickableItem.of ( tienda, e -> {
                            player.sendMessage ( CC.translate ( "&cEstamos trabajando en eso..." ) );
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
