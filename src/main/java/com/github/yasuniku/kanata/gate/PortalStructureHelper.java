package com.github.yasuniku.kanata.gate;

import com.github.yasuniku.kanata.registry.KanataBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

public class PortalStructureHelper {
    // 外周に使うブロック（Mod のポータルフレーム）
    private static boolean isFrame(Block block) {
        return block == KanataBlock.T1_PORTAL_FRAME_BLOCK.get();
    }

    // 外周のどこかに置かれたときに呼び出す
    public static void tryActivatePortal(Level level, BlockPos placed) {
        BlockPos center = findValidCenter(level, placed);
        if (center == null) {
            return;
        }

        if (isValidFrame(level, center)) {
            fillInner(level, center);
        }
    }

    // 中心候補を総当たりで探す
    private static @Nullable BlockPos findValidCenter(Level level, BlockPos placed) {
        int px = placed.getX();
        int py = placed.getY();
        int pz = placed.getZ();

        for (int cx = px - 2; cx <= px + 2; cx++) {
            for (int cz = pz - 2; cz <= pz + 2; cz++) {
                BlockPos center = new BlockPos(cx, py, cz);

                if (isValidFrame(level, center) && isOnFrame(center, placed)) {
                    return center;
                }
            }
        }
        return null;
    }

    // 置いた位置が外周（角以外）にあるか？
    private static boolean isOnFrame(BlockPos center, BlockPos placed) {
        int dx = placed.getX() - center.getX();
        int dz = placed.getZ() - center.getZ();

        // 角は外周扱いしない
        if (Math.abs(dx) == 2 && Math.abs(dz) == 2) {
            return false;
        }

        boolean edgeX = Math.abs(dx) == 2 && Math.abs(dz) <= 1;
        boolean edgeZ = Math.abs(dz) == 2 && Math.abs(dx) <= 1;

        return edgeX || edgeZ;
    }

    // 外周チェック（角は自由）
    private static boolean isValidFrame(Level level, BlockPos center) {
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {

                // 内側は無視
                if (Math.abs(dx) <= 1 && Math.abs(dz) <= 1) {
                    continue;
                }

                // 角は自由
                if (Math.abs(dx) == 2 && Math.abs(dz) == 2) {
                    continue;
                }

                BlockPos pos = center.offset(dx, 0, dz);
                Block block = level.getBlockState(pos).getBlock();

                if (!isFrame(block)) {
                    return false;
                }
            }
        }
        return true;
    }

    // 内側 3×3 を生成（後でポータルブロックに変更可能）
    private static void fillInner(Level level, BlockPos center) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos pos = center.offset(dx, 0, dz);
                level.setBlockAndUpdate(pos, KanataBlock.T1_PORTAL_BLOCK.get().defaultBlockState());
            }
        }
    }
}
