package net.ninjadev.spawnvisualizer.gui.widget.entry;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.*;
import net.ninjadev.spawnvisualizer.gui.widget.ScrollingListWidget;
import net.ninjadev.spawnvisualizer.settings.SpawnSettings;
import net.ninjadev.spawnvisualizer.settings.SpawnValidator;

public class MobEntry extends Entry {

    private final SpawnValidator validator;
    private final ScrollingListWidget parent;

    public MobEntry(int x, int y, SpawnValidator validator, ScrollingListWidget parent) {
        super(x, y, Component.translatable(validator.getType().getDescriptionId()), parent);
        this.validator = validator;
        this.selected = validator.isEnabled();
        this.parent = parent;
        this.x = parent.getBounds().x + x;
        this.y = parent.getBounds().y + y;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        validator.toggle();
        this.selected = validator.isEnabled();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        validator.toggle();
        this.selected = validator.isEnabled();
        return true;
    }

    @Override
    public boolean isHovered(int mouseX, int mouseY) {
        return super.isHovered(mouseX, mouseY);
    }

    @Override
    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        if (selected) {
            hLine(matrices, x, x + BUTTON_WIDTH - 1, this.y, validator.getColor().getRGB());
            hLine(matrices, x, x + BUTTON_WIDTH - 1, this.y + BUTTON_HEIGHT - 1, validator.getColor().getRGB());
            vLine(matrices, x, y, y + BUTTON_HEIGHT - 1, validator.getColor().getRGB());
            vLine(matrices, x + BUTTON_WIDTH - 1, y, y + BUTTON_HEIGHT - 1, validator.getColor().getRGB());
        }
    }

    public ScrollingListWidget getParent() {
        return parent;
    }
}
