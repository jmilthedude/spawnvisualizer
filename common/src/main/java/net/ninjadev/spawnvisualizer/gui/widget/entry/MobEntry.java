package net.ninjadev.spawnvisualizer.gui.widget.entry;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.ninjadev.spawnvisualizer.gui.widget.ScrollingListWidget;
import net.ninjadev.spawnvisualizer.settings.SpawnValidator;

public class MobEntry extends Entry {

    private final SpawnValidator validator;
    private final ScrollingListWidget parent;

    public MobEntry(int x, int y, SpawnValidator validator, ScrollingListWidget parent) {
        super(x, y, Text.translatable(validator.getType().getTranslationKey()), parent);
        this.validator = validator;
        this.selected = validator.isEnabled();
        this.parent = parent;
        this.setX(parent.getBounds().x + x);
        this.setY(parent.getBounds().y + y);
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
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        if (selected) {
            drawHorizontalLine(matrices, this.getX(), this.getX() + BUTTON_WIDTH - 1, this.getY(), validator.getColor().getRGB());
            drawHorizontalLine(matrices, this.getX(), this.getX() + BUTTON_WIDTH - 1, this.getY() + BUTTON_HEIGHT - 1, validator.getColor().getRGB());
            drawVerticalLine(matrices, this.getX(), this.getY(), this.getY() + BUTTON_HEIGHT - 1, validator.getColor().getRGB());
            drawVerticalLine(matrices, this.getX() + BUTTON_WIDTH - 1, this.getY(), this.getY() + BUTTON_HEIGHT - 1, validator.getColor().getRGB());
        }
    }

    public ScrollingListWidget getParent() {
        return parent;
    }
}
