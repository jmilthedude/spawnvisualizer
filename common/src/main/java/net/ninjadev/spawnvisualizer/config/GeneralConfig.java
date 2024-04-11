package net.ninjadev.spawnvisualizer.config;

import com.google.gson.annotations.Expose;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.GeneratorOptions;

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
    private String seed;

    @Override
    public String getName() {
        return "general";
    }

    @Override
    protected void reset() {
        enable = false;
        this.rangeHorizontal = 15;
        this.rangeVertical = 5;
        this.ticksBetweenScans = 20;
        seed = "0";
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
        this.writeConfig();
        return this.enable;
    }

    public long getSeed() {
        return GeneratorOptions.parseSeed(this.seed).orElse(0);
    }

    public String getSeedString() {
        return this.seed;
    }

    public void setRangeHorizontal(double value) {
        this.rangeHorizontal = (int) Math.round(MathHelper.clamp(value, 1, 32));
        this.writeConfig();
    }

    public void setRangeVertical(double value) {
        this.rangeVertical = (int) Math.round(MathHelper.clamp(value, 1, 32));
        this.writeConfig();
    }

    public void setTicksBetweenScans(double value) {
        this.ticksBetweenScans = (int) Math.round(MathHelper.clamp(value, 1, 60));
        this.writeConfig();
    }

    public void setSeed(String seed) {
        this.seed = seed;
        this.writeConfig();
    }
}
