package level12345.level.Entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class smilerModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    private final ModelPart group2;
    private final ModelPart group;

    public smilerModel(ModelPart root) {
        this.group2 = root.getChild("group2");
        this.group = root.getChild("group");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition group2 = partdefinition.addOrReplaceChild("group2", CubeListBuilder.create().texOffs(0, 10).addBox(24.0F, -3.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(25.0F, -3.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(23.0F, -2.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(22.0F, 1.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(21.0F, 3.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(20.0F, 4.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(19.0F, 4.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(19.0F, 7.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(18.0F, 8.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(17.0F, 9.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(16.0F, 9.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(15.0F, 10.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(14.0F, 9.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(13.0F, 10.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(12.0F, 9.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(11.0F, 10.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(10.0F, 10.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(9.0F, 9.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(8.0F, 9.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(7.0F, 8.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(11.0F, 10.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(18.0F, 5.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(17.0F, 5.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(16.0F, 6.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(15.0F, 6.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(14.0F, 5.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(13.0F, 5.0F, 0.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(12.0F, 6.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(11.0F, 6.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(10.0F, 6.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(9.0F, 6.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(8.0F, 6.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(7.0F, 6.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(6.0F, 6.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(5.0F, 6.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(4.0F, 5.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(3.0F, 4.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(2.0F, 3.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(1.0F, 2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(0.0F, 1.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-2.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.0F, 8.0F, -1.0F));

        PartDefinition cube_r1 = group2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 10).addBox(-2.0F, -3.0F, 0.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-3.0F, -2.0F, 0.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(21.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r2 = group2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 10).addBox(-2.0F, -3.0F, 0.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-3.0F, -2.0F, 0.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition group = partdefinition.addOrReplaceChild("group", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, 2.0F, 2.0F, -3.0F, -3.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(1, 1).addBox(27.0F, 0.0F, 2.0F, -4.0F, -4.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(25.0F, 1.0F, 2.0F, -3.0F, -4.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(1.0F, 4.0F, 2.0F, -3.0F, -5.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(2.0F, 4.0F, 2.0F, -3.0F, -4.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(3.0F, 5.0F, 2.0F, -3.0F, -4.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(4.0F, 6.0F, 2.0F, -3.0F, -4.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(5.0F, 7.0F, 2.0F, -3.0F, -4.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(6.0F, 8.0F, 2.0F, -3.0F, -4.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(7.0F, 9.0F, 2.0F, -3.0F, -4.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(9.0F, 11.0F, 2.0F, -3.0F, -6.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(11.0F, 12.0F, 2.0F, -3.0F, -7.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(14.0F, 13.0F, 2.0F, -3.0F, -8.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(19.0F, 12.0F, 2.0F, -3.0F, -8.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(16.0F, 13.0F, 2.0F, -4.0F, -9.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(19.0F, 12.0F, 2.0F, -3.0F, -8.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(20.0F, 11.0F, 2.0F, -3.0F, -8.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(20.0F, 11.0F, 2.0F, -3.0F, -8.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(21.0F, 10.0F, 2.0F, -3.0F, -8.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(22.0F, 8.0F, 2.0F, -3.0F, -6.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(23.0F, 6.0F, 2.0F, -3.0F, -6.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(24.0F, 4.0F, 2.0F, -3.0F, -7.0F, -2.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.0F, 8.0F, -1.0F));

        PartDefinition cube_r3 = group.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(5, 1).mirror().addBox(-2.0F, 5.0F, -1.0F, -6.0F, -4.0F, -2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0F, -7.0F, 3.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r4 = group.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(4, 1).addBox(8.0F, -1.0F, -1.0F, -6.0F, -4.0F, -2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, -4.0F, 3.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r5 = group.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(5, 1).mirror().addBox(-2.0F, 5.0F, -1.0F, -6.0F, -4.0F, -2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(21.0F, -8.0F, 3.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r6 = group.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(1, 1).addBox(8.0F, -1.0F, -1.0F, -6.0F, -4.0F, -2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -3.0F, 3.0F, 0.0F, 0.0F, 0.7854F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        group2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        group.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
