package net.ninjadev.spawnvisualizer.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.ninjadev.spawnvisualizer.config.adapter.IdentifierAdapter;

import java.io.*;
import java.lang.reflect.Type;

public abstract class Config {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapterFactory(IdentifierAdapter.FACTORY)
            .excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    protected File root = new File("config/SpawnVisualizer/");
    protected String extension = ".json";

    private boolean isDirty = true;

    public void generateConfig() {
        this.reset();

        this.writeConfig();
    }

    private File getConfigFile() {
        return new File(this.root, this.getName() + this.extension);
    }

    public abstract String getName();

    public <T extends Config> T readConfig() {
        try {
            return GSON.fromJson(new FileReader(this.getConfigFile()), (Type) this.getClass());
        } catch (FileNotFoundException e) {
            this.generateConfig();
        }

        return (T) this;
    }

    protected abstract void reset();

    public void writeConfig() {
        if (!this.isDirty) return;
        try {
            if (!root.exists() && !root.mkdirs()) return;
            if (!this.getConfigFile().exists() && !this.getConfigFile().createNewFile()) return;
            FileWriter writer = new FileWriter(this.getConfigFile());
            GSON.toJson(this, writer);
            writer.flush();
            writer.close();
            this.isDirty = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void markDirty() {
        this.isDirty = true;
    }

}
