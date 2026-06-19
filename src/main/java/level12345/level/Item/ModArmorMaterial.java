package level12345.level.Item;

import level12345.level.BackroomsLevel;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import level12345.level.Item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
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

    public ModArmorMaterial(String name, int durabilityMultiplier,
                            Map<ArmorItem.Type, Integer> defenseMap,
                            int enchantability, SoundEvent equipSound,
                            float toughness, float knockbackResistance,
                            Supplier<Ingredient> repairIngredient,float projectileResistance) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.defenseMap = defenseMap;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
        this.projectileResistance = projectileResistance;
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
    @Override public String getName() { return BackroomsLevel.MOD_ID + ":" + this.name; }
    @Override public float getToughness() { return toughness; }
    @Override public float getKnockbackResistance() { return knockbackResistance; }
    public float getProjectileResistance() {
        return projectileResistance;
    }
}
