package com.github.yasuniku.kanata.block.spawnbox;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class SpawnBoxBlockItem extends BlockItem {
    public SpawnBoxBlockItem(Block block, Properties props) {
        super(block, props);
    }

    @Override
    public void initializeClient(
        Consumer<IClientItemExtensions> consumer
    ) {
        consumer.accept(new IClientItemExtensions() {
            @Nullable
            private BlockEntityWithoutLevelRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    renderer = new SpawnBoxBlockItemRenderer();
                }

                return renderer;
            }
        });
    }
}
