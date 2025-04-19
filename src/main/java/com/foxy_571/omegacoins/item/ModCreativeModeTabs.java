package com.foxy_571.omegacoins.item;

import com.foxy_571.omegacoins.OmegaCoins;
import com.foxy_571.omegacoins.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OmegaCoins.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> OMEGACOINS_TAB = CREATIVE_MODE_TABS.register("omegacoins_tab", () -> CreativeModeTab.builder()
        .icon(() -> new ItemStack(ModItems.IRON_COIN.get()))
        .title(Component.translatable("creativetab.omegacoins.omegacoins_tab"))
        .displayItems((itemDisplayParameters, output) -> {
            output.accept(ModItems.COPPER_COIN);
            output.accept(ModItems.IRON_COIN);
            output.accept(ModItems.GOLD_COIN);
            output.accept(ModItems.DIAMOND_COIN);
            output.accept(ModItems.NETHERITE_COIN);

            output.accept(ModBlocks.COPPER_COIN_CRATE);
            output.accept(ModBlocks.IRON_COIN_CRATE);
            output.accept(ModBlocks.GOLD_COIN_CRATE);
            output.accept(ModBlocks.DIAMOND_COIN_CRATE);
            output.accept(ModBlocks.NETHERITE_COIN_CRATE);
        }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
