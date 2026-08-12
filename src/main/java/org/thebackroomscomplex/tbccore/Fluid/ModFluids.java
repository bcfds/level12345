package org.thebackroomscomplex.tbccore.Fluid;

import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.thebackroomscomplex.tbccore.TBCcore;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, TBCcore.MOD_ID);

//    public static final RegistryObject<FlowingFluid> ALMOND_WATER_SOURCE = FLUIDS.register("almond_water",
//            () -> new ForgeFlowingFluid.Source(ModFluids.ALMOND_WATER_PROPERTIES));
//    public static final RegistryObject<FlowingFluid> ALMOND_WATER_FLOWING = FLUIDS.register("almond_water_flowing",
//            () -> new ForgeFlowingFluid.Flowing(ModFluids.ALMOND_WATER_PROPERTIES));
//
//    public static final ForgeFlowingFluid.Properties ALMOND_WATER_PROPERTIES = new ForgeFlowingFluid.Properties(
//            ALMOND_WATER_SOURCE::get, ALMOND_WATER_FLOWING);
}
