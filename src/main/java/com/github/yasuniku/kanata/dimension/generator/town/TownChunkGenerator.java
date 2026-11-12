package com.github.yasuniku.kanata.dimension.generator.town;

import com.github.yasuniku.kanata.utils.BlockPlaceHelper;
import com.github.yasuniku.kanata.utils.MyChunkPos;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class TownChunkGenerator extends ChunkGenerator {
    private final ChunkBuildingRenderer renderer;

    public static final Codec<TownChunkGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        BiomeSource.CODEC.fieldOf("biome_source").forGetter(g -> g.biomeSource),
        Codec.LONG.fieldOf("seed").forGetter(g -> g.seed)
    ).apply(instance, TownChunkGenerator::new));

    private final long seed;

    public TownChunkGenerator(BiomeSource biomeSource, long seed) {
        super(biomeSource);
        this.seed = seed;
        this.renderer = new ChunkBuildingRenderer(seed);
    }

    @Override
    public Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void applyCarvers(
        WorldGenRegion region,
        long seed,
        RandomState randomState,
        BiomeManager biomeManager,
        StructureManager structureManager,
        ChunkAccess chunk,
        GenerationStep.Carving step
    ) {
    }

    @Override
    public void buildSurface(
        WorldGenRegion region,
        StructureManager structureManager,
        RandomState randomState,
        ChunkAccess chunk
    ) {
        generateChunk(region, chunk.getPos());
    }

    public void generateChunk(ServerLevelAccessor level, ChunkPos chunkPos) {
        MyChunkPos myPos = MyChunkPos.fromChunkPos(chunkPos);

        BlockPlaceHelper.fillCuboid(level, myPos, 0, 0, Blocks.BEDROCK.defaultBlockState());
        BlockPlaceHelper.fillCuboid(level, myPos, 1, 3, Blocks.DIRT.defaultBlockState());

        renderer.generateChunk(level, myPos);
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(
        Executor executor,
        Blender blender,
        RandomState randomState,
        StructureManager structureManager,
        ChunkAccess chunk
    ) {
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public void applyBiomeDecoration(WorldGenLevel level, ChunkAccess chunk, StructureManager structureManager) {
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion region) {
    }

    @Override
    public int getGenDepth() {
        return 256;
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getBaseHeight(
        int x,
        int z,
        Heightmap.Types type,
        LevelHeightAccessor heightAccessor,
        RandomState randomState
    ) {
        return 0;
    }

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor heightAccessor, RandomState randomState) {
        int minY = heightAccessor.getMinBuildHeight();
        int maxY = heightAccessor.getMaxBuildHeight();
        BlockState[] states = new BlockState[maxY - minY];
        Arrays.fill(states, Blocks.AIR.defaultBlockState());
        return new NoiseColumn(minY, states);
    }

    @Override
    public void addDebugScreenInfo(List<String> info, RandomState randomState, BlockPos pos) {
        info.add("CityChunkGenerator active");
    }

    public void regenerateChunk(ServerLevel level, ChunkPos chunkPos) {
        clearChunk(level, chunkPos);
        generateChunk(level, chunkPos);
    }

    private static void clearChunk(ServerLevel level, ChunkPos chunkPos) {
        int minY = level.getMinBuildHeight();
        int maxY = level.getMaxBuildHeight();

        BlockPlaceHelper.fillCuboid(
            level,
            MyChunkPos.fromChunkPos(chunkPos),
            minY,
            maxY,
            Blocks.AIR.defaultBlockState()
        );
    }
}
