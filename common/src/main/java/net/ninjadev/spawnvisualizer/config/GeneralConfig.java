package net.ninjadev.spawnvisualizer.config;

import com.google.gson.annotations.Expose;
import net.minecraft.util.math.MathHelper;

public class GeneralConfig extends Config {

    @Expose
    private boolean enable;
    @Expose
    private int rangeHorizontal;
    @Expose
    private int rangeVertical;
    @Expose
    private int ticksBetweenScans;
    @Expose
    private long seed;

    @Override
    public String getName() {
        return "General";
    }

    @Override
    protected void reset() {
        enable = false;
        this.rangeHorizontal = 15;
        this.rangeVertical = 5;
        this.ticksBetweenScans = 20;
        seed = 1234567890L;
    }

    public int getRangeHorizontal() {
        return rangeHorizontal;
    }

    public int getRangeVertical() {
        return rangeVertical;
    }

    public int getTicksBetweenScans() {
        return ticksBetweenScans;
    }

    public boolean isEnabled() {
        return enable;
    }

    public boolean toggleEnabled() {
        this.enable = !this.enable;
        this.markDirty();
        return this.enable;
    }

    public long getSeed() {
        return seed;
    }

    public void setRangeHorizontal(double value) {
        this.rangeHorizontal = (int) Math.round(MathHelper.clamp(value, 1, 32));
        this.markDirty();
    }

    public void setRangeVertical(double value) {
        this.rangeVertical = (int) Math.round(MathHelper.clamp(value, 1, 32));
        this.markDirty();
    }

    public void setTicksBetweenScans(double value) {
        this.ticksBetweenScans = (int) Math.round(MathHelper.clamp(value, 1, 60));
        this.markDirty();
    }
}
