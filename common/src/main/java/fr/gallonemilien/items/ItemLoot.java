package fr.gallonemilien.items;

import dev.architectury.event.events.common.LootEvent;
import dev.architectury.registry.registries.RegistrySupplier;
import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.config.ModConfig;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;

/**
 * Manages the injection of mod items into vanilla loot tables and villager trades.
 */
public class ItemLoot {

    /**
     * Registers all loot modifications.
     */
    public static void register() {
        registerIron();
        registerGold();
        registerDiamond();
        registerNetherite();
        registerLootTable();
    }

    private static void registerIron() {
        registerVillager(VillagerProfession.ARMORER, 1, 12, DopedHorsesItems.IRON_HORSE_SHOES, 12, 5, 0.05f);
    }

    private static void registerGold() {
        registerVillager(VillagerProfession.ARMORER, 3, 19, DopedHorsesItems.GOLD_HORSE_SHOES, 12, 8, 0.05f);
    }

    private static void registerDiamond() {
        registerVillager(VillagerProfession.ARMORER, 4, 31, DopedHorsesItems.DIAMOND_HORSE_SHOES, 12, 10, 0.05f);
    }

    private static void registerNetherite() {
        // Currently no villager trade for Netherite shoes.
    }

    /**
     * Registers a listener to modify vanilla loot tables, adding horse shoes to various Nether chests.
     */
    private static void registerLootTable() {
        LootEvent.MODIFY_LOOT_TABLE.register((lootTableId, context, builtin) -> {
            // A list of Nether chest loot tables to target.
            List<String> netherLootTables = List.of(
                    "minecraft:chests/nether_bridge",
                    "minecraft:chests/bastion_treasure",
                    "minecraft:chests/bastion_other",
                    "minecraft:chests/bastion_bridge",
                    "minecraft:chests/ruined_portal"
            );

            if (builtin && netherLootTables.stream().anyMatch(id -> lootTableId.toString().equals(id))) {
                ModConfig config = DopedHorses.getConfig();
                LootPool.Builder pool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(DopedHorsesItems.IRON_HORSE_SHOES.get())
                                .when(LootItemRandomChanceCondition.randomChance(config.getShoeLoot(ShoeType.IRON))))
                        .add(LootItem.lootTableItem(DopedHorsesItems.GOLD_HORSE_SHOES.get())
                                .when(LootItemRandomChanceCondition.randomChance(config.getShoeLoot(ShoeType.GOLD))))
                        .add(LootItem.lootTableItem(DopedHorsesItems.DIAMOND_HORSE_SHOES.get())
                                .when(LootItemRandomChanceCondition.randomChance(config.getShoeLoot(ShoeType.DIAMOND))))
                        .add(LootItem.lootTableItem(DopedHorsesItems.NETHERITE_HORSE_SHOES.get())
                                .when(LootItemRandomChanceCondition.randomChance(config.getShoeLoot(ShoeType.NETHERITE))))
                        .setRolls(ConstantValue.exactly(1))
                        .setBonusRolls(ConstantValue.exactly(0));

                context.addPool(pool);
            }
        });
    }

    /**
     * A placeholder for registering villager trades.
     * The functionality is currently disabled and marked as a TODO for a future update.
     *
     * @param profession The villager profession to trade with.
     * @param level      The villager level required for the trade.
     * @param price      The emerald cost of the item.
     * @param item       The item to be sold.
     * @param maxUses    The maximum number of times the trade can be used.
     * @param experience The experience granted to the villager upon trade.
     * @param multiplier The price multiplier.
     */
    private static void registerVillager(ResourceKey<VillagerProfession> profession, int level, int price,
                                         RegistrySupplier<ShoeItem> item, int maxUses, int experience, float multiplier) {
        // TODO: Fix villager trades in a future update. The Architectury TradeRegistry API seems to have issues.
        /*
        TradeRegistry.registerVillagerTrade(
                profession,
                level,
                (entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, price),
                    item.get().getDefaultInstance(),
                    maxUses,
                    experience,
                    multiplier
                )
        );
        */
    }
}
