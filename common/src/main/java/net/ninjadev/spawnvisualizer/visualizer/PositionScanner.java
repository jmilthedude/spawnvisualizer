package net.ninjadev.spawnvisualizer.visualizer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.ninjadev.spawnvisualizer.init.ModConfigs;
import net.ninjadev.spawnvisualizer.init.ModSpawnValidators;
import net.ninjadev.spawnvisualizer.settings.SpawnValidator;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PositionScanner {

    public HashMap<BlockPos, List<Color>> findSpawnablePositions() {
        final HashMap<BlockPos, List<Color>> spawnablePositions = new HashMap<>();

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return spawnablePositions;

        ClientWorld level = player.clientWorld;
        BlockPos pos = player.getSteppingPos();
        for (int x = -ModConfigs.GENERAL.getRangeHorizontal(); x <= ModConfigs.GENERAL.getRangeHorizontal(); x++) {
            for (int y = -ModConfigs.GENERAL.getRangeVertical(); y <= ModConfigs.GENERAL.getRangeVertical(); y++) {
                for (int z = -ModConfigs.GENERAL.getRangeHorizontal(); z <= ModConfigs.GENERAL.getRangeHorizontal(); z++) {
                    BlockPos current = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                    List<SpawnValidator> validators = ModSpawnValidators.getValidators();
                    for (SpawnValidator validator : validators) {
                        if (!validator.isEnabled()) continue;
                        if (!validator.canSpawn(level, current)) continue;
                        spawnablePositions.computeIfAbsent(current, blockPos -> new ArrayList<>()).add(validator.getColor());
                    }
                }
            }
        }
        return spawnablePositions;
    }
}
