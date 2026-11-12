package com.github.yasuniku.kanata.registry;

import com.github.yasuniku.kanata.KanataMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class KanataModelLayers {
    public static final ModelLayerLocation ROBOT_LAYER =
        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(KanataMod.MOD_ID, "robot_layer"), "main");
    public static final ModelLayerLocation MODULE_UPDATE_MACHINE = new ModelLayerLocation(
        ResourceLocation.fromNamespaceAndPath(KanataMod.MOD_ID, "module_update_machine"),
        "main"
    );
}
