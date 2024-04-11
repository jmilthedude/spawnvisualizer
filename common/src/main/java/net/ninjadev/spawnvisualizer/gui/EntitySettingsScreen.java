package net.ninjadev.spawnvisualizer.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractTextWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import net.ninjadev.spawnvisualizer.gui.widget.ColorSliderWidget;
import net.ninjadev.spawnvisualizer.gui.widget.ColorSquareWidget;
import net.ninjadev.spawnvisualizer.gui.widget.entry.Entry;
import net.ninjadev.spawnvisualizer.gui.widget.entry.OptionEntry;
import net.ninjadev.spawnvisualizer.init.ModConfigs;
import net.ninjadev.spawnvisualizer.settings.SpawnValidator;

import java.awt.*;
import java.util.Arrays;

public class EntitySettingsScreen extends Screen {

    private final SpawnValidator spawnValidator;
    private final SpawnValidator backup;

    public EntitySettingsScreen(SpawnValidator validator) {
        super(Text.translatable(validator.getType().getTranslationKey()).append(" ").append(Text.translatable("screen.spawnvisualizer.validator.title")));
        this.spawnValidator = validator;
        this.backup = validator.copy();
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startX = centerX - Entry.BUTTON_WIDTH;
        int y = 35;

        int seedWidth = this.textRenderer.getWidth(this.title);
        AbstractTextWidget seedTextWidget = new TextWidget(centerX - seedWidth / 2, y, seedWidth, 24, this.title, textRenderer);
        this.addDrawable(seedTextWidget);
        y += Entry.BUTTON_HEIGHT + 2;

        ColorSliderWidget hueSlider = new ColorSliderWidget(startX, y, Entry.BUTTON_WIDTH * 2, Entry.BUTTON_HEIGHT, Text.translatable("screen.spawnvisualizer.config.hue"), this.getColorHSB()[0], 0, 360, value -> this.updateColor(value, 0));
        this.addDrawableChild(hueSlider);
        y += Entry.BUTTON_HEIGHT + 2;
        ColorSliderWidget saturationSlider = new ColorSliderWidget(startX, y, Entry.BUTTON_WIDTH * 2, Entry.BUTTON_HEIGHT, Text.translatable("screen.spawnvisualizer.config.saturation"), this.getColorHSB()[1], 0, 100, value -> this.updateColor(value, 1));
        this.addDrawableChild(saturationSlider);
        y += Entry.BUTTON_HEIGHT + 2;
        ColorSliderWidget brightnessSlider = new ColorSliderWidget(startX, y, Entry.BUTTON_WIDTH * 2, Entry.BUTTON_HEIGHT, Text.translatable("screen.spawnvisualizer.config.brightness"), this.getColorHSB()[2], 0, 100, value -> this.updateColor(value, 2));
        this.addDrawableChild(brightnessSlider);
        y += Entry.BUTTON_HEIGHT + 2;

        ColorSquareWidget colorSquareWidget = new ColorSquareWidget(startX, y, Entry.BUTTON_WIDTH * 2, Entry.BUTTON_HEIGHT, this.spawnValidator::getColor);
        this.addDrawable(colorSquareWidget);
        y += Entry.BUTTON_HEIGHT + 2;

        this.addDrawableChild(new OptionEntry(startX, y, Text.translatable("selectWorld.edit.save"), entry -> {
            MinecraftClient.getInstance().setScreen(new ConfigScreen());
        }).setSelected(false));

        this.addDrawableChild(new OptionEntry(centerX, y, Text.translatable("gui.cancel"), entry -> {
            ModConfigs.ENTITY_SETTINGS.resetSettings(this.spawnValidator.getType(), this.backup.getConfig());
            MinecraftClient.getInstance().setScreen(new ConfigScreen());
        }).setSelected(false));

    }

    private void updateColor(float value, int index) {
        float[] hsb = this.getColorHSB();
        hsb[index] = value;
        int color = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
        this.spawnValidator.setColorInt(color);
    }

    private float[] getColorHSB() {
        Color color = this.spawnValidator.getColor();
        return Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
