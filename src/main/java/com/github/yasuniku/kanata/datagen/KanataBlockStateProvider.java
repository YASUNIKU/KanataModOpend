package com.github.yasuniku.kanata.datagen;

import com.github.yasuniku.kanata.registry.KanataBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import static com.github.yasuniku.kanata.KanataMod.MOD_ID;

public class KanataBlockStateProvider extends BlockStateProvider {

    public KanataBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        //region 強化コンクリート
        generateCube(KanataBlock.REINFORCED_CONCRETE_BLOCK.get());
        generateStairs(
            KanataBlock.REINFORCED_CONCRETE_STAIRS.get(),
            blockTexture(KanataBlock.REINFORCED_CONCRETE_BLOCK.get())
        );
        generateSlab(
            KanataBlock.REINFORCED_CONCRETE_SLAB.get(),
            blockTexture(KanataBlock.REINFORCED_CONCRETE_BLOCK.get())
        );
        generateWall(
            KanataBlock.REINFORCED_CONCRETE_WALL.get(),
            blockTexture(KanataBlock.REINFORCED_CONCRETE_BLOCK.get())
        );
        generatePane(
            KanataBlock.REINFORCED_CONCRETE_PANEL.get(),
            blockTexture(KanataBlock.REINFORCED_CONCRETE_BLOCK.get())
        );
        //endregion

        //region 壊れた強化コンクリート
        generateCube(KanataBlock.CRUMBLING_REINFORCED_CONCRETE_BLOCK.get());
        generateStairs(
            KanataBlock.CRUMBLING_REINFORCED_CONCRETE_STAIRS.get(),
            blockTexture(KanataBlock.CRUMBLING_REINFORCED_CONCRETE_BLOCK.get())
        );
        generateSlab(
            KanataBlock.CRUMBLING_REINFORCED_CONCRETE_SLAB.get(),
            blockTexture(KanataBlock.CRUMBLING_REINFORCED_CONCRETE_BLOCK.get())
        );
        generateWall(
            KanataBlock.CRUMBLING_REINFORCED_CONCRETE_WALL.get(),
            blockTexture(KanataBlock.CRUMBLING_REINFORCED_CONCRETE_BLOCK.get())
        );
        generatePane(
            KanataBlock.CRUMBLING_REINFORCED_CONCRETE_PANEL.get(),
            blockTexture(KanataBlock.CRUMBLING_REINFORCED_CONCRETE_BLOCK.get())
        );
        //endregion

        //region 強化コンクリート床
        generateCube(KanataBlock.REINFORCED_CONCRETE_FLOOR_BLOCK.get());
        generateStairs(
            KanataBlock.REINFORCED_CONCRETE_FLOOR_STAIRS.get(),
            blockTexture(KanataBlock.REINFORCED_CONCRETE_FLOOR_BLOCK.get())
        );
        generateSlab(
            KanataBlock.REINFORCED_CONCRETE_FLOOR_SLAB.get(),
            blockTexture(KanataBlock.REINFORCED_CONCRETE_FLOOR_BLOCK.get())
        );
        generateWall(
            KanataBlock.REINFORCED_CONCRETE_FLOOR_WALL.get(),
            blockTexture(KanataBlock.REINFORCED_CONCRETE_FLOOR_BLOCK.get())
        );
        generatePane(
            KanataBlock.REINFORCED_CONCRETE_FLOOR_PANEL.get(),
            blockTexture(KanataBlock.REINFORCED_CONCRETE_FLOOR_BLOCK.get())
        );
        //endregion

        //region 強化合金板
        generateCube(KanataBlock.REINFORCED_ALLOY_PLATE_BLOCK.get());
        generateStairs(
            KanataBlock.REINFORCED_ALLOY_PLATE_STAIRS.get(),
            blockTexture(KanataBlock.REINFORCED_ALLOY_PLATE_BLOCK.get())
        );
        generateSlab(
            KanataBlock.REINFORCED_ALLOY_PLATE_SLAB.get(),
            blockTexture(KanataBlock.REINFORCED_ALLOY_PLATE_BLOCK.get())
        );
        generateWall(
            KanataBlock.REINFORCED_ALLOY_PLATE_WALL.get(),
            blockTexture(KanataBlock.REINFORCED_ALLOY_PLATE_BLOCK.get())
        );
        generatePane(
            KanataBlock.REINFORCED_ALLOY_PLATE_PANEL.get(),
            blockTexture(KanataBlock.REINFORCED_ALLOY_PLATE_BLOCK.get())
        );
        //endregion

        //region 強化レンガ
        generateCube(KanataBlock.REINFORCED_BRICK_BLOCK.get());
        generateStairs(
            KanataBlock.REINFORCED_BRICK_STAIRS.get(),
            blockTexture(KanataBlock.REINFORCED_BRICK_BLOCK.get())
        );
        generateSlab(KanataBlock.REINFORCED_BRICK_SLAB.get(), blockTexture(KanataBlock.REINFORCED_BRICK_BLOCK.get()));
        generateWall(KanataBlock.REINFORCED_BRICK_WALL.get(), blockTexture(KanataBlock.REINFORCED_BRICK_BLOCK.get()));
        generatePane(KanataBlock.REINFORCED_BRICK_PANEL.get(), blockTexture(KanataBlock.REINFORCED_BRICK_BLOCK.get()));
        //endregion

        generateDoor(KanataBlock.REINFORCED_DOOR.get());
    }

    private void generateCube(Block block) {
        simpleBlockWithItem(block, cubeAll(block));
    }

    private void generateStairs(StairBlock block, ResourceLocation texture) {
        stairsBlock(block, texture);
        simpleBlockItem(block, models().getExistingFile(ForgeRegistries.BLOCKS.getKey(block)));
    }

    private void generateSlab(SlabBlock block, ResourceLocation texture) {
        slabBlock(block, texture, texture);
        simpleBlockItem(block, models().getExistingFile(ForgeRegistries.BLOCKS.getKey(block)));
    }

    private void generateWall(WallBlock block, ResourceLocation texture) {
        String invPath = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath() + "_inventory";
        wallBlock(block, texture);
        models().wallInventory(invPath, texture);
        simpleBlockItem(block, models().getExistingFile(modLoc(invPath)));
    }

    private void generatePane(IronBarsBlock block, ResourceLocation texture) {
        String path = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
        paneBlock(block, texture, texture);
        itemModels()
            .getBuilder(path)
            .parent(new ModelFile.UncheckedModelFile("item/generated"))
            .texture("layer0", texture);
    }

    private void generateDoor(DoorBlock block) {
        var prefix = "block/" + Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
        var texTop = modLoc(prefix + "_top");
        var texBottom = modLoc(prefix + "_bottom");
        doorBlock(block, texBottom, texTop);
        itemModels().basicItem(block.asItem());
    }
}
