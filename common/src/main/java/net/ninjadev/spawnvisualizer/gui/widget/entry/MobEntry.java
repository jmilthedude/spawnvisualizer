package net.ninjadev.spawnvisualizer.gui.widget.entry;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.ninjadev.spawnvisualizer.gui.EntitySettingsScreen;
import net.ninjadev.spawnvisualizer.gui.widget.ScrollingListWidget;
import net.ninjadev.spawnvisualizer.settings.SpawnValidator;

public class MobEntry extends Entry {

    private final SpawnValidator validator;
    private final ScrollingListWidget parent;

    public MobEntry(int x, int y, SpawnValidator validator, ScrollingListWidget parent) {
        super(parent.getBounds().x + x, parent.getBounds().y + y, Text.translatable(validator.getType().getTranslationKey()));
        this.validator = validator;
        this.selected = validator.isEnabled();
        this.parent = parent;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        validator.toggle();
        this.selected = validator.isEnabled();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.isHovered((int) mouseX, (int) mouseY)) return false;
        if(button == 0) {
            validator.toggle();
            this.selected = validator.isEnabled();
        } else if(button == 1) {
            MinecraftClient.getInstance().setScreen(new EntitySettingsScreen(this.validator));
        }
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        return true;
    }

    @Override
    public boolean isHovered(int mouseX, int mouseY) {
        return this.getX() - parent.getBounds().x <= mouseX && mouseX <= this.getX() - parent.getBounds().x + BUTTON_WIDTH
                && this.getY() - parent.getBounds().y <= mouseY && mouseY <= this.getY() - parent.getBounds().y + BUTTON_HEIGHT;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderWidget(context, mouseX, mouseY, delta);
        if (selected) {
            context.drawHorizontalLine(this.getX(), this.getX() + BUTTON_WIDTH - 1, this.getY(), validator.getColor().getRGB());
            context.drawHorizontalLine(this.getX(), this.getX() + BUTTON_WIDTH - 1, this.getY() + BUTTON_HEIGHT - 1, validator.getColor().getRGB());
            context.drawVerticalLine(this.getX(), this.getY(), this.getY() + BUTTON_HEIGHT - 1, validator.getColor().getRGB());
            context.drawVerticalLine(this.getX() + BUTTON_WIDTH - 1, this.getY(), this.getY() + BUTTON_HEIGHT - 1, validator.getColor().getRGB());
        }
    }
}
