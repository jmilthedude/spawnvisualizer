package net.ninjadev.spawnvisualizer.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractTextWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.gui.widget.HorizontalRangeSlider;
import net.ninjadev.spawnvisualizer.gui.widget.MobSettingListWidget;
import net.ninjadev.spawnvisualizer.gui.widget.TicksBetweenScansSlider;
import net.ninjadev.spawnvisualizer.gui.widget.VerticalRangeSlider;
import net.ninjadev.spawnvisualizer.gui.widget.entry.Entry;
import net.ninjadev.spawnvisualizer.gui.widget.entry.OptionEntry;
import net.ninjadev.spawnvisualizer.init.ModConfigs;
import net.ninjadev.spawnvisualizer.init.ModKeybinds;

public class ConfigScreen extends Screen {

    public static final Identifier HUD_RESOURCE = new Identifier(SpawnVisualizer.MODID, "textures/gui/config_screen.png");

    private MobSettingListWidget mobList;

    public ConfigScreen() {
        super(Text.translatable("screen.spawnvisualizer.config.title"));
    }

    @Override
    protected void init() {
        int buttonY = 35;
        int buttonHeight = 24;

        int center = this.width / 2;

        OptionEntry enableButton = new OptionEntry(center - Entry.BUTTON_WIDTH - 4, buttonY, Text.translatable("screen.spawnvisualizer.config.toggle"), entry -> entry.setSelected(ModConfigs.GENERAL.toggleEnabled()));

        this.addDrawableChild(enableButton);

        int scanRate = ModConfigs.GENERAL.getTicksBetweenScans();
        TicksBetweenScansSlider ticks = new TicksBetweenScansSlider(center + 4, buttonY + 2, scanRate);
        this.addDrawableChild(ticks);
        buttonY += buttonHeight;

        MutableText rangeText = Text.translatable("screen.spawnvisualizer.config.range").setStyle(Style.EMPTY.withUnderline(true));
        int rangeWidth = this.textRenderer.getWidth(rangeText);
        AbstractTextWidget rangeTextWidget = new TextWidget(center - rangeWidth / 2, buttonY, rangeWidth, 24, rangeText, textRenderer);
        this.addDrawable(rangeTextWidget);
        buttonY += buttonHeight - 4;

        int horizontalRange = ModConfigs.GENERAL.getRangeHorizontal();
        HorizontalRangeSlider horizontal = new HorizontalRangeSlider(center - Entry.BUTTON_WIDTH - 4, buttonY, horizontalRange);
        this.addDrawableChild(horizontal);

        int verticalRange = ModConfigs.GENERAL.getRangeVertical();
        VerticalRangeSlider vertical = new VerticalRangeSlider(center + 4, buttonY, verticalRange);
        this.addDrawableChild(vertical);
        buttonY += buttonHeight - 4;

        MutableText seedText = Text.translatable("screen.spawnvisualizer.config.seed").setStyle(Style.EMPTY.withUnderline(true));
        int seedWidth = this.textRenderer.getWidth(seedText);
        AbstractTextWidget seedTextWidget = new TextWidget(center - seedWidth / 2, buttonY, seedWidth, 24, seedText, textRenderer);
        this.addDrawable(seedTextWidget);
        buttonY += buttonHeight - 4;

        String seed = ModConfigs.GENERAL.getSeedString();
        TextFieldWidget seedWidget = new TextFieldWidget(this.textRenderer, center - 100, buttonY, 200, 20, seedText);
        seedWidget.setText(seed);
        seedWidget.setChangedListener(ModConfigs.GENERAL::setSeed);
        this.addDrawableChild(seedWidget);
        buttonY += buttonHeight;

        int maxY = this.height - buttonY - 10;
        int height = Math.min(maxY, (ModConfigs.ENTITY_SETTINGS.getEntityIds().size() * 24 / 2) + 20);
        mobList = new MobSettingListWidget(center - 100, buttonY, 200, height);
        this.addDrawable(mobList);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double verticalAmount) {
        if (mobList.isMouseOver(mouseX, mouseY)) {
            return mobList.mouseScrolled(mouseX, mouseY, verticalAmount);
        }
        return super.mouseScrolled(mouseX, mouseY, verticalAmount);
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
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredTextWithShadow(matrices, this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (ModKeybinds.OPEN_MENU.matchesKey(keyCode, scanCode)) {
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
        ModConfigs.saveGeneral();
        super.close();
    }
}
