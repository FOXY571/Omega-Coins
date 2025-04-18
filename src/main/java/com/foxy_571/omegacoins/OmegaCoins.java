package com.foxy_571.omegacoins;

import com.foxy_571.omegacoins.item.ModCreativeModeTabs;
import com.foxy_571.omegacoins.item.ModItems;
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
    }
}
