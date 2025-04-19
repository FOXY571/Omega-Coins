package com.foxy_571.omegacoins.block.entity;

import com.foxy_571.omegacoins.OmegaCoins;
import com.foxy_571.omegacoins.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, OmegaCoins.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CoinPressBlockEntity>> COIN_PRESS = BLOCK_ENTITIES.register("coin_press", () -> BlockEntityType.Builder.of(CoinPressBlockEntity::new, ModBlocks.COIN_PRESS.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
