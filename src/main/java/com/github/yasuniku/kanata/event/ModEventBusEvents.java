package com.github.yasuniku.kanata.event;

import com.github.yasuniku.kanata.KanataMod;
import com.github.yasuniku.kanata.entity.custom.RobotEntity;
import com.github.yasuniku.kanata.registry.KanataEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = KanataMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(KanataEntity.ROBOT.get(), RobotEntity.createAttribute().build());
    }
}
