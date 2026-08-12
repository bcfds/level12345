package org.thebackroomscomplex.tbccore.Entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.thebackroomscomplex.tbccore.Entity.custom.SmilerEntity;
import org.thebackroomscomplex.tbccore.Entity.projectile.Firesalt;
import org.thebackroomscomplex.tbccore.Entity.projectile.LightningInABottle;
import org.thebackroomscomplex.tbccore.Entity.projectile.bullet;
import org.thebackroomscomplex.tbccore.Entity.projectile.flame;
import org.thebackroomscomplex.tbccore.TBCcore;

public class ModEntities{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TBCcore.MOD_ID);
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
                            .sized(0.1F, 0.1F)
                            .clientTrackingRange(4)
                            .updateInterval(90)
                            .build("bullet"));
    public static final RegistryObject<EntityType<bullet>> compress_firesalt =
            ENTITY_TYPES.register("compress_firesalt",
                    () -> EntityType.Builder.<bullet>of(bullet::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(16)
                            .updateInterval(20)
                            .build("compress_firesalt"));
    public static final RegistryObject<EntityType<flame>> flame =
            ENTITY_TYPES.register("flame",
                    () -> EntityType.Builder.<flame>of(flame::new, MobCategory.MISC)
                            .sized(1.5F, 1.5F)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build("flame"));
    public static final RegistryObject<EntityType<SmilerEntity>> smiler =
            ENTITY_TYPES.register("smiler",
                    () -> EntityType.Builder.of(SmilerEntity::new, MobCategory.CREATURE)
                            .sized(1.5F, 1.5F)
                            .clientTrackingRange(8)
                            .build("smiler"));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
