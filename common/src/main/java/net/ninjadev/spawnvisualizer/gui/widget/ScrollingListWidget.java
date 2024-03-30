package net.ninjadev.spawnvisualizer.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.ninjadev.spawnvisualizer.gui.ConfigScreen;
import net.ninjadev.spawnvisualizer.gui.widget.entry.Entry;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class ScrollingListWidget extends DrawableHelper implements Drawable, Element {

    protected final Rectangle bounds;
    private final List<Entry> entries = new ArrayList<>();
    protected final int scrollBarWidth = 8;
    private boolean scrolling;
    protected int yOffset;
    private double scrollAmount;
    protected double scrollingStartY;
    protected int scrollingOffsetY;

    private boolean focused;

    public ScrollingListWidget(int x, int y, int width, int height) {
        this.bounds = new Rectangle(x, y, width + scrollBarWidth, height);

        init();
    }

    public int getYOffset() {
        return yOffset;
    }

    public boolean isScrolling() {
        return scrolling;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    protected Rectangle getRenderableBounds() {
        return new Rectangle(bounds.x, bounds.y, bounds.width - scrollBarWidth, bounds.height);
    }

    protected Rectangle getScrollableBounds() {
        return new Rectangle(bounds.x, bounds.y, bounds.width - scrollBarWidth, 10 + (entries.size() * 24));
    }

    protected Rectangle getScrollbarBounds() {
        return new Rectangle(bounds.x + bounds.width - scrollBarWidth, bounds.y, scrollBarWidth, bounds.height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    private boolean isScrollbarClicked(double mouseX, double mouseY) {
        return getScrollbarBounds().contains(mouseX, mouseY);
    }


    public int getSize() {
        return entries.size();
    }

    protected abstract void init();

    protected void addEntry(Entry entry) {
        this.entries.add(entry);
    }



    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        //drawBounds(matrices);

        Rectangle renderableBounds = getRenderableBounds();
        Rectangle scrollBounds = getScrollbarBounds();
        Rectangle scrollableBounds = getScrollableBounds();

        renderOverflowHidden(matrices,
                (ms) -> fill(ms, renderableBounds.x + 1, renderableBounds.y + 1, renderableBounds.x + renderableBounds.width, renderableBounds.y + renderableBounds.height, 0x01_000000),
                (ms) -> {
                    ms.push();
                    ms.translate(0, -yOffset, 0);

                    int containerX = mouseX - scrollableBounds.x;
                    int containerY = mouseY - scrollableBounds.y + yOffset;

                    entries.forEach(entry -> entry.render(matrices, containerX, containerY, delta));
                    ms.pop();
                });


        float scrollPercentage = (float) yOffset / (scrollableBounds.height - scrollBounds.height);
        float viewportRatio = (float) renderableBounds.getHeight() / scrollableBounds.height;
        int scrollHeight = (int) (renderableBounds.height * viewportRatio);

        if (viewportRatio <= 1) {

            RenderSystem.setShaderTexture(0, ConfigScreen.HUD_RESOURCE);
            matrices.push();
            matrices.translate(scrollBounds.x, scrollBounds.y, 0);
            matrices.scale(1, scrollBounds.height, 1);
            drawTexture(matrices, 1, 0, 0, 146, 8, 1);
            matrices.pop();
            drawTexture(matrices, scrollBounds.x + 1, scrollBounds.y, 0, 145, 8, 1);
            drawTexture(matrices, scrollBounds.x + 1, scrollBounds.y + scrollBounds.height, 0, 251, 8, 1);

            int scrollU = scrolling ? 28 : scrollBounds.contains(mouseX, mouseY) ? 18 : 8;

            matrices.push();
            matrices.translate(0, (scrollBounds.getHeight() - scrollHeight) * scrollPercentage, 0);
            drawTexture(matrices, scrollBounds.x + 1, scrollBounds.y,
                    scrollU, 104,
                    8, scrollHeight);
            drawTexture(matrices, scrollBounds.x + 1, scrollBounds.y - 2,
                    scrollU, 101,
                    8, 2);
            drawTexture(matrices, scrollBounds.x + 1, scrollBounds.y + scrollHeight,
                    scrollU, 253,
                    8, 2);
            matrices.pop();
        }
    }

    private void renderOverflowHidden(MatrixStack matrices, Consumer<MatrixStack> backgroundRenderer, Consumer<MatrixStack> innerRenderer) {
        matrices.push();
        RenderSystem.enableDepthTest();

        matrices.translate(0, 0, 950);
        RenderSystem.colorMask(false, false, false, false);
        fill(matrices, 4680, 2260, -4680, -2260, 0xff_000000);
        RenderSystem.colorMask(true, true, true, true);
        matrices.translate(0, 0, -950);

        RenderSystem.depthFunc(GL11.GL_GEQUAL);
        backgroundRenderer.accept(matrices);
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        innerRenderer.accept(matrices);
        RenderSystem.depthFunc(GL11.GL_GEQUAL);

        matrices.translate(0, 0, -950);
        RenderSystem.colorMask(false, false, false, false);
        fill(matrices, 4680, 2260, -4680, -2260, 0xff_000000);
        RenderSystem.colorMask(true, true, true, true);
        matrices.translate(0, 0, 950);
        RenderSystem.depthFunc(GL11.GL_LEQUAL);

        RenderSystem.disableDepthTest();
        matrices.pop();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isScrollbarClicked(mouseX, mouseY)) {
            scrollingStartY = mouseY;
            scrollingOffsetY = yOffset;
            scrolling = true;
            return true;
        }
        for (Entry entry1 : entries) {
            if (entry1.isHovered(MathHelper.floor(mouseX), MathHelper.floor(mouseY + yOffset))) {
                entry1.mouseClicked(mouseX, mouseY + scrollingOffsetY, button);
                MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            }
        }
        return Element.super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        scrolling = false;
        return Element.super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (scrolling) {
            double dY = mouseY - scrollingStartY;
            Rectangle scrollableBounds = getScrollableBounds();
            Rectangle renderableBounds = getRenderableBounds();
            Rectangle scrollbarBounds = getScrollbarBounds();
            double deltaOffset = dY * renderableBounds.height / scrollbarBounds.getHeight();

            yOffset = MathHelper.clamp(scrollingOffsetY + (int) (deltaOffset * scrollableBounds.height / scrollbarBounds.height),
                    0,
                    scrollableBounds.height - renderableBounds.height + 2);
        }
        return Element.super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        Rectangle scrollableBounds = getScrollableBounds();
        Rectangle renderableBounds = getRenderableBounds();
        float viewportRatio = (float) renderableBounds.getHeight() / scrollableBounds.height;

        if (viewportRatio < 1) {
            yOffset = MathHelper.clamp(yOffset + (int) (-delta * 5),
                    0,
                    scrollableBounds.height - renderableBounds.height + 2);
            return true;
        }
        return false;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return bounds.contains(mouseX, mouseY);
    }

    private void drawBounds(MatrixStack matrices) {
        // scroll

        drawRectangle(matrices, getRenderableBounds(), 0xFF_00FFFF);
        drawRectangle(matrices, getScrollableBounds(), 0xFF_FF0000);
        drawRectangle(matrices, getScrollbarBounds(), 0xFF_00FF00);


    }

    private void drawRectangle(MatrixStack matrices, Rectangle bounds, int color) {
        drawHorizontalLine(matrices, bounds.x, bounds.x + bounds.width, bounds.y, color);
        drawHorizontalLine(matrices, bounds.x, bounds.x + bounds.width, bounds.y + bounds.height, color);
        drawVerticalLine(matrices, bounds.x, bounds.y, bounds.y + bounds.height, color);
        drawVerticalLine(matrices, bounds.x + bounds.width, bounds.y, bounds.y + bounds.height, color);
    }


    @Override
    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    @Override
    public boolean isFocused() {
        return this.focused;
    }
}
