package net.ninjadev.spawnvisualizer.forge.init;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.particle.SpawnDustParticle;

import static net.ninjadev.spawnvisualizer.init.ModParticles.SPAWN_DUST;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeParticles {
    static {
        SpawnVisualizer.LOGGER.info("Initialize Forge Particles");
    }

    @SubscribeEvent
    public static void onParticleFactoryRegistry(RegisterParticleProvidersEvent event) {
        event.register(SPAWN_DUST, SpawnDustParticle.Provider::new);
    }

    @SubscribeEvent
    public static void onParticleTypeRegistry(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.PARTICLE_TYPES , registry -> registry.register(new ResourceLocation(SpawnVisualizer.MODID, "spawn_dust"), SPAWN_DUST));
    }
}
