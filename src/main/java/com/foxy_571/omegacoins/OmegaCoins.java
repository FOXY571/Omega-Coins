package com.foxy_571.omegacoins;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(OmegaCoins.MOD_ID)
public class OmegaCoins {
    public static final String MOD_ID = "omegacoins";
    private static final Logger LOGGER = LogUtils.getLogger();

    public OmegaCoins(IEventBus modEventBus, ModContainer modContainer) {

    }
}
