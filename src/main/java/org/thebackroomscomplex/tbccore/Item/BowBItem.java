package org.thebackroomscomplex.tbccore.Item;

import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class BowBItem extends BowItem {
    public BowBItem(Properties properties) {
        super(properties);
    }

    // 核心：告诉弓什么样的物品可作为弹药
    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return (stack) -> stack.getItem() == ModItems.Compressfiresalt.get();
    }
}
