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
            ModArmors.createDefenseMap(5,11,0,0),
            30,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            3.0F, 0.025F,
            () -> Ingredient.of(ModItems.BSTEEL.get()),0.25F
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
