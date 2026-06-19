package level12345.level.mixin;

import level12345.level.Item.Rifle;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {

    @Inject(method = "setModelProperties", at = @At("TAIL"))
    private void onSetModelProperties(AbstractClientPlayer player, CallbackInfo ci) {
        // 检查主手物品
        ItemStack mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (mainHandItem.getItem() instanceof Rifle) {
            // 强制手臂姿势为 CROSSBOW_HOLD（双手端枪）
            HumanoidModel<AbstractClientPlayer> model = ((PlayerRenderer)(Object)this).getModel();
            model.rightArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
            model.leftArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
        }
    }
}