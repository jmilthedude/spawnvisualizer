package net.ninjadev.spawnvisualizer.gui.widget.entry;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.ninjadev.spawnvisualizer.gui.ConfigScreen;
import net.ninjadev.spawnvisualizer.gui.widget.ScrollingListWidget;

public abstract class Entry extends ClickableWidget {
    public static final int BUTTON_WIDTH = 90;
    public static final int BUTTON_HEIGHT = 24;
    protected boolean selected;
    private ScrollingListWidget parent;

    public Entry(int x, int y, Text message, ScrollingListWidget parent) {
        super(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, message);
        this.parent = parent;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return this.getX() <= mouseX && mouseX <= this.getX() + BUTTON_WIDTH
                && this.getY() <= mouseY && mouseY <= this.getY() + BUTTON_HEIGHT;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();

        boolean isHovered = isHovered(mouseX, mouseY);

       // RenderSystem.setShaderTexture(0, ConfigScreen.HUD_RESOURCE);

        context.drawTexture(ConfigScreen.HUD_RESOURCE, this.getX(), this.getY(), 0, selected ? 24 : 48, BUTTON_WIDTH, BUTTON_HEIGHT, 256, 256);

//        if (isHovered) {
//            drawTexture(matrices, x, y, 0, 48, BUTTON_WIDTH, BUTTON_HEIGHT, 256, 256);
//        }

        //RenderSystem.disableDepthTest();
        float startX = (this.getX() + (BUTTON_WIDTH / 2f) - (client.textRenderer.getWidth(getMessage()) / 2f));
        context.drawText(client.textRenderer, this.getMessage(), (int) startX, this.getY() + 8, isHovered ? 0xFF_FFFF00 : 0xFF_FFFFFF, true);
       // RenderSystem.enableDepthTest();
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
