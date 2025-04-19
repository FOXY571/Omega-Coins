package com.foxy_571.omegacoins;

import com.foxy_571.omegacoins.block.ModBlocks;
import com.foxy_571.omegacoins.block.entity.ModBlockEntities;
import com.foxy_571.omegacoins.block.entity.renderer.CoinPressBlockEntityRenderer;
import com.foxy_571.omegacoins.item.ModCreativeModeTabs;
import com.foxy_571.omegacoins.item.ModItems;
import com.foxy_571.omegacoins.recipes.ModRecipes;
import com.foxy_571.omegacoins.util.ModTags;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(OmegaCoins.MOD_ID)
public class OmegaCoins {
    public static final String MOD_ID = "omegacoins";
    public static final Logger LOGGER = LogUtils.getLogger();

    public OmegaCoins(IEventBus modEventBus, ModContainer modContainer) {
        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModRecipes.register(modEventBus);
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.COIN_PRESS.get(), CoinPressBlockEntityRenderer::new);
        }
    }
}
