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
                                                                        inventoryContents.set ( row, col, ClickableItem.empty (
                                                                                new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) )
                                                                        ) );
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
                                                                        inventoryContents.set ( row, col, ClickableItem.empty (
                                                                                new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) )
                                                                        ) );
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
                                                                        inventoryContents.set ( row, col, ClickableItem.empty (
                                                                                new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) )
                                                                        ) );
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
                                                                        inventoryContents.set ( row, col, ClickableItem.empty (
                                                                                new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData ( ) )
                                                                        ) );
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
                                            ItemStack humanos = new ItemStack ( 6207 );
                                            ItemMeta humanosMeta = humanos.getItemMeta ( );
                                            humanosMeta.setDisplayName ( CC.translate ( "&6Templo de tenshinhan" ) );
                                            humanos.setItemMeta ( humanosMeta );
                                            inventoryContents.set ( 1, 1, ClickableItem.of ( humanos, ( event ) -> {
                                                player.performCommand ( "warp humanos" );
                                            } ) );

                                            ItemStack arcosianos = new ItemStack ( 6219 );
                                            ItemMeta arcosianosMeta = arcosianos.getItemMeta ( );
                                            arcosianosMeta.setDisplayName ( CC.translate ( "&5Nave de Freezer" ) );
                                            arcosianos.setItemMeta ( arcosianosMeta );
                                            inventoryContents.set ( 1, 3, ClickableItem.of ( arcosianos, ( event ) -> {
                                                player.performCommand ( "warp arcosianos" );
                                            } ) );

                                            ItemStack saiyans = new ItemStack ( 6208 );
                                            ItemMeta saiyansMeta = saiyans.getItemMeta ( );
                                            saiyansMeta.setDisplayName ( CC.translate ( "&eTorneo" ) );
                                            saiyans.setItemMeta ( saiyansMeta );
                                            inventoryContents.set ( 3, 1, ClickableItem.of ( saiyans, ( event ) -> {
                                                player.performCommand ( "warp torneo" );
                                            } ) );

                                            ItemStack Majin = new ItemStack ( 6190 );
                                            ItemMeta MajinMeta = Majin.getItemMeta ( );
                                            MajinMeta.setDisplayName ( CC.translate ( "&dMajin" ) );
                                            Majin.setItemMeta ( MajinMeta );
                                            inventoryContents.set ( 3, 3, ClickableItem.of ( Majin, ( event ) -> {
                                                player.performCommand ( "warp CiudadSatan" );
                                            } ) );

                                            ItemStack Namekianos = new ItemStack ( 6187 );
                                            ItemMeta NamekianosMeta = Namekianos.getItemMeta ( );
                                            NamekianosMeta.setDisplayName ( CC.translate ( "&2Corporacion capsula" ) );
                                            Namekianos.setItemMeta ( NamekianosMeta );
                                            inventoryContents.set ( 2, 5, ClickableItem.of ( Namekianos, ( event ) -> {
                                                player.performCommand ( "warp corporacionCapsula" );
                                            } ) );

                                            ItemStack Kaioken = new ItemStack ( 6192 );
                                            ItemMeta KaiokenMeta = Kaioken.getItemMeta ( );
                                            KaiokenMeta.setDisplayName ( CC.translate ( "&8[&4Kaioken&8]" ) );
                                            Kaioken.setItemMeta ( KaiokenMeta );
                                            inventoryContents.set ( 1, 7, ClickableItem.of ( Kaioken, ( event ) -> {
                                                player.performCommand ( "warp kaioken" );

                                            } ) );

                                            ItemStack Mistico = new ItemStack ( 6210 );
                                            ItemMeta MisticoMeta = Mistico.getItemMeta ( );
                                            MisticoMeta.setDisplayName ( CC.translate ( "&8[&7Raciales&8]" ) );
                                            Mistico.setItemMeta ( MisticoMeta );
                                            inventoryContents.set ( 3, 7, ClickableItem.of ( Mistico, ( event ) -> {
                                                player.performCommand ( "warp raciales" );

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
                        ItemStack tienda = new ItemStack ( 4407, 1 );
                        ItemMeta tiendaMeta = tienda.getItemMeta ( );
                        tiendaMeta.setDisplayName ( CC.translate ( "&6&lTienda" ) );
                        tienda.setItemMeta ( tiendaMeta );
                        inventoryContents.set ( 2, 6, ClickableItem.of ( tienda, e -> {
                            player.sendMessage ( CC.translate ( "&e[&bTienda&e]  &chttps://dbzenkai.tip4serv.com/" ) );
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
