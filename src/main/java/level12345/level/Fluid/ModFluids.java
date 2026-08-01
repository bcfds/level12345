package level12345.level.Fluid;

import level12345.level.BackroomsLevel;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, BackroomsLevel.MOD_ID);

//    public static final RegistryObject<FlowingFluid> ALMOND_WATER_SOURCE = FLUIDS.register("almond_water",
//            () -> new ForgeFlowingFluid.Source(ModFluids.ALMOND_WATER_PROPERTIES));
//    public static final RegistryObject<FlowingFluid> ALMOND_WATER_FLOWING = FLUIDS.register("almond_water_flowing",
//            () -> new ForgeFlowingFluid.Flowing(ModFluids.ALMOND_WATER_PROPERTIES));
//
//    public static final ForgeFlowingFluid.Properties ALMOND_WATER_PROPERTIES = new ForgeFlowingFluid.Properties(
//            ALMOND_WATER_SOURCE::get, ALMOND_WATER_FLOWING);
}
