//TODO FINISH THIS
package org.thebackroomscomplex.tbccore.Capability.sanity.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.thebackroomscomplex.tbccore.Capability.ModCapabilities;
import org.thebackroomscomplex.tbccore.TBCcore;

@Mod.EventBusSubscriber(modid = TBCcore.MOD_ID, value = Dist.CLIENT)
public class SanityRender {

    // 使用 RenderGuiEvent 监听游戏界面渲染
    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        player.getCapability(ModCapabilities.PLAYER_SANITY).ifPresent(sanity -> {
            int currentSanity = sanity.getSanity();
            // 在屏幕左上角绘制文本，位置 (10, 10)
            event.getGuiGraphics().drawString(Minecraft.getInstance().font,
                    "Sanity: " + currentSanity, 10, 10, 0xFFFFFF);
        });
    }
}
