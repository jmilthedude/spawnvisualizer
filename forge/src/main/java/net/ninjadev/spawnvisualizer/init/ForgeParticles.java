package net.ninjadev.spawnvisualizer.init;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.particle.SpawnDustParticle;

import static net.ninjadev.spawnvisualizer.init.ModParticles.SPAWN_DUST;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeParticles {
    static {
        SpawnVisualizer.LOGGER.info("Initialize Forge Particles");
    }

    @SubscribeEvent
    public static void onParticleFactoryRegistry(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(SPAWN_DUST, SpawnDustParticle.Provider::new);
    }

    @SubscribeEvent
    public static void onParticleTypeRegistry(RegistryEvent.Register<ParticleType<?>> event) {
        SPAWN_DUST.setRegistryName(new ResourceLocation(SpawnVisualizer.MODID, "spawn_dust"));
        event.getRegistry().register(SPAWN_DUST);
    }
}
