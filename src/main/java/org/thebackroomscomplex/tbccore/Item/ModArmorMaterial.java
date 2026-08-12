package org.thebackroomscomplex.tbccore.Item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.thebackroomscomplex.tbccore.TBCcore;

import java.util.Map;
import java.util.function.Supplier;

public class ModArmorMaterial implements ArmorMaterial {
    private final String name;
    private final int durabilityMultiplier;
    private final Map<ArmorItem.Type, Integer> defenseMap;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;
    private final float projectileResistance;
    private final float specialresistance;

    public ModArmorMaterial(String name, int durabilityMultiplier,
                            Map<ArmorItem.Type, Integer> defenseMap,
                            int enchantability, SoundEvent equipSound,
                            float toughness, float knockbackResistance,
                            Supplier<Ingredient> repairIngredient,float projectileResistance,float specialresistance) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.defenseMap = defenseMap;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
        this.projectileResistance = projectileResistance;
        this.specialresistance = specialresistance;
    }

    @Override public int getDurabilityForType(ArmorItem.Type type) {
        return durabilityMultiplier * switch (type) {
            case HELMET -> 11; case CHESTPLATE -> 16;
            case LEGGINGS -> 15; case BOOTS -> 13;
        };
    }

    @Override public int getDefenseForType(ArmorItem.Type type) {
        return defenseMap.getOrDefault(type, 0);
    }

    @Override public int getEnchantmentValue() { return enchantability; }
    @Override public SoundEvent getEquipSound() { return equipSound; }
    @Override public Ingredient getRepairIngredient() { return repairIngredient.get(); }
    @Override public String getName() { return TBCcore.MOD_ID + ":" + this.name; }
    @Override public float getToughness() { return toughness; }
    @Override public float getKnockbackResistance() { return knockbackResistance; }
    public float getProjectileResistance() {
        return projectileResistance;
    }
    public float getSpecialresistance() {
        return specialresistance;
    }
}
