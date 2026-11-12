package com.github.yasuniku.kanata.registry;

import com.github.yasuniku.kanata.KanataMod;
import com.github.yasuniku.kanata.entity.custom.RobotEntity;
import com.github.yasuniku.kanata.entity.custom.SeatEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class KanataEntity {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, KanataMod.MOD_ID);

    public static final RegistryObject<EntityType<RobotEntity>> ROBOT = ENTITY_TYPE.register(
        "robot",
        () -> EntityType.Builder
            .of(RobotEntity::new, MobCategory.MONSTER)
            .sized(RobotEntity.BASE_SIZE_WIDTH, RobotEntity.BASE_SIZE_HEIGHT)
            .build("robot")
    );
    public static final RegistryObject<EntityType<SeatEntity>> SEAT = ENTITY_TYPE.register(
        "seat",
        () -> EntityType.Builder.of(SeatEntity::new, MobCategory.MISC).sized(0.01F, 0.01F).build("seat")
    );

    public static void register(IEventBus eventBus) {
        ENTITY_TYPE.register(eventBus);
    }
}
