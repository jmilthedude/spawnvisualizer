package net.ninjadev.spawnvisualizer.gui.widget;

import net.minecraft.util.Identifier;
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
        List<Identifier> keys = new ArrayList<>(ModSpawnValidators.getValidatorMap().keySet());
        Collections.sort(keys);
        int i = 0;
        for (Identifier key : keys) {
            boolean even = i % 2 == 0;
            int buttonX = even ? (this.getBounds().width - scrollBarWidth) / 2 - 95 : (this.getBounds().width - scrollBarWidth) / 2 + 5;
            this.createButton(key, buttonX, buttonY);
            if (!even) {
                buttonY += buttonHeight;
            }
            i++;
        }
    }

    private void createButton(Identifier key, int buttonX, int buttonY) {
        SpawnValidator validator = ModSpawnValidators.getValidator(key);
        MobEntry buttonEntry = new MobEntry(buttonX, buttonY, validator, this);
        this.addEntry(buttonEntry);
    }
}
