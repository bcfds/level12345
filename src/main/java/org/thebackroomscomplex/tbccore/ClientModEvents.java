package org.thebackroomscomplex.tbccore;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.thebackroomscomplex.tbccore.Entity.ModEntities;
import org.thebackroomscomplex.tbccore.Entity.client.smilerRenderer;
import org.thebackroomscomplex.tbccore.Entity.projectile.CustomArrowRenderer;
import org.thebackroomscomplex.tbccore.Entity.projectile.FlameRenderer;

@Mod.EventBusSubscriber(modid = TBCcore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.LIGHTNING_IN_A_BOTTLE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.FIRESALT.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.bullet.get(), CustomArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.compress_firesalt.get(), CustomArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.flame.get(), FlameRenderer::new);
        event.registerEntityRenderer(ModEntities.smiler.get(), smilerRenderer::new);
    }
}