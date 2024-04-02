package net.ninjadev.spawnvisualizer.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.gui.widget.HorizontalRangeSlider;
import net.ninjadev.spawnvisualizer.gui.widget.MobSettingListWidget;
import net.ninjadev.spawnvisualizer.gui.widget.TicksBetweenScansSlider;
import net.ninjadev.spawnvisualizer.gui.widget.VerticalRangeSlider;
import net.ninjadev.spawnvisualizer.gui.widget.entry.OptionEntry;
import net.ninjadev.spawnvisualizer.init.ModConfigs;
import org.lwjgl.glfw.GLFW;

public class ConfigScreen extends Screen {

    public static final Identifier HUD_RESOURCE = new Identifier(SpawnVisualizer.MODID, "textures/gui/config_screen.png");

    private MobSettingListWidget mobList;

    public ConfigScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        int buttonY = 40;
        int buttonHeight = 24;

        OptionEntry enableButton = new OptionEntry(this.width / 2 - 45, buttonY, Text.of("Toggle On/Off"));
        this.addDrawableChild(enableButton);
        buttonY += buttonHeight + 4;

        int horizontalRange = ModConfigs.GENERAL.getRangeHorizontal();
        HorizontalRangeSlider horizontal = new HorizontalRangeSlider(this.width / 2 - 45, buttonY, horizontalRange);
        this.addDrawableChild(horizontal);
        buttonY += buttonHeight;

        int verticalRange = ModConfigs.GENERAL.getRangeVertical();
        VerticalRangeSlider vertical = new VerticalRangeSlider(this.width / 2 - 45, buttonY, verticalRange);
        this.addDrawableChild(vertical);
        buttonY += buttonHeight;

        int scanRate = ModConfigs.GENERAL.getTicksBetweenScans();
        TicksBetweenScansSlider ticks = new TicksBetweenScansSlider(this.width / 2 - 45, buttonY, scanRate);
        this.addDrawableChild(ticks);
        buttonY += buttonHeight;

        mobList = new MobSettingListWidget((this.width / 2) - 50, buttonY, 100, this.height - buttonY - 15);
        this.addDrawable(mobList);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (mobList.isMouseOver(mouseX, mouseY)) {
            return mobList.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (mobList.isMouseOver(mouseX, mouseY)) {
            return mobList.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (mobList.isScrolling()) {
            return mobList.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (mobList.isScrolling()) {
            return mobList.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_M) {
            MinecraftClient.getInstance().setScreen(null);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }


    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void close() {
        ModConfigs.GENERAL.writeConfig();
        super.close();
    }
}
