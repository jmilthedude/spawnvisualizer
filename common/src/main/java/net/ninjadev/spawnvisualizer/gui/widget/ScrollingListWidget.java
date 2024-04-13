package net.ninjadev.spawnvisualizer.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.ninjadev.spawnvisualizer.gui.ConfigScreen;
import net.ninjadev.spawnvisualizer.gui.widget.entry.Entry;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class ScrollingListWidget extends ClickableWidget {

    protected final Rectangle bounds;
    private final List<Entry> entries = new ArrayList<>();
    protected final int scrollBarWidth = 8;
    private boolean scrolling;
    protected int yOffset;
    protected double scrollingStartY;
    protected int scrollingOffsetY;

    private boolean focused;

    public ScrollingListWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Text.literal(""));
        this.bounds = new Rectangle(x, y, width + scrollBarWidth, height);

        init();
    }

    public boolean isScrolling() {
        return scrolling;
    }

    protected Rectangle getRenderableBounds() {
        return new Rectangle(bounds.x, bounds.y, bounds.width - scrollBarWidth, bounds.height);
    }

    protected Rectangle getScrollableBounds() {
        return new Rectangle(bounds.x, bounds.y, bounds.width - scrollBarWidth, 20 + (entries.size() * 24 / 2));
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

    protected abstract void init();

    protected void addEntry(Entry entry) {
        this.entries.add(entry);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        Rectangle renderableBounds = getRenderableBounds();
        Rectangle scrollBounds = getScrollbarBounds();
        Rectangle scrollableBounds = getScrollableBounds();


        renderOverflowHidden(matrices,
                (ms) -> fill(ms, renderableBounds.x, renderableBounds.y, renderableBounds.x + renderableBounds.width, renderableBounds.y + renderableBounds.height, 0xFF_888888),
                (ms) -> {
                    ms.push();
                    ms.translate(0, -yOffset, 0);

                    int containerX = mouseX - scrollableBounds.x;
                    int containerY = mouseY - scrollableBounds.y + yOffset;

                    for (Entry entry : entries) {
                        entry.render(ms, containerX, containerY, delta);
                    }

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
            drawTexture(matrices,  1, 0, 0, 146, 8, 1);
            matrices.pop();
            drawTexture(matrices,  scrollBounds.x + 1, scrollBounds.y, 0, 145, 8, 1);
            drawTexture(matrices,  scrollBounds.x + 1, scrollBounds.y + scrollBounds.height, 0, 251, 8, 1);

            int scrollU = scrolling ? 28 : scrollBounds.contains(mouseX, mouseY) ? 18 : 8;

            matrices.push();
            matrices.translate(0, (scrollBounds.getHeight() - scrollHeight) * scrollPercentage, 0);
            drawTexture(matrices,  scrollBounds.x + 1, scrollBounds.y,
                    scrollU, 104,
                    8, scrollHeight);
            drawTexture(matrices,  scrollBounds.x + 1, scrollBounds.y - 2,
                    scrollU, 101,
                    8, 2);
            drawTexture(matrices,  scrollBounds.x + 1, scrollBounds.y + scrollHeight,
                    scrollU, 253,
                    8, 2);
            matrices.pop();
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

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
        double translatedMouseX = mouseX - this.getBounds().x;
        double translatedMouseY = mouseY + yOffset - this.getBounds().y;
        for (Entry entry : entries) {
            if (entry.isHovered(MathHelper.floor(translatedMouseX), MathHelper.floor(translatedMouseY))) {
                return entry.mouseClicked(translatedMouseX, translatedMouseY, button);
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        scrolling = false;
        return super.mouseReleased(mouseX, mouseY, button);
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
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double verticalAmount) {
        Rectangle scrollableBounds = getScrollableBounds();
        Rectangle renderableBounds = getRenderableBounds();
        float viewportRatio = (float) renderableBounds.getHeight() / scrollableBounds.height;

        if (viewportRatio < 1) {
            yOffset = MathHelper.clamp(yOffset + (int) (-verticalAmount * 5),
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

    @Override
    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    @Override
    public boolean isFocused() {
        return this.focused;
    }
}
