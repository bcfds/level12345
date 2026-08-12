package org.thebackroomscomplex.tbccore;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.thebackroomscomplex.tbccore.Item.Flamethrower;
import org.thebackroomscomplex.tbccore.Item.LightningInABottleItem;
import org.thebackroomscomplex.tbccore.Item.Rifle;

@Mod.EventBusSubscriber(modid = TBCcore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientAntiHurt {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            if (player.getMainHandItem().getItem() instanceof Rifle) {
                event.getEntity().invulnerableTime = 0;
            }
        }
        if (event.getSource().getEntity() instanceof Player player) {
            if (player.getMainHandItem().getItem() instanceof LightningInABottleItem) {
                event.getEntity().invulnerableTime = 0;
            }
        }
        if (event.getSource().getEntity() instanceof Player player) {
            if (player.getMainHandItem().getItem() instanceof Flamethrower) {
                event.getEntity().invulnerableTime = 0;
            }
        }
    }
}
