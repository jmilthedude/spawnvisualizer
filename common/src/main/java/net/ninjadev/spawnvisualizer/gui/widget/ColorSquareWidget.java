package net.ninjadev.spawnvisualizer.gui.widget;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.minecraft.client.gui.DrawableHelper.fill;

public class ColorSquareWidget implements Drawable, Widget {

    private int x;
    private int y;
    private int width;
    private int height;

    private final Supplier<Color> color;

    public ColorSquareWidget(int x, int y, int width, int height, Supplier<Color> color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        fill(matrices, this.x, this.y, this.x + this.width, this.y + this.height, this.color.get().getRGB());
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void forEachChild(Consumer<ClickableWidget> consumer) {

    }
}
