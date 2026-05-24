package level12345.level.entity;

import level12345.level.Level;
import level12345.level.entity.projectile.bottle_light;
import level12345.level.entity.projectile.fire_salt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
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
    public static final RegistryObject<EntityType<bottle_light>> BOTTLE_LIGHT =
            ENTITY_TYPES.register("bottle_light", () -> EntityType.Builder.<bottle_light>of(
                            bottle_light::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(64)
                    .build("bottle_light"));
    public static final RegistryObject<EntityType<fire_salt>> FIRE_SALT =
            ENTITY_TYPES.register("fire_salt", () -> EntityType.Builder.<fire_salt>of(
                            fire_salt::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(64)
                    .build("fire_salt"));
}
