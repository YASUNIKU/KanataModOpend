package com.github.yasuniku.kanata.dimension;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class TownData extends SavedData {

    private int activeStageID = 0;
    private int generationVersion = 0;

    public static TownData load(CompoundTag tag) {
        TownData data = new TownData();
        data.activeStageID = tag.getInt("ActiveStageID");
        data.generationVersion = tag.getInt("GenerationVersion");
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putInt("ActiveStageID", activeStageID);
        tag.putInt("GenerationVersion", generationVersion);
        return tag;
    }

    public static TownData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TownData::load, TownData::new, "town_data");
    }

    public int getActiveStageID() {
        return activeStageID;
    }

    public int getGenerationVersion() {
        return generationVersion;
    }

    public void setActiveStageID(int activeStageID) {
        this.activeStageID = activeStageID;
        setDirty();
    }

    public void incrementGenerationVersion() {
        generationVersion++;
        setDirty();
    }

    public void incrementActiveStageID() {
        setActiveStageID(getActiveStageID() + 1);
    }
}
