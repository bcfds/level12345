package level12345.level.Item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.Map;

public class ModArmors {
    public static final ArmorMaterial BSTEEL = new ModArmorMaterial(
            "bsteel",
            40,
            ModArmors.createDefenseMap(5,10,8,4),
            20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            3.0F, 0.025F,
            () -> Ingredient.of(ModItems.BSTEEL.get()),0.3F,0.05F
    );
    public static final ArmorMaterial RIGAL = new ModArmorMaterial(
            "rigal",
            40,
            ModArmors.createDefenseMap(3,5,4,2),
            20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            4.0F, 0.005F,
            () -> Ingredient.of(ModItems.BSTEEL.get()),0.05F,0.4F
    );
    private static Map<ArmorItem.Type, Integer> createDefenseMap(int h, int c, int l, int b) {
        Map<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        map.put(ArmorItem.Type.HELMET, h);
        map.put(ArmorItem.Type.CHESTPLATE, c);
        map.put(ArmorItem.Type.LEGGINGS, l);
        map.put(ArmorItem.Type.BOOTS, b);
        return map;
    }
}
