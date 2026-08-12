package org.thebackroomscomplex.tbccore.Entity.projectile;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class FlameRenderer extends ArrowRenderer<flame> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("tbccore", "assets/tbccore/textures/item/bullet");

    public FlameRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull flame entity) {
        return TEXTURE;
    }
}
