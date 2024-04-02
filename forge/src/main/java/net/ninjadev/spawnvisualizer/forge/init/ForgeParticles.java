package net.ninjadev.spawnvisualizer.forge.init;

import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeParticles {
    static {
        SpawnVisualizer.LOGGER.info("Initialize Forge Particles");
    }

    @SubscribeEvent
    public static void onParticleFactoryRegistry(RegisterParticleProvidersEvent event) {
        //event.registerSpecial(SPAWN_DUST, new SpawnDustParticle.Provider());
    }

    @SubscribeEvent
    public static void onParticleTypeRegistry(RegisterEvent event) {
        //event.register(ForgeRegistries.Keys.PARTICLE_TYPES , registry -> registry.register(new Identifier(SpawnVisualizer.MODID, "spawn_dust"), SPAWN_DUST));
    }
}
