package com.github.yasuniku.kanata.registry;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.github.yasuniku.kanata.KanataMod.MOD_ID;

public class KanataAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES =
        DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MOD_ID);

    public static final RegistryObject<Attribute> COMBAT_STATE = ATTRIBUTES.register(
        "combat_state",
        () -> new RangedAttribute("attribute.kanata_mod.combat_state", 0.0, 0.0, 10000.0).setSyncable(true)
    );

    public static void register(IEventBus bus) {
        ATTRIBUTES.register(bus);
    }
}
