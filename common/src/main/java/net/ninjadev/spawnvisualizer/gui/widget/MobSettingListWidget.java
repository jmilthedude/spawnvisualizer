package net.ninjadev.spawnvisualizer.gui.widget;

import net.minecraft.resources.ResourceLocation;
import net.ninjadev.spawnvisualizer.gui.widget.entry.MobEntry;
import net.ninjadev.spawnvisualizer.init.ModConfigs;
import net.ninjadev.spawnvisualizer.init.ModSpawnValidators;
import net.ninjadev.spawnvisualizer.settings.SpawnValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MobSettingListWidget extends ScrollingListWidget {

    public MobSettingListWidget(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    protected void init() {
        int buttonY = 5;
        int buttonHeight = 24;
        List<ResourceLocation> keys = new ArrayList<>(ModConfigs.MOB_SETTINGS.getEntityIds());
        Collections.sort(keys);

        for (ResourceLocation key : keys) {
            this.createButton(key, buttonY);
            buttonY += buttonHeight;
        }
    }

    private void createButton(ResourceLocation id, int buttonY) {
        SpawnValidator validator = ModSpawnValidators.getValidator(id);
        MobEntry buttonEntry = new MobEntry((this.getBounds().width - scrollBarWidth) / 2 - 45, buttonY, validator, this);
        this.addEntry(buttonEntry);
    }
}
