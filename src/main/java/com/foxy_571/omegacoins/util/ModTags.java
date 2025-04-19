package com.foxy_571.omegacoins.util;

import com.foxy_571.omegacoins.OmegaCoins;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> COIN_CRATES = createTag("coin_crates");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(OmegaCoins.MOD_ID, name));
        }
    }
}
