package net.ninjadev.spawnvisualizer.gui.widget;

import net.ninjadev.spawnvisualizer.gui.widget.entry.MobEntry;
import net.ninjadev.spawnvisualizer.init.ModSpawnSettings;
import net.ninjadev.spawnvisualizer.settings.SpawnSettings;

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

        List<String> keys = new ArrayList<>(ModSpawnSettings.getRegistry().keySet());
        Collections.sort(keys);

        for (String key : keys) {
            SpawnSettings validator = ModSpawnSettings.getRegistry().get(key);
            MobEntry buttonEntry = new MobEntry((this.getBounds().width - scrollBarWidth) / 2 - 45, buttonY, validator, this);
            this.addEntry(buttonEntry);
            buttonY += buttonHeight;
        }
    }
}
