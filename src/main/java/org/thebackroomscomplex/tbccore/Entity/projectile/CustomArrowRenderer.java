package org.thebackroomscomplex.tbccore.Entity.projectile;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CustomArrowRenderer extends ArrowRenderer<bullet> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("tbccore", "assets/tbccore/textures/item/bullet");

    public CustomArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull bullet entity) {
        return TEXTURE;
    }
}
