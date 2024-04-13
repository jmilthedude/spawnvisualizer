package net.ninjadev.spawnvisualizer.gui.widget.entry;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.ninjadev.spawnvisualizer.gui.ConfigScreen;

import java.util.function.Consumer;

public abstract class Entry extends ClickableWidget {
    public static final int BUTTON_WIDTH = 90;
    public static final int BUTTON_HEIGHT = 24;
    protected boolean selected;

    private Consumer<Entry> onClick;

    public Entry(int x, int y, Text message) {
        this(x, y, message, entry -> {
        });
    }

    public Entry(int x, int y, Text message, Consumer<Entry> onClick) {
        super(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, message);
        this.onClick = onClick;
    }

    public <E extends Entry> E setSelected(boolean selected) {
        this.selected = selected;
        return (E) this;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    public <E extends Entry> E setOnClick(Consumer<Entry> onClick) {
        this.onClick = onClick;
        return (E) this;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.onClick.accept(this);
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return this.getX() <= mouseX && mouseX <= this.getX() + BUTTON_WIDTH
                && this.getY() <= mouseY && mouseY <= this.getY() + BUTTON_HEIGHT;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();

        boolean isHovered = isHovered(mouseX, mouseY);
        RenderSystem.setShaderTexture(0, ConfigScreen.HUD_RESOURCE);
        drawTexture(matrices, this.getX(), this.getY(),  0, selected ? 24 : 48, BUTTON_WIDTH, BUTTON_HEIGHT, 256, 256);
        float startX = (this.getX() + (BUTTON_WIDTH / 2f) - (client.textRenderer.getWidth(getMessage()) / 2f));
        RenderSystem.disableDepthTest();
        client.textRenderer.drawWithShadow(matrices, this.getMessage(), (int) startX, this.getY() + 8, isHovered ? 0xFF_FFFF00 : 0xFF_FFFFFF);
        RenderSystem.enableDepthTest();
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
