package level12345.level.Entity;

import level12345.level.Level;
import level12345.level.Entity.projectile.Firesalt;
import level12345.level.Entity.projectile.LightningInABottle;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Level.MOD_ID);
    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
    public static final RegistryObject<EntityType<LightningInABottle>> LIGHTNING_IN_A_BOTTLE =
            ENTITY_TYPES.register("LIGHTNING_IN_A_BOTTLE", () -> EntityType.Builder.<LightningInABottle>of(
                            LightningInABottle::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(64)
                    .build("LIGHTNING_IN_A_BOTTLE"));
    public static final RegistryObject<EntityType<Firesalt>> FIRESALT =
            ENTITY_TYPES.register("FiresaltItem", () -> EntityType.Builder.<Firesalt>of(
                            Firesalt::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(64)
                    .build("FiresaltItem"));
}
