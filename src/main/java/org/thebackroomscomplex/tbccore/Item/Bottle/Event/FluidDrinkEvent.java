package org.thebackroomscomplex.tbccore.Item.Bottle.Event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fluids.FluidStack;

/**
 * 瓶子饮用事件。其他 MOD 可监听此事件以添加自定义效果或取消默认效果。
 */
@Cancelable
public class FluidDrinkEvent extends Event {
    private final LivingEntity entity;
    private final FluidStack fluid;

    public FluidDrinkEvent(LivingEntity entity, FluidStack fluid) {
        this.entity = entity;
        this.fluid = fluid;
    }

    public LivingEntity getEntity() { return entity; }
    public FluidStack getFluid() { return fluid; }
    public Level getLevel() { return entity.level(); }
}