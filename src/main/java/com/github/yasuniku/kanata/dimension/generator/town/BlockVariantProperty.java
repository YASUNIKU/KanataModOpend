package com.github.yasuniku.kanata.dimension.generator.town;

import com.github.yasuniku.kanata.registry.KanataBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public record BlockVariantProperty(String name, Block fullBlock, Block stair, Block half, Block floor, Block damaged) {
    public static final List<BlockVariantProperty> BUILDING_MATERIALS_VARIANT = List.of(
        new BlockVariantProperty(
            "reinforced_concrete",
            KanataBlock.REINFORCED_CONCRETE_BLOCK.get(),
            KanataBlock.REINFORCED_CONCRETE_STAIRS.get(),
            KanataBlock.REINFORCED_CONCRETE_SLAB.get(),
            KanataBlock.REINFORCED_CONCRETE_FLOOR_BLOCK.get(),
            KanataBlock.CRUMBLING_REINFORCED_CONCRETE_BLOCK.get()
        ),
        new BlockVariantProperty("brick",
            Blocks.BRICKS,
            Blocks.BRICK_STAIRS,
            Blocks.BRICK_SLAB,
            Blocks.BRICKS,
            Blocks.BRICKS
        ),
        new BlockVariantProperty(
            "smooth_stone",
            Blocks.SMOOTH_STONE,
            Blocks.STONE_STAIRS,
            Blocks.SMOOTH_STONE_SLAB,
            Blocks.SMOOTH_STONE,
            Blocks.SMOOTH_STONE
        ),
        new BlockVariantProperty(
            "polished_andesite",
            Blocks.POLISHED_ANDESITE,
            Blocks.POLISHED_ANDESITE_STAIRS,
            Blocks.POLISHED_ANDESITE_SLAB,
            Blocks.POLISHED_ANDESITE,
            Blocks.POLISHED_ANDESITE
        ),
        new BlockVariantProperty(
            "deepslate_bricks",
            Blocks.DEEPSLATE_BRICKS,
            Blocks.DEEPSLATE_BRICK_STAIRS,
            Blocks.DEEPSLATE_BRICK_SLAB,
            Blocks.DEEPSLATE_BRICKS,
            Blocks.DEEPSLATE_BRICKS
        ),
        new BlockVariantProperty(
            "stone_bricks",
            Blocks.STONE_BRICKS,
            Blocks.STONE_BRICK_STAIRS,
            Blocks.STONE_BRICK_SLAB,
            Blocks.STONE_BRICKS,
            Blocks.STONE_BRICKS
        )
    );
}
