package com.github.yasuniku.kanata.registry;

import com.github.yasuniku.kanata.dimension.generator.town.TownChunkGenerator;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class KanataDimension {
    public static final DeferredRegister<Codec<? extends ChunkGenerator>> CHUNK_GENERATORS =
        DeferredRegister.create(Registries.CHUNK_GENERATOR, "kanata");

    @SuppressWarnings("unused")
    public static final RegistryObject<Codec<TownChunkGenerator>> CITY_CHUNK_GENERATOR =
        CHUNK_GENERATORS.register("city_chunk_generator", () -> TownChunkGenerator.CODEC);

    public static void register(IEventBus bus) {
        CHUNK_GENERATORS.register(bus);
    }

    @SuppressWarnings("unused")
    public static final ResourceKey<Level> KANATA_TEST_DIMENSION = ResourceKey.create(Registries.DIMENSION,
        ResourceLocation.fromNamespaceAndPath("kanata", "kanata_test_dimension")
    );

    public static final ResourceKey<Level> KANATA_TOWN_DIMENSION =
        ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("kanata", "town"));
}
