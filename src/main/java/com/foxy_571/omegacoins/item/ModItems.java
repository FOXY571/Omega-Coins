package com.foxy_571.omegacoins.item;

import com.foxy_571.omegacoins.OmegaCoins;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(OmegaCoins.MOD_ID);

    public static final DeferredItem<Item> COPPER_COIN = ITEMS.register("copper_coin", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> IRON_COIN = ITEMS.register("iron_coin", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GOLD_COIN = ITEMS.register("gold_coin", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DIAMOND_COIN = ITEMS.register("diamond_coin", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> NETHERITE_COIN = ITEMS.register("netherite_coin", () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
