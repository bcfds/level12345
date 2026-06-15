package level12345.level.Entity;

import level12345.level.BackroomsLevel;
import level12345.level.Entity.projectile.Firesalt;
import level12345.level.Entity.projectile.LightningInABottle;
import level12345.level.Entity.projectile.bullet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static javax.swing.text.html.parser.DTDConstants.ENTITIES;

public class ModEntities{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BackroomsLevel.MOD_ID);
    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
    public static final RegistryObject<EntityType<LightningInABottle>> LIGHTNING_IN_A_BOTTLE =
            ENTITY_TYPES.register("lightning_in_a_bottle", () -> EntityType.Builder.<LightningInABottle>of(
                            LightningInABottle::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(64)
                    .build("lightning_in_a_bottle"));
    public static final RegistryObject<EntityType<Firesalt>> FIRESALT =
            ENTITY_TYPES.register("fire_salt", () -> EntityType.Builder.<Firesalt>of(
                            Firesalt::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(64)
                    .build("fire_salt"));
    public static final RegistryObject<EntityType<bullet>> bullet =
            ENTITY_TYPES.register("bullet",
                    () -> EntityType.Builder.<bullet>of(bullet::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build("bullet"));
}
