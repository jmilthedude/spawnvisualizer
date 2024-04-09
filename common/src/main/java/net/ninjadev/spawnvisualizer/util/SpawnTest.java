package net.ninjadev.spawnvisualizer.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

@FunctionalInterface
public interface SpawnTest<T extends Entity> {

    boolean test(EntityType<T> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random);
}
