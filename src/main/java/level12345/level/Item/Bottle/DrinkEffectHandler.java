package level12345.level.Item.Bottle;

import level12345.level.Item.Bottle.Event.FluidDrinkEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;

public class DrinkEffectHandler {

    @SubscribeEvent
    public void onItemFinish(LivingEntityUseItemEvent.Finish event) {
        ItemStack usedStack = event.getItem();

        if (!(usedStack.getItem() instanceof BottleItem)) return;

        FluidStack fluid = BottleItem.getFluid(usedStack);
        if (fluid.isEmpty()) return;

        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        // 触发自定义事件，允许其他 MOD 取消或修改
        FluidDrinkEvent drinkEvent = new FluidDrinkEvent(entity, fluid);
        if (MinecraftForge.EVENT_BUS.post(drinkEvent)) {
            return;
        }

        applyEffects(entity, fluid, level);
    }

    public static void applyEffects(LivingEntity entity, FluidStack fluid, Level level) {
        if (level.isClientSide) return;

        if (fluid.getFluid() == Fluids.LAVA) {
            entity.hurt(level.damageSources().lava(), 4.0F);
        } else if (fluid.getFluid().getFluidType().getTemperature() > 1000) {
            entity.hurt(level.damageSources().onFire(), 2.0F);
        }
    }
}