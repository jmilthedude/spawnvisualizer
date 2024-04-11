package net.ninjadev.spawnvisualizer.config;

import com.google.gson.annotations.Expose;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.ninjadev.spawnvisualizer.config.entry.EntitySpawnSettings;
import net.ninjadev.spawnvisualizer.init.ModSpawnValidators;
import net.ninjadev.spawnvisualizer.settings.SpawnValidator;

import java.util.HashMap;
import java.util.List;

public class EntitySettingsConfig extends Config {

    @Expose HashMap<Identifier, EntitySpawnSettings> entitySettings;

    @Override
    public String getName() {
        return "entity_spawn_settings";
    }

    @Override
    protected void reset() {
        entitySettings = new HashMap<>();

        entitySettings.put(getEntityId(EntityType.AXOLOTL), new EntitySpawnSettings().setHexColor("e088b9"));
        entitySettings.put(getEntityId(EntityType.COD), new EntitySpawnSettings().setHexColor("b1977b"));
        entitySettings.put(getEntityId(EntityType.CREEPER), new EntitySpawnSettings().setHexColor("c1fec1"));
        entitySettings.put(getEntityId(EntityType.DOLPHIN), new EntitySpawnSettings().setHexColor("aabcce"));
        entitySettings.put(getEntityId(EntityType.DROWNED), new EntitySpawnSettings().setHexColor("6eb9a6"));
        entitySettings.put(getEntityId(EntityType.SQUID), new EntitySpawnSettings().setHexColor("354758"));
        entitySettings.put(getEntityId(EntityType.BAT), new EntitySpawnSettings().setHexColor("2e2623"));
        entitySettings.put(getEntityId(EntityType.BLAZE), new EntitySpawnSettings().setHexColor("c08921"));
        entitySettings.put(getEntityId(EntityType.ENDERMAN), new EntitySpawnSettings().setHexColor("0f0f0f"));
        entitySettings.put(getEntityId(EntityType.FROG), new EntitySpawnSettings().setHexColor("638E2D"));
        entitySettings.put(getEntityId(EntityType.GHAST), new EntitySpawnSettings().setHexColor("e3e2e2"));
        entitySettings.put(getEntityId(EntityType.GLOW_SQUID), new EntitySpawnSettings().setHexColor("278680"));
        entitySettings.put(getEntityId(EntityType.HUSK), new EntitySpawnSettings().setHexColor("796a4a"));
        entitySettings.put(getEntityId(EntityType.MAGMA_CUBE), new EntitySpawnSettings().setHexColor("ffa500"));
        entitySettings.put(getEntityId(EntityType.MOOSHROOM), new EntitySpawnSettings().setHexColor("A31013"));
        entitySettings.put(getEntityId(EntityType.HOGLIN), new EntitySpawnSettings().setHexColor("b0775e"));
        entitySettings.put(getEntityId(EntityType.PIGLIN), new EntitySpawnSettings().setHexColor("f0b985"));
        entitySettings.put(getEntityId(EntityType.SKELETON), new EntitySpawnSettings().setHexColor("a8a7a7"));
        entitySettings.put(getEntityId(EntityType.SLIME), new EntitySpawnSettings().setHexColor("539144"));
        entitySettings.put(getEntityId(EntityType.SPIDER), new EntitySpawnSettings().setHexColor("393029"));
        entitySettings.put(getEntityId(EntityType.STRAY), new EntitySpawnSettings().setHexColor("99aaa9"));
        entitySettings.put(getEntityId(EntityType.STRIDER), new EntitySpawnSettings().setHexColor("7e3536"));
        entitySettings.put(getEntityId(EntityType.WITCH), new EntitySpawnSettings().setHexColor("30144f"));
        entitySettings.put(getEntityId(EntityType.WITHER_SKELETON), new EntitySpawnSettings().setHexColor("252626"));
        entitySettings.put(getEntityId(EntityType.ZOMBIE), new EntitySpawnSettings().setHexColor("006600"));
        entitySettings.put(getEntityId(EntityType.ZOMBIFIED_PIGLIN), new EntitySpawnSettings().setHexColor("ac7263"));
    }

    public SpawnValidator createValidator(Identifier id) {
        return new SpawnValidator(Registries.ENTITY_TYPE.get(id), entitySettings.get(id));
    }

    public List<Identifier> getEntityIds() {
        return entitySettings.keySet().stream().toList();
    }

    private Identifier getEntityId(EntityType<?> type) {
        return Registries.ENTITY_TYPE.getId(type);
    }

    public void resetSettings(EntityType<?> type, EntitySpawnSettings config) {
        Identifier entityId = this.getEntityId(type);
        entitySettings.put(entityId, config);
        ModSpawnValidators.updateValidator(entityId, this.createValidator(entityId));
        this.writeConfig();
    }
}
