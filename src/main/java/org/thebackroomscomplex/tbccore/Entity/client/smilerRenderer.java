package org.thebackroomscomplex.tbccore.Entity.client;


import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.thebackroomscomplex.tbccore.Entity.custom.SmilerEntity;
import org.thebackroomscomplex.tbccore.TBCcore;


public class smilerRenderer extends MobRenderer<SmilerEntity,smilerModel<SmilerEntity>> {
    public smilerRenderer(EntityRendererProvider.Context pContext){
        super(pContext,new smilerModel<>(pContext.bakeLayer(ModModelLayers.SMILER_LAYER)),2f);
    }
    @Override
    public ResourceLocation getTextureLocation(SmilerEntity pEntity){
        return new ResourceLocation(TBCcore.MOD_ID, "assets/tbccore/textures/entity/smiler.png");
    }
}
