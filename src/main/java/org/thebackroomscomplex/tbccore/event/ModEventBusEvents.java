package org.thebackroomscomplex.tbccore.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.thebackroomscomplex.tbccore.Entity.client.ModModelLayers;
import org.thebackroomscomplex.tbccore.Entity.client.smilerModel;
import org.thebackroomscomplex.tbccore.TBCcore;

@Mod.EventBusSubscriber(modid = TBCcore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(ModModelLayers.SMILER_LAYER, smilerModel::createBodyLayer);
    }
}
