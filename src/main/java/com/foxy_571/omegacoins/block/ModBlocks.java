package com.foxy_571.omegacoins.block;

import com.foxy_571.omegacoins.OmegaCoins;
import com.foxy_571.omegacoins.block.custom.CoinPressBlock;
import com.foxy_571.omegacoins.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(OmegaCoins.MOD_ID);

    public static final DeferredBlock<Block> COPPER_COIN_CRATE =  registerBlock("copper_coin_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> IRON_COIN_CRATE =  registerBlock("iron_coin_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> GOLD_COIN_CRATE =  registerBlock("gold_coin_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> DIAMOND_COIN_CRATE =  registerBlock("diamond_coin_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> NETHERITE_COIN_CRATE =  registerBlock("netherite_coin_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)));
    public static final DeferredBlock<CoinPressBlock> COIN_PRESS = registerBlock("coin_press", () -> new CoinPressBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.STONE)));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
