package fr.gallonemilien.items;

import dev.architectury.event.events.common.LootEvent;
import dev.architectury.registry.level.entity.trade.TradeRegistry;
import fr.gallonemilien.DopedHorses;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;

public class ItemLoot {

    public static void register() {
        registerIron();
        registerGold();
        registerDiamond();
        registerNetherite();
        registerLootTable();
    }

    private static void registerIron() {
        registerVillager(VillagerProfession.ARMORER,1,12, DopedHorsesItems.IRON_HORSE_SHOES.get(), 12, 5, 0.05f);
    }

    private static void registerGold() {
        registerVillager(VillagerProfession.ARMORER,3,19, DopedHorsesItems.GOLD_HORSE_SHOES.get(), 12, 8, 0.05f);
    }

    private static void registerDiamond() {
        registerVillager(VillagerProfession.ARMORER,4,31, DopedHorsesItems.GOLD_HORSE_SHOES.get(), 12, 10, 0.05f);
    }

    private static void registerNetherite() {

    }

    public static void registerLootTable() {
        LootEvent.MODIFY_LOOT_TABLE.register((lootTableId, context, builtin) -> {
            // Liste des loot tables des coffres du Nether
            List<String> netherLootTables = List.of(
                    "minecraft:chests/nether_bridge",
                    "minecraft:chests/bastion_treasure",
                    "minecraft:chests/bastion_other",
                    "minecraft:chests/bastion_bridge",
                    "minecraft:chests/ruined_portal"
            );
            if(builtin) {
                netherLootTables.forEach(netherLootTable -> {
                    if(lootTableId.toString().contains(netherLootTable)) {
                        LootPool.Builder pool = LootPool.lootPool()
                                .add(LootItem.lootTableItem(DopedHorsesItems.IRON_HORSE_SHOES.get())
                                        .when(LootItemRandomChanceCondition.randomChance(DopedHorses.MOD_CONFIG.getShoeLoot(ShoeType.IRON))))
                                .add(LootItem.lootTableItem(DopedHorsesItems.GOLD_HORSE_SHOES.get())
                                        .when(LootItemRandomChanceCondition.randomChance(DopedHorses.MOD_CONFIG.getShoeLoot(ShoeType.GOLD))))
                                .add(LootItem.lootTableItem(DopedHorsesItems.DIAMOND_HORSE_SHOES.get())
                                        .when(LootItemRandomChanceCondition.randomChance(DopedHorses.MOD_CONFIG.getShoeLoot(ShoeType.DIAMOND))))
                                .add(LootItem.lootTableItem(DopedHorsesItems.NETHERITE_HORSE_SHOES.get())
                                        .when(LootItemRandomChanceCondition.randomChance(DopedHorses.MOD_CONFIG.getShoeLoot(ShoeType.NETHERITE))))
                                .setRolls(ConstantValue.exactly(1)) // 1 seul tirage
                                .setBonusRolls(ConstantValue.exactly(0)); // Pas de tirage supplémentaire

                        context.addPool(pool);
                    }
                });
            }
        });
    }

    public static void registerVillager(VillagerProfession profession, int lvl, int price, Item item, int maxUses, int experience, float multiplier) {
        TradeRegistry.registerVillagerTrade(
                profession,
                lvl,
                (entity, randomSource) ->
             new MerchantOffer(
                new ItemCost(Items.EMERALD, price),
                new ItemStack(item, 1),
                maxUses,
                experience,
                multiplier
             ));
    }
}
