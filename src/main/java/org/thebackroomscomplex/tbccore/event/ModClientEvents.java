package org.thebackroomscomplex.tbccore.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.thebackroomscomplex.tbccore.Entity.ModEntities;
import org.thebackroomscomplex.tbccore.Entity.custom.SmilerEntity;
import org.thebackroomscomplex.tbccore.TBCcore;

@Mod.EventBusSubscriber(modid = TBCcore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.smiler.get(), SmilerEntity.createAttributes().build());
    }
}
