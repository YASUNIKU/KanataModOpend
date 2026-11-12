package com.github.yasuniku.kanata.utils;

import com.github.yasuniku.kanata.registry.KanataAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.IItemHandler;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Utils {
    public static <K> @Nullable K getRandomKey(Map<K, ?> map) {
        if (map.isEmpty()) {
            return null;
        }

        int index = new Random().nextInt(map.size());
        return map.keySet().stream().skip(index).findFirst().orElse(null);
    }

    public static <T> @Nullable T getRandomElement(List<T> list, Random r) {
        if (list.isEmpty()) {
            return null;
        }

        int index = r.nextInt(list.size());
        return list.get(index);
    }

    public static <T> @Nullable T getRandomElement(List<T> list, RandomSource r) {
        if (list.isEmpty()) {
            return null;
        }

        int index = r.nextInt(list.size());
        return list.get(index);
    }

    public static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        }
        //noinspection ManualMinMaxCalculation
        if (value > max) {
            return max;
        }
        return value;
    }

    public static Stream<ItemStack> toStream(IItemHandler handler) {
        return IntStream.range(0, handler.getSlots()).mapToObj(handler::getStackInSlot);
    }

    public static void dropContents(Level pLevel, BlockPos pPos, Stream<ItemStack> pStackList) {
        pStackList.forEach((itemStack) -> Containers.dropItemStack(
            pLevel,
            pPos.getX(),
            pPos.getY(),
            pPos.getZ(),
            itemStack
        ));
    }

    public static ItemStack createErrorItem(String errorMessage) {
        return new ItemStack(Items.TNT).setHoverName(Component.literal(errorMessage));
    }

    public static <K, V> Optional<Map.Entry<K, V>> getMapFirstEntry(Map<K, V> map) {
        return Optional.ofNullable(map.entrySet().iterator().next());
    }

    @SuppressWarnings("unused")
    public static boolean isInCombat(ServerPlayer player) {
        var inCombat = player.getAttribute(KanataAttributes.COMBAT_STATE.get());
        if (inCombat == null) {
            return false;
        }
        return inCombat.getBaseValue() > 0;
    }

    public static int getTickFromSecond(int second) {
        return 20 * second;
    }

    @SuppressWarnings("unused")
    public static int getTickFromMinute(int Minute) {
        return getTickFromSecond(60 * Minute);
    }

    public static VoxelShape rotateShape(Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};

        int times = (to.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4;

        for (int i = 0; i < times; i++) {
            VoxelShape current = buffer[0];
            VoxelShape rotated = Shapes.empty();

            for (AABB box : current.toAabbs()) {
                rotated = Shapes.or(
                    rotated,
                    Shapes.create(1 - box.maxZ, box.minY, box.minX, 1 - box.minZ, box.maxY, box.maxX)
                );
            }

            buffer[0] = rotated;
        }

        return buffer[0];
    }
}
