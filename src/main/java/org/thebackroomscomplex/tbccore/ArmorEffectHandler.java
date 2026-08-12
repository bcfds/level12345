package org.thebackroomscomplex.tbccore;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.thebackroomscomplex.tbccore.Item.ModArmorMaterial;

@Mod.EventBusSubscriber(modid = TBCcore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ArmorEffectHandler {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        // 只处理玩家，并且伤害来源是弹射物（箭、三叉戟、火焰弹等）
        if (!(event.getEntity() instanceof Player player)) return;
        if (!event.getSource().is(DamageTypeTags.IS_PROJECTILE)) return;

        // 累加全身提供的弹射物减免
        float totalResistance = 0.0F;
        for (ItemStack armorStack : player.getArmorSlots()) {
            if (armorStack.getItem() instanceof ArmorItem armorItem) {
                ArmorMaterial material = armorItem.getMaterial();
                if (material instanceof ModArmorMaterial modMaterial) {
                    totalResistance += modMaterial.getProjectileResistance();
                }
            }
        }

        // 限制最大减免不超过 1.0（100%）
        if (totalResistance > 1.0F) totalResistance = 1.0F;

        // 应用减免
        if (totalResistance > 0.0F) {
            float newDamage = event.getAmount() * (1.0F - totalResistance);
            event.setAmount(newDamage);
        }
    }
    @SubscribeEvent
    public static void onAHurt(LivingHurtEvent event){
        if (!(event.getEntity() instanceof Player player)) return;
        if (!event.getSource().is(DamageTypeTags.BYPASSES_ARMOR)) return;
        float totalResistance1 = 0.0F;
        for (ItemStack armorStack : player.getArmorSlots()) {
            if (armorStack.getItem() instanceof ArmorItem armorItem) {
                ArmorMaterial material = armorItem.getMaterial();
                if (material instanceof ModArmorMaterial modMaterial) {
                    totalResistance1 += modMaterial.getSpecialresistance();
                }
            }
        }
        if (totalResistance1 > 1.0F) totalResistance1 = 1.0F;
        if (totalResistance1 > 0.0F) {
            float newDamage = event.getAmount() * (1.0F - totalResistance1);
            event.setAmount(newDamage);
        }
    }
}
