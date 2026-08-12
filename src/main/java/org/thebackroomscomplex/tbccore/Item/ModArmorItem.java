package org.thebackroomscomplex.tbccore.Item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class ModArmorItem extends ArmorItem {
    private final String armorTexturePrefix;

    public ModArmorItem(ArmorMaterial material, Type type, Properties properties, String armorTexturePrefix) {
        super(material, type, properties);
        this.armorTexturePrefix = armorTexturePrefix;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String layer) {
        return armorTexturePrefix + "_" + layer + ".png";
    }
}
