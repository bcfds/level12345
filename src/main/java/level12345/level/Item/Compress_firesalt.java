package level12345.level.Item;

import level12345.level.Entity.ModEntities;
import level12345.level.Entity.projectile.bullet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Compress_firesalt extends ArrowItem {
    public Compress_firesalt(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        return new level12345.level.Entity.projectile.Compress_firesalt(ModEntities.compress_firesalt.get(), shooter, level);
    }
}
