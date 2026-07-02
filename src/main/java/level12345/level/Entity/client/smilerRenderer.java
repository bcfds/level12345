package level12345.level.Entity.client;


import level12345.level.BackroomsLevel;
import level12345.level.Entity.custom.SmilerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;


public class smilerRenderer extends MobRenderer<SmilerEntity,smilerModel<SmilerEntity>> {
    public smilerRenderer(EntityRendererProvider.Context pContext){
        super(pContext,new smilerModel<>(pContext.bakeLayer(ModModelLayers.SMILER_LAYER)),2f);
    }
    @Override
    public ResourceLocation getTextureLocation(SmilerEntity pEntity){
        return new ResourceLocation(BackroomsLevel.MOD_ID,"textures/entity/smiler.png");
    }
}
