package level12345.level;

import level12345.level.Entity.ModEntities;
import level12345.level.Entity.projectile.CustomArrowRenderer;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BackroomsLevel.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.LIGHTNING_IN_A_BOTTLE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.FIRESALT.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.bullet.get(), CustomArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.compress_firesalt.get(), CustomArrowRenderer::new);
    }
}