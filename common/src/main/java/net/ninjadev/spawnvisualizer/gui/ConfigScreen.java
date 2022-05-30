package net.ninjadev.spawnvisualizer.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.gui.widget.HorizontalRangeSlider;
import net.ninjadev.spawnvisualizer.gui.widget.MobSettingListWidget;
import net.ninjadev.spawnvisualizer.gui.widget.VerticalRangeSlider;
import net.ninjadev.spawnvisualizer.gui.widget.entry.OptionEntry;
import net.ninjadev.spawnvisualizer.init.ModConfigs;
import org.lwjgl.glfw.GLFW;

public class ConfigScreen extends Screen {

    public static final ResourceLocation HUD_RESOURCE = new ResourceLocation(SpawnVisualizer.MODID, "textures/gui/config_screen.png");

    private MobSettingListWidget mobList;

    public ConfigScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        int buttonY = 40;
        int buttonHeight = 24;

        OptionEntry enableButton = new OptionEntry(this.width / 2 - 45, buttonY, Component.nullToEmpty("Toggle On/Off"));
        this.addRenderableWidget(enableButton);
        buttonY += buttonHeight + 4;

        int horizontalRange = ModConfigs.GENERAL.getRangeHorizontal();
        HorizontalRangeSlider horizontal = new HorizontalRangeSlider(this.width / 2 - 45, buttonY, horizontalRange);
        this.addRenderableWidget(horizontal);
        buttonY += buttonHeight;

        int verticalRange = ModConfigs.GENERAL.getRangeVertical();
        VerticalRangeSlider vertical = new VerticalRangeSlider(this.width / 2 - 45, buttonY, verticalRange);
        this.addRenderableWidget(vertical);
        buttonY += buttonHeight;

        mobList = new MobSettingListWidget((this.width / 2) - 50, buttonY, 100, this.height - buttonY - 15);
        this.addRenderableOnly(mobList);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (mobList.isMouseOver(mouseX, mouseY)) {
            return mobList.mouseScrolled(mouseX, mouseY, amount);
        }
        return super.mouseScrolled(mouseX, mouseY, amount);
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
    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        Screen.drawCenteredString(matrices, this.font, this.title, this.width / 2, 15, 0xFFFFFF);

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_M) {
            Minecraft.getInstance().setScreen(null);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        ModConfigs.GENERAL.writeConfig();
        super.onClose();
    }
}
