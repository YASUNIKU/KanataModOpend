package com.github.yasuniku.kanata.item.module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.BakedModelWrapper;
import org.jspecify.annotations.Nullable;

public class ModuleModel extends BakedModelWrapper<BakedModel> {

    private final ItemOverrides overrides;

    public ModuleModel(BakedModel original) {
        super(original);
        this.overrides = new ItemOverrides() {
            @Override
            public @Nullable BakedModel resolve(
                BakedModel original,
                ItemStack stack,
                @Nullable ClientLevel world,
                @Nullable LivingEntity entity,
                int seed
            ) {
                BakedModel specific = ModuleModel.this.resolve(original, stack);
                return specific == original ? specific : specific
                    .getOverrides()
                    .resolve(specific, stack, world, entity, seed);
            }
        };
    }

    public BakedModel resolve(BakedModel original, ItemStack stack) {
        var modelManager = Minecraft.getInstance().getModelManager();
        return ModuleInstance
            .fromItemStack(stack)
            .map(x -> modelManager.getModel(x.getModelResourceLocation()))
            .orElse(original);
    }

    @Override
    public ItemOverrides getOverrides() {
        return this.overrides;
    }
}
