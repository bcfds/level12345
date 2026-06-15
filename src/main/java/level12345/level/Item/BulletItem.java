package level12345.level.Item;

import level12345.level.Entity.ModEntities;
import level12345.level.Entity.projectile.bullet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BulletItem extends ArrowItem {
    public BulletItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        return new bullet(ModEntities.bullet.get(), shooter, level);
    }
}
