package net.ninjadev.spawnvisualizer.visualizer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
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

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return spawnablePositions;

        ClientLevel level = player.clientLevel;
        BlockPos pos = player.getOnPos();
        for (int x = -ModConfigs.GENERAL.getRangeHorizontal(); x <= ModConfigs.GENERAL.getRangeHorizontal(); x++) {
            for (int y = -ModConfigs.GENERAL.getRangeVertical(); y <= ModConfigs.GENERAL.getRangeVertical(); y++) {
                for (int z = -ModConfigs.GENERAL.getRangeHorizontal(); z <= ModConfigs.GENERAL.getRangeHorizontal(); z++) {
                    BlockPos current = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                    ModSpawnValidators.getValidators()
                            .stream()
                            .filter(SpawnValidator::isEnabled)
                            .filter(spawnValidator -> spawnValidator.canSpawn(level, current))
                            .forEach(spawnSettings -> {
                                List<Color> colors = spawnablePositions.computeIfAbsent(current, blockPos -> new ArrayList<>());
                                colors.add(spawnSettings.getColor());
                            });
//                    ModSpawnSettings.getRegistry()
//                            .values()
//                            .stream()
//                            .filter(SpawnSettings::isEnabled)
//                            .filter(spawnSettings -> spawnSettings.canSpawn(level, current))
//                            .forEach(spawnSettings -> {
//                                List<Color> colors = spawnablePositions.computeIfAbsent(current, blockPos -> new ArrayList<>());
//                                colors.add(spawnSettings.getColor());
//                            });
                }
            }
        }
        return spawnablePositions;
    }
}
